# 并发设计模式

## 1. 单例模式

### 双重检查锁定
```java
public class Singleton {
    private volatile static Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) { // 第一次检查
            synchronized (Singleton.class) {
                if (instance == null) { // 第二次检查
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

特点：
- 线程安全
- 延迟加载
- 高效访问

## 2. 不变模式

### 实现要点
```java
public final class ImmutableObject {
    private final int value;
    private final String name;
    
    public ImmutableObject(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
}
```

关键特性：
- 类声明为final
- 所有字段final
- 没有setter方法
- 构造函数私有化

## 3. 生产者-消费者模式

### BlockingQueue实现
```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

// 生产者
Runnable producer = () -> {
    try {
        while (true) {
            String item = produceItem();
            queue.put(item);
            System.out.println("Produced: " + item);
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
};

// 消费者
Runnable consumer = () -> {
    try {
        while (true) {
            String item = queue.take();
            processItem(item);
            System.out.println("Consumed: " + item);
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
};
```

优势：
- 解耦生产与消费
- 流量控制
- 异步处理

## 4. 读写锁模式

### 使用场景
```java
ReadWriteLock lock = new ReentrantReadWriteLock();
Map<String, String> cache = new HashMap<>();

// 读操作
String read(String key) {
    lock.readLock().lock();
    try {
        return cache.get(key);
    } finally {
        lock.readLock().unlock();
    }
}

// 写操作
void write(String key, String value) {
    lock.writeLock().lock();
    try {
        cache.put(key, value);
    } finally {
        lock.writeLock().unlock();
    }
}
```

适用场景：
- 读多写少
- 数据一致性要求
- 提高并发性能
