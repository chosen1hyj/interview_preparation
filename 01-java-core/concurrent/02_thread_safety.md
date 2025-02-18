# Java线程安全与同步机制

## 线程安全的概念

### 什么是线程安全
- 当多个线程同时访问一个对象时，如果不用考虑这些线程在运行时环境下的调度和交替执行，也不需要进行额外的同步，或者在调用方进行任何其他的协调操作，调用这个对象的行为都可以获得正确的结果，那这个对象是线程安全的。

### 线程不安全示例
```java
public class ThreadUnsafeExample {
    private int count = 0;
    
    public void increment() {
        count++; // 非原子操作
    }
    
    public int getCount() {
        return count;
    }
}
```

### 线程安全实现方式
1. 互斥同步（Synchronization）
2. 非阻塞同步（CAS）
3. 无同步方案（线程本地存储、不可变对象）

## synchronized关键字

### 基本使用方式
1. **修饰实例方法**
```java
public synchronized void method() {
    // 方法体
}
```

2. **修饰静态方法**
```java
public static synchronized void method() {
    // 方法体
}
```

3. **同步代码块**
```java
synchronized (object) {
    // 同步代码块
}
```

### synchronized的实现原理
1. **对象锁**
   - Java对象头的Mark Word
   - 重量级锁、轻量级锁、偏向锁
   - 锁的升级过程

2. **同步原理**
   - 监视器（Monitor）机制
   - 进入和退出Monitor对象
   - 线程之间的互斥与同步

### synchronized的优化
1. **锁消除**：编译器优化
2. **锁粗化**：合并相邻同步块
3. **自旋锁**：避免线程切换
4. **锁升级**：从偏向锁到轻量级锁再到重量级锁

## volatile关键字

### 作用
1. **保证可见性**：一个线程修改了变量值，其他线程能够立即看到
2. **禁止指令重排**：保证有序性

### 实现原理
1. **内存屏障**：
   - LoadLoad屏障
   - StoreStore屏障
   - LoadStore屏障
   - StoreLoad屏障

2. **MESI缓存一致性协议**

### 适用场景
1. 状态标志位
2. 双重检查锁定（DCL）
3. 简单的读写操作

```java
public class VolatileExample {
    private volatile boolean flag = false;
    
    public void setFlag() {
        flag = true; // 写操作
    }
    
    public boolean isFlag() {
        return flag; // 读操作
    }
}
```

## CAS机制

### 原理
- Compare And Swap（比较并交换）
- 原子性操作
- ABA问题

### 实现方式
```java
public class CASExample {
    private AtomicInteger value = new AtomicInteger(0);
    
    public void increment() {
        int oldValue;
        int newValue;
        do {
            oldValue = value.get();
            newValue = oldValue + 1;
        } while (!value.compareAndSet(oldValue, newValue));
    }
}
```

### CAS的优缺点
优点：
- 无锁操作，性能好
- 避免线程切换

缺点：
- CPU占用高
- ABA问题
- 只能保证单个变量的原子性

## 锁机制

### ReentrantLock
```java
public class ReentrantLockExample {
    private ReentrantLock lock = new ReentrantLock();
    
    public void method() {
        lock.lock();
        try {
            // 临界区代码
        } finally {
            lock.unlock();
        }
    }
}
```

特点：
1. 可重入性
2. 公平性选择
3. 限时等待
4. 可中断

### ReadWriteLock
```java
public class ReadWriteLockExample {
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    public void read() {
        rwLock.readLock().lock();
        try {
            // 读操作
        } finally {
            rwLock.readLock().unlock();
        }
    }
    
    public void write() {
        rwLock.writeLock().lock();
        try {
            // 写操作
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
```

特点：
1. 读写分离
2. 写独占，读共享
3. 适合读多写少场景

### StampedLock
- 支持乐观读
- 三种模式：写、读、乐观读
- 性能更好

## ThreadLocal原理

### 实现原理
1. **ThreadLocalMap**
   - Thread类中的成员变量
   - Entry继承自WeakReference

2. **内存结构**
   - 每个线程独立的存储空间
   - key是ThreadLocal对象的弱引用
   - value是线程的变量副本

### 常见应用
1. 线程隔离
2. 上下文传递
3. 数据库连接管理

```java
public class ThreadLocalExample {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    
    public void setContext(String value) {
        threadLocal.set(value);
    }
    
    public String getContext() {
        return threadLocal.get();
    }
    
    public void clear() {
        threadLocal.remove(); // 防止内存泄漏
    }
}
```

## 面试高频问题

1. **synchronized和volatile的区别？**
   - synchronized解决原子性、可见性、有序性
   - volatile只解决可见性和有序性
   - synchronized是重量级锁机制
   - volatile是轻量级同步机制

2. **synchronized和ReentrantLock的区别？**
   - synchronized是关键字，ReentrantLock是类
   - ReentrantLock功能更丰富（可中断、限时、公平性）
   - ReentrantLock需要手动释放锁
   - synchronized自动释放锁

3. **volatile的实现原理？**
   - 内存屏障保证可见性和有序性
   - MESI缓存一致性协议
   - CPU级别的内存栅栏指令

4. **ThreadLocal的实现原理和内存泄漏问题**
   - ThreadLocalMap的结构
   - 弱引用和内存泄漏的关系
   - 使用完及时remove的重要性

## 最佳实践建议

1. **选择合适的同步方式**
   - 优先使用并发集合而不是同步集合
   - 优先使用原子类而不是synchronized
   - 使用ThreadLocal要注意内存泄漏
   - 适当的锁粒度

2. **性能优化**
   - 减少锁的持有时间
   - 降低锁的粒度
   - 读写分离（ReadWriteLock）
   - 避免嵌套锁

3. **代码规范**
   - 正确释放锁
   - 规范的锁获取顺序
   - 合理的锁超时时间
   - 良好的异常处理

4. **调试方案**
   - 使用jstack分析死锁
   - 使用JMH进行性能测试
   - 使用线程dump分析问题
