# 线程池与Executor框架

## 1. 线程池原理

### 核心概念
- 线程复用机制
- 工作队列（Work Queue）
- 核心线程数与最大线程数
- 拒绝策略

### 使用线程池的好处
- 减少线程创建和销毁开销
- 控制并发线程数量
- 提高响应速度
- 统一管理资源

## 2. ExecutorService接口

### 常见实现类
```java
// 固定大小线程池
ExecutorService fixedPool = Executors.newFixedThreadPool(5);

// 缓存线程池
ExecutorService cachedPool = Executors.newCachedThreadPool();

// 单线程执行器
ExecutorService singlePool = Executors.newSingleThreadExecutor();
```

### 主要方法
- execute(Runnable command)
- submit(Callable<T> task)
- invokeAll(Collection<? extends Callable<T>> tasks)
- shutdown()
- shutdownNow()

## 3. ThreadPoolExecutor详解

### 构造参数
```java
public ThreadPoolExecutor(
    int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    ThreadFactory threadFactory,
    RejectedExecutionHandler handler)
```

参数说明：
- corePoolSize: 核心线程数
- maximumPoolSize: 最大线程数
- keepAliveTime: 空闲线程存活时间
- unit: 时间单位
- workQueue: 工作队列
- threadFactory: 线程工厂
- handler: 拒绝策略

### 工作流程
1. 当前运行线程数 < corePoolSize
   - 创建新线程处理任务
2. 当前运行线程数 ≥ corePoolSize
   - 将任务加入工作队列
3. 工作队列已满且运行线程数 < maximumPoolSize
   - 创建新线程处理任务
4. 工作队列已满且运行线程数 ≥ maximumPoolSize
   - 执行拒绝策略

## 4. ScheduledExecutorService

### 定时任务
```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

// 延迟执行
scheduler.schedule(() -> {
    // 任务代码
}, 5, TimeUnit.SECONDS);

// 周期执行
scheduler.scheduleAtFixedRate(() -> {
    // 任务代码
}, 2, 5, TimeUnit.SECONDS);
```

特点：
- 支持延迟执行
- 支持周期性任务
- 更精确的时间控制

## 5. 实战建议
- 合理设置线程池参数
- 注意线程安全问题
- 正确处理异常
- 及时关闭线程池
