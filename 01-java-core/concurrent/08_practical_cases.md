# 实际案例

## 1. 高并发秒杀系统

### 系统架构
```
+-------------------+       +-------------------+       +-------------------+
|   Web Server      | <---> |   API Gateway     | <---> |   Business Logic   |
| (Nginx/Tomcat)    |       | (Rate Limiting)   |       | (Redis/DB)         |
+-------------------+       +-------------------+       +-------------------+
        ^                           ^                           ^
        |                           |                           |
+-------------------+       +-------------------+       +-------------------+
|   CDN             |       |   Redis Cluster   |       |   MySQL Cluster   |
| (Static Resources)|       | (Inventory Cache) |       | (Order Storage)   |
+-------------------+       +-------------------+       +-------------------+
```

### 核心流程
```java
// 库存扣减
public class SeckillService {
    
    private final StringRedisTemplate redisTemplate;
    private final OrderService orderService;
    
    public Result seckill(String userId, String productId) {
        String stockKey = "product:stock:" + productId;
        
        // 1. 扣减库存
        Long stock = redisTemplate.opsForValue().decrement(stockKey);
        if (stock == null || stock < 0) {
            redisTemplate.opsForValue().increment(stockKey); // 回滚
            return Result.fail("商品已售罄");
        }
        
        try {
            // 2. 创建订单
            orderService.createOrder(userId, productId);
            return Result.success("秒杀成功");
        } catch (Exception e) {
            redisTemplate.opsForValue().increment(stockKey); // 异常回滚
            return Result.fail("系统异常");
        }
    }
}
```

### 关键设计点
1. **限流策略**
```java
// 令牌桶算法
@RateLimiter(name = "seckill", permitsPerSecond = 100)
@PostMapping("/seckill")
public Result doSeckill(@RequestBody SeckillRequest request) {
    // 业务逻辑
}
```

2. **缓存穿透防护**
```java
// 缓存空值
public Product getProduct(String productId) {
    String cacheKey = "product:" + productId;
    Product product = redisCache.get(cacheKey);
    if (product != null) {
        return product;
    }
    
    // 查询数据库
    product = productMapper.selectById(productId);
    if (product == null) {
        redisCache.set(cacheKey, "", 60); // 缓存空值
        return null;
    }
    
    redisCache.set(cacheKey, product, 3600);
    return product;
}
```

3. **分布式锁**
```java
// Redis分布式锁
public boolean tryLock(String key, String value, long expire) {
    Boolean success = redisTemplate.opsForValue()
        .setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    return Boolean.TRUE.equals(success);
}

public void unlock(String key, String value) {
    String currentValue = redisTemplate.opsForValue().get(key);
    if (value.equals(currentValue)) {
        redisTemplate.delete(key);
    }
}
```

### 性能数据
| 场景 | QPS | 响应时间(ms) | 成功率 |
| --- | --- | --- | --- |
| 单机 | 1000 | 50 | 99% |
| 集群 | 5000 | 80 | 99.9% |

## 2. 分布式任务调度

### 系统架构
```
+-------------------+       +-------------------+       +-------------------+
|   Admin Console   | <---> |   Scheduler Node  | <---> |   Worker Node     |
| (任务管理)        |       | (任务分发)        |       | (任务执行)        |
+-------------------+       +-------------------+       +-------------------+
        ^                           ^                           ^
        |                           |                           |
+-------------------+       +-------------------+       +-------------------+
|   DB Cluster      |       |   Registry Center |       |   Execution Logs  |
| (任务存储)        |       | (服务发现)        |       | (日志收集)        |
+-------------------+       +-------------------+       +-------------------+
```

### 核心实现
```java
// 动态任务注册
@Component
public class DynamicTaskRegistrar implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<TaskConfig> tasks = taskConfigRepository.findAllEnabled();
        for (TaskConfig task : tasks) {
            ScheduledFuture<?> future = taskScheduler.schedule(
                () -> executeTask(task),
                new CronTrigger(task.getCronExpression())
            );
            task.setFuture(future);
        }
    }
    
    private void executeTask(TaskConfig task) {
        try {
            task.getTaskExecutor().execute();
            task.setLastExecutionTime(new Date());
            task.setStatus(TaskStatus.SUCCESS);
        } catch (Exception e) {
            task.setStatus(TaskStatus.FAILED);
            task.setErrorMsg(e.getMessage());
        } finally {
            taskRepository.save(task);
        }
    }
}
```

### 容错机制
1. **失败重试**
```java
// 指数退避重试
@Retryable(
    value = {TaskExecutionException.class},
    maxAttempts = 5,
    backoff = @Backoff(delay = 1000, multiplier = 2)
)
public void executeWithRetry() {
    // 任务逻辑
}
```

2. **死信队列**
```java
// 死信处理
@RabbitListener(queues = "dead-letter-queue")
public void handleDeadLetter(Message message) {
    // 记录错误
    // 人工介入
}
```

3. **熔断保护**
```java
// Hystrix熔断器
@HystrixCommand(
    fallbackMethod = "fallback",
    commandProperties = {
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    }
)
public void executeTask() {
    // 任务逻辑
}

public void fallback() {
    // 降级处理
}
```

### 运维监控
1. **关键指标**
```yaml
metrics:
  enabled: true
  endpoints:
    - task.count
    - task.duration
    - task.failure.rate
    - thread.pool.usage
```

2. **告警规则**
```yaml
alerts:
  - name: TaskFailureRate
    threshold: 5%
    duration: 5m
    notification:
      email: ops@example.com
      sms: +8613800138000
```

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

## 4. 实时消息推送

### 系统架构
```
+-------------------+       +-------------------+       +-------------------+
|   Client          | <---> |   WebSocket       | <---> |   Message Broker  |
| (Browser/Mobile)  |       | (Session Manager) |       | (Kafka/RabbitMQ)  |
+-------------------+       +-------------------+       +-------------------+
        ^                           ^                           ^
        |                           |                           |
+-------------------+       +-------------------+       +-------------------
