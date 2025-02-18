# 并发优化

## 1. 性能调优

### 线程池参数优化
```java
// 核心参数设置
int corePoolSize = Runtime.getRuntime().availableProcessors();
int maxPoolSize = corePoolSize * 2;
long keepAliveTime = 60L;

ThreadPoolExecutor executor = new ThreadPoolExecutor(
    corePoolSize,
    maxPoolSize,
    keepAliveTime,
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(queueSize)
);
```

关键点：
- 合理设置核心线程数
- 控制最大线程数
- 适当调整队列大小
- 监控线程池状态

### 锁优化策略
- 减小锁粒度
- 使用读写锁替代独占锁
- 优先使用无锁算法
- 避免锁嵌套

### 减少上下文切换
- 减少线程数量
- 使用合适的阻塞策略
- 优化任务分配

## 2. 死锁预防

### 最佳实践
```java
// 统一资源获取顺序
void transfer(Account from, Account to, double amount) {
    Account first = from.accountId < to.accountId ? from : to;
    Account second = from.accountId < to.accountId ? to : from;
    
    synchronized (first) {
        synchronized (second) {
            // 执行转账逻辑
        }
    }
}
```

预防措施：
- 统一资源获取顺序
- 设置锁超时时间
- 使用tryLock()
- 定期检查死锁

## 3. 内存模型优化

### volatile使用场景
- 状态标志位
- 双重检查锁定
- 发布共享对象

### 原子类选择
```java
AtomicInteger counter = new AtomicInteger(0);

// CAS操作
counter.incrementAndGet();
```

优势：
- 无锁实现
- 高性能
- 线程安全
