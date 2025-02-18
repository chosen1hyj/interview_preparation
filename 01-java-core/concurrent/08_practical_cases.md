# 实际案例

## 1. 高并发秒杀系统

### 核心要点
```java
// 库存扣减
boolean success = redis.decrementIfPositive("product:stock:" + productId);
if (success) {
    // 创建订单
    orderService.createOrder(userId, productId);
} else {
    throw new SoldOutException("商品已售罄");
}
```

关键点：
- 使用Redis分布式锁
- 异步处理订单
- 缓存库存信息
- 限流与降级

## 2. 分布式任务调度

### 实现方案
```java
@Scheduled(cron = "0 0/5 * * * ?")
public void processTasks() {
    List<Task> tasks = taskRepository.findPendingTasks();
    tasks.parallelStream().forEach(task -> {
        try {
            task.execute();
        } catch (Exception e) {
            task.markFailed(e.getMessage());
        }
    });
}
```

特点：
- 定时任务调度
- 并行任务处理
- 失败重试机制
- 状态监控

## 3. 并发数据处理

### 数据分片
```java
int shardCount = 10;
int shardIndex = machineId % shardCount;

List<Data> dataBatch = dataRepository.getDataByShard(shardIndex, shardCount);

dataBatch.parallelStream().forEach(data -> {
    processData(data);
});
```

优势：
- 数据分区处理
- 负载均衡
- 故障隔离
- 水平扩展

## 4. 实时消息推送

### WebSocket实现
```java
@ServerEndpoint("/chat/{userId}")
public class ChatEndpoint {

    private static final Set<ChatEndpoint> connections = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        connections.add(this);
    }

    @OnMessage
    public void onMessage(String message) {
        broadcast(message);
    }

    private void broadcast(String message) {
        for (ChatEndpoint client : connections) {
            client.session.getAsyncRemote().sendText(message);
        }
    }
}
```

特性：
- 实时通信
- 长连接维护
- 消息广播
- 连接管理
