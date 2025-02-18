# 并发优化

## 1. 性能调优

### 线程池参数优化

#### 参数计算公式
- CPU密集型任务：
  - corePoolSize = CPU核心数 + 1
  - maxPoolSize = corePoolSize
  - queueSize = 较小值（如100）

- IO密集型任务：
  - corePoolSize = CPU核心数 * 2
  - maxPoolSize = corePoolSize * 2
  - queueSize = 较大值（如1000）

```java
// 动态计算线程池参数
int cpuCores = Runtime.getRuntime().availableProcessors();

ThreadPoolExecutor ioExecutor = new ThreadPoolExecutor(
    cpuCores * 2,     // 核心线程数
    cpuCores * 4,     // 最大线程数
    60L,              // 空闲线程存活时间
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(1000) // 队列大小
);

ThreadPoolExecutor cpuExecutor = new ThreadPoolExecutor(
    cpuCores + 1,     // 核心线程数
    cpuCores + 1,     // 最大线程数
    60L,              // 空闲线程存活时间
    TimeUnit.SECONDS,
    new LinkedBlockingQueue<>(100) // 队列大小
);
```

#### 调优步骤
1. 分析任务类型（CPU/IO密集）
2. 初始参数设置
3. 压力测试
4. 性能监控
5. 参数调整

#### 监控指标
- 活跃线程数
- 队列长度
- 任务执行时间
- 拒绝任务数
- 线程池使用率

#### 常见问题
- 队列过长导致内存溢出
- 线程数过多引发上下文切换
- 线程饥饿现象

### 锁优化策略

#### 锁选择策略
| 场景 | 推荐方案 | 优点 |
| --- | --- | --- |
| 读多写少 | ReentrantReadWriteLock | 提高并发性 |
| 简单状态更新 | AtomicInteger | 无锁实现 |
| 复杂业务逻辑 | synchronized | 可重入、简单 |
| 分布式环境 | Redis分布式锁 | 跨服务协调 |

#### 优化案例
```java
// 原始实现
public synchronized void updateCache() {
    // 缓存更新逻辑
}

// 优化后
private final ReadWriteLock lock = new ReentrantReadWriteLock();

public void updateCache() {
    lock.writeLock().lock();
    try {
        // 缓存更新逻辑
    } finally {
        lock.writeLock().unlock();
    }
}

public Object getFromCache(String key) {
    lock.readLock().lock();
    try {
        return cache.get(key);
    } finally {
        lock.readLock().unlock();
    }
}
```

#### 无锁优化
```java
// 原始实现
private int counter = 0;

public synchronized int incrementAndGet() {
    return ++counter;
}

// 优化后
private final AtomicInteger atomicCounter = new AtomicInteger(0);

public int incrementAndGet() {
    return atomicCounter.incrementAndGet();
}
```

### 减少上下文切换

#### 上下文切换成本
- CPU寄存器保存/恢复
- 程序计数器更新
- 栈信息切换
- TLB刷新

#### 优化策略
1. **控制线程数量**
```java
// 计算最佳线程数
int optimalThreads = availableProcessors * targetCpuUtilization 
                     * (1 + waitTime / computeTime);
```

2. **任务拆分**
```java
// 大任务拆分为小任务
ForkJoinPool pool = new ForkJoinPool();
pool.submit(() -> {
    List<Task> subTasks = split(task);
    subTasks.parallelStream().forEach(subTask -> process(subTask));
});
```

3. **阻塞优化**
```java
// 使用异步非阻塞API
CompletableFuture.supplyAsync(() -> blockingOperation())
    .thenApply(result -> process(result))
    .exceptionally(ex -> handleException(ex));
```

#### 性能对比
| 方案 | 上下文切换次数 | 执行时间(ms) |
| --- | --- | --- |
| 原始实现 | 1200 | 500 |
| 优化后 | 300 | 150 |

#### 监控工具
- jstack：查看线程状态
- pidstat：监控上下文切换
- perf：性能分析

## 2. 死锁预防

### 死锁成因分析
四个必要条件：
1. 互斥条件
2. 请求与保持
3. 不剥夺条件
4. 循环等待

#### 实际案例
```java
// 死锁示例
public class DeadLockDemo {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();
    
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock2) {
                    System.out.println("Thread 1完成");
                }
            }
        });
        
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock1) {
                    System.out.println("Thread 2完成");
                }
            }
        });
        
        t1.start();
        t2.start();
    }
}
```

### 预防措施

#### 1. 统一资源获取顺序
```java
// 安全的转账实现
void transfer(Account from, Account to, double amount) {
    Account first = from.accountId < to.accountId ? from : to;
    Account second = from.accountId < to.accountId ? to : from;
    
    synchronized (first) {
        synchronized (second) {
            if (from.balance >= amount) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }
}
```

#### 2. 锁超时机制
```java
// 使用tryLock实现
ReentrantLock lock1 = new ReentrantLock();
ReentrantLock lock2 = new ReentrantLock();

public void safeTransfer() {
    while (true) {
        if (lock1.tryLock()) {
            try {
                if (lock2.tryLock()) {
                    try {
                        // 执行关键逻辑
                        break;
                    } finally {
                        lock2.unlock();
                    }
                }
            } finally {
                lock1.unlock();
            }
        }
        // 等待重试
        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }
}
```

#### 3. 死锁检测
```java
// 死锁检测工具类
public class DeadlockDetector {
    private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    
    public void detectDeadlocks() {
        long[] deadlockedThreads = threadBean.findDeadlockedThreads();
        if (deadlockedThreads != null) {
            for (long threadId : deadlockedThreads) {
                ThreadInfo info = threadBean.getThreadInfo(threadId);
                System.out.println("发现死锁: " + info.getThreadName());
            }
        }
    }
}
```

### 最佳实践
1. 尽量减少锁的持有时间
2. 使用try-finally确保释放锁
3. 避免嵌套锁
4. 使用高级并发工具替代synchronized

## 3. 内存模型优化

### volatile使用场景

#### 典型应用
```java
// 状态标志位
class SharedResource {
    private volatile boolean initialized = false;
    
    public void initialize() {
        // 初始化逻辑
        initialized = true;
    }
    
    public boolean isInitialized() {
        return initialized;
    }
}

// 双重检查锁定
class Singleton {
    private volatile static Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

#### 注意事项
- 仅保证可见性
- 不保证原子性
- 适用于单一变量更新

### 原子类选择

#### 常用原子类
```java
// 原子整数
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();

// 原子引用
AtomicReference<String> ref = new AtomicReference<>("old");
ref.compareAndSet("old", "new");

// 原子数组
AtomicIntegerArray array = new AtomicIntegerArray(10);
array.incrementAndGet(0);

// 原子字段更新器
AtomicIntegerFieldUpdater<SomeClass> updater =
    AtomicIntegerFieldUpdater.newUpdater(SomeClass.class, "field");
updater.incrementAndGet(instance);
```

#### CAS操作原理
```java
// CAS操作过程
do {
    oldValue = value.get();
    newValue = compute(newValue);
} while (!value.compareAndSet(oldValue, newValue));
```

### 内存屏障优化

#### volatile语义
```java
// happens-before关系
volatile write -> volatile read

// 内存屏障
LoadLoad
StoreStore
LoadStore
StoreLoad
```

#### 缓存行填充
```java
// 防止伪共享
@Contended
class PaddedAtomicLong extends AtomicLong {
    public volatile long p1, p2, p3, p4, p5, p6, p7;
}
```

#### 性能对比
| 实现方式 | 单线程(ns) | 多线程(ns) |
| --- | --- | --- |
| synchronized | 50 | 500 |
| ReentrantLock | 30 | 300 |
| AtomicInteger | 10 | 50 |
| LongAdder | 15 | 20 |

### JVM优化技巧

#### 锁消除
```java
// 编译器优化
public String concat(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb.toString();
}
```

#### 锁粗化
```java
// 连续同步块合并
public void update() {
    synchronized (this) {
        // 第一段逻辑
    }
    
    // 中间代码
    
    synchronized (this) {
        // 第二段逻辑
    }
}
```

#### 偏向锁
```java
// 启用偏向锁
-XX:+UseBiasedLocking
-XX:BiasedLockingStartupDelay=0
```

### 性能监控

#### JVM工具
- jvisualvm
- jconsole
- jstack
- jmap
- jstat

#### GC日志
```bash
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xloggc:gc.log
```

#### 性能指标
- 吞吐量
- 响应时间
- 平均延迟
- 错误率
- CPU使用率
