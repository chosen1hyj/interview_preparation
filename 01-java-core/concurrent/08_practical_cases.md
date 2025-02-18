# 实际案例

## 1. 高并发秒杀系统
...(前面内容保持不变)

## 3. 并发数据处理

### 数据分片策略
```java
// 数据分片
public class DataSharding {
    
    private static final int SHARD_COUNT = 10;
    
    public List<DataBatch> shard(List<Data> dataList) {
        Map<Integer, List<Data>> shards = new HashMap<>();
        
        for (Data data : dataList) {
            int shardId = Math.abs(data.getId().hashCode()) % SHARD_COUNT;
            shards.computeIfAbsent(shardId, k -> new ArrayList<>()).add(data);
        }
        
        return shards.entrySet().stream()
            .map(entry -> new DataBatch(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }
}
```

### 并行处理
```java
// ForkJoin实现
public class ParallelProcessor extends RecursiveTask<Void> {

    private final List<Data> dataList;
    private static final int THRESHOLD = 1000;
    
    public ParallelProcessor(List<Data> dataList) {
        this.dataList = dataList;
    }
    
    @Override
    protected Void compute() {
        if (dataList.size() <= THRESHOLD) {
            processSequentially();
        } else {
            processInParallel();
        }
        return null;
    }
    
    private void processSequentially() {
        dataList.forEach(this::processItem);
    }
    
    private void processInParallel() {
        int mid = dataList.size() / 2;
        ParallelProcessor left = new ParallelProcessor(dataList.subList(0, mid));
        ParallelProcessor right = new ParallelProcessor(dataList.subList(mid, dataList.size()));
        invokeAll(left, right);
    }
    
    private void processItem(Data data) {
        // 处理逻辑
    }
}
```

### 性能调优

1. **线程池配置**
```java
@Bean
public ExecutorService executorService() {
    return new ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        keepAliveTime,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(queueCapacity),
        new NamedThreadFactory("data-processor"),
        new CallerRunsPolicy()
    );
}
```

2. **批处理优化**
```java
// 批量写入
@Transactional
public void batchInsert(List<Data> dataList) {
    jdbcTemplate.batchUpdate(
        "INSERT INTO data_table (id, content) VALUES (?, ?)",
        dataList,
        batchSize,
        (ps, data) -> {
            ps.setString(1, data.getId());
            ps.setString(2, data.getContent());
        }
    );
}
```

3. **资源控制**
```java
// 信号量限流
private final Semaphore semaphore = new Semaphore(maxConcurrentTasks);

public void processWithLimit() throws InterruptedException {
    semaphore.acquire();
    try {
        // 处理逻辑
    } finally {
        semaphore.release();
    }
}
```

### 性能数据
| 场景 | 数据量 | 处理时间(ms) | 内存使用(MB) | CPU利用率 |
| --- | --- | --- | --- | --- |
| 单线程 | 100万 | 5000 | 500 | 90% |
| 多线程 | 100万 | 1200 | 800 | 300% |
| 分布式 | 1000万 | 3000 | - | - |

## 4. 实时消息推送

### 系统架构
```
+-------------------+       +-------------------+       +-------------------+
|   Client          | <---> |   WebSocket       | <---> |   Message Broker  |
| (Browser/Mobile)  |       | (Session Manager) |       | (Kafka/RabbitMQ)  |
+-------------------+       +-------------------+       +-------------------+
        ^                           ^                           ^
        |                           |                           |
+-------------------+       +-------------------+       +-------------------+
|   Load Balancer   |       |   Session Store   |       |   Message Store   |
| (Nginx/Haproxy)   |       | (Redis Cluster)   |       | (MongoDB/Cassandra)|
+-------------------+       +-------------------+       +-------------------+
```

### 核心实现

#### WebSocket服务端
```java
@ServerEndpoint("/chat/{userId}")
@Component
public class ChatEndpoint {

    private static final ConcurrentHashMap<String, ChatEndpoint> connections = new ConcurrentHashMap<>();
    
    private Session session;
    private String userId;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        connections.put(userId, this);
        
        // 维护会话信息
        RedisSessionStore.saveSession(userId, session.getId());
    }
    
    @OnMessage
    public void onMessage(String message) {
        Message msg = parseMessage(message);
        handleMessage(msg);
    }
    
    @OnClose
    public void onClose() {
        connections.remove(userId);
        RedisSessionStore.removeSession(userId);
    }
    
    @OnError
    public void onError(Throwable error) {
        // 错误处理
    }
}
```

#### 消息分发器
```java
@Component
public class MessageDispatcher {
    
    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    
    public void dispatch(Message message) {
        // 发送到消息队列
        kafkaTemplate.send("message-topic", message.getReceiverId(), message);
    }
    
    @KafkaListener(topics = "message-topic")
    public void handleMessage(ConsumerRecord<String, Message> record) {
        String receiverId = record.key();
        Message message = record.value();
        
        ChatEndpoint endpoint = ChatEndpoint.getConnection(receiverId);
        if (endpoint != null) {
            endpoint.sendMessage(message);
        } else {
            // 存储离线消息
            OfflineMessageStore.save(receiverId, message);
        }
    }
}
```

### 消息协议设计
```json
{
  "type": "CHAT_MESSAGE",
  "timestamp": 1698732456789,
  "senderId": "user123",
  "receiverId": "user456",
  "content": {
    "text": "Hello, how are you?",
    "attachments": [
      {
        "type": "IMAGE",
        "url": "https://example.com/image.jpg"
      }
    ]
  },
  "metadata": {
    "priority": "NORMAL",
    "ttl": 86400
  }
}
```

### 性能优化

1. **连接管理**
```java
// 连接池配置
@Bean
public TomcatServletWebServerFactory tomcatFactory() {
    return new TomcatServletWebServerFactory() {
        @Override
        protected void postProcessContext(Context context) {
            context.setSessionTimeout(30, TimeUnit.MINUTES);
            context.setManager(new PersistentManager());
        }
    };
}

// 最大连接数限制
server:
  max-connections: 10000
  max-http-post-size: 10MB
```

2. **消息压缩**
```java
// Gzip压缩
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat")
                .setAllowedOrigins("*")
                .withSockJS()
                .setMessageCodec(new GzipSockJsMessageCodec());
    }
}
```

3. **批量处理**
```java
// 批量发送
public void batchSend(List<Message> messages) {
    messages.stream()
            .collect(Collectors.groupingBy(Message::getReceiverId))
            .forEach((receiverId, msgList) -> {
                ChatEndpoint endpoint = getConnection(receiverId);
                if (endpoint != null) {
                    endpoint.sendBatch(msgList);
                }
            });
}
```

### 容错机制

1. **断线重连**
```javascript
// 客户端重连逻辑
function connect() {
    const socket = new WebSocket('wss://example.com/chat');
    
    socket.onclose = () => {
        setTimeout(connect, 5000); // 5秒后重连
    };
    
    socket.onerror = (error) => {
        console.error('WebSocket error:', error);
    };
}

connect();
```

2. **消息确认**
```java
// 消息确认机制
public void sendMessageWithAck(Message message) {
    String messageId = UUID.randomUUID().toString();
    PendingMessageStore.save(messageId, message);
    
    send(message);
    
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.schedule(() -> {
        if (!PendingMessageStore.isAcknowledged(messageId)) {
            resend(message);
        }
    }, 10, TimeUnit.SECONDS);
}

@OnMessage
public void onAck(String ackMessage) {
    Ack ack = parseAck(ackMessage);
    PendingMessageStore.markAcknowledged(ack.getMessageId());
}
```

### 监控指标

1. **关键指标**
```yaml
metrics:
  websocket:
    connections:
      total: ${websocket.connections.total}
      active: ${websocket.connections.active}
    messages:
      received: ${websocket.messages.received}
      sent: ${websocket.messages.sent}
      failed: ${websocket.messages.failed}
    latency:
      avg: ${websocket.latency.avg}
      p95: ${websocket.latency.p95}
```

2. **告警规则**
```yaml
alerts:
  - name: ConnectionFailureRate
    threshold: 1%
    duration: 5m
    notification:
      email: ops@example.com
      sms: +8613800138000
  
  - name: MessageLatency
    threshold: 500ms
    duration: 1m
    notification:
      pagerduty: true
```

3. **监控面板**
```json
{
  "panels": [
    {
      "title": "Active Connections",
      "type": "graph",
      "targets": [
        {
          "query": "sum(websocket_connections_active)",
          "refId": "A"
        }
      ]
    },
    {
      "title": "Message Throughput",
      "type": "singlestat",
      "targets": [
        {
          "query": "rate(websocket_messages_sent[5m])",
          "refId": "B"
        }
      ]
    }
  ]
}
```

### 性能数据
| 场景 | 并发连接数 | 消息吞吐量 | 平均延迟(ms) | 成功率 |
| --- | --- | --- | --- | --- |
| 单机 | 5000 | 10000/s | 50 | 99.9% |
| 集群 | 50000 | 100000/s | 80 | 99.99% |

### 最佳实践

1. **连接复用**
```java
// 连接池实现
public class ConnectionPool {
    
    private final Map<String, WebSocketSession> pool = new ConcurrentHashMap<>();
    
    public WebSocketSession borrowConnection(String userId) {
        return pool.computeIfAbsent(userId, id -> createNewConnection(id));
    }
    
    public void returnConnection(String userId, WebSocketSession session) {
        if (session.isOpen()) {
            pool.put(userId, session);
        } else {
            pool.remove(userId);
        }
    }
}
```

2. **消息分片**
```java
// 大消息分片
public List<MessageChunk> splitMessage(Message message) {
    int chunkSize = 1024; // 1KB
    List<MessageChunk> chunks = new ArrayList<>();
    
    byte[] payload = serialize(message);
    for (int i = 0; i < payload.length; i += chunkSize) {
        int end = Math.min(i + chunkSize, payload.length);
        chunks.add(new MessageChunk(
            message.getId(),
            chunks.size(),
            chunks.size() == ((payload.length - 1) / chunkSize),
            Arrays.copyOfRange(payload, i, end)
        ));
    }
    
    return chunks;
}
```

3. **流量控制**
```java
// 流控算法
public class FlowControl {
    
    private final RateLimiter rateLimiter = RateLimiter.create(1000); // 1000 QPS
    
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
    
    public void acquire() throws InterruptedException {
        rateLimiter.acquire();
    }
}
