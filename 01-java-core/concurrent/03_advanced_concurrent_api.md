# 高级并发API

## 1. 并发集合

### ConcurrentHashMap
```java
// 基本用法示例
Map<String, String> map = new ConcurrentHashMap<>();
map.put("key", "value");
String value = map.get("key");
```

特点：
- 线程安全的哈希表实现
- 分段锁机制提高并发性能
- 不会出现ConcurrentModificationException

常见方法：
- putIfAbsent(K key, V value)
- computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
- forEach(BiConsumer<? super K,? super V> action)

### CopyOnWriteArrayList
```java
// 基本用法示例
List<String> list = new CopyOnWriteArrayList<>();
list.add("item");
list.forEach(item -> System.out.println(item));
```

特点：
- 适用于读多写少场景
- 写操作时复制整个数组
- 迭代器不支持修改操作

适用场景：
- 事件监听器列表
- 缓存数据

## 2. 同步工具类

### CountDownLatch
```java
// 示例：等待多个线程完成
CountDownLatch latch = new CountDownLatch(3);

Runnable task = () -> {
    try {
        // 执行任务
    } finally {
        latch.countDown();
    }
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();

latch.await(); // 等待所有任务完成
```

使用场景：
- 主线程等待多个子线程完成
- 初始化资源等待

### CyclicBarrier
```java
// 示例：多个线程相互等待
CyclicBarrier barrier = new CyclicBarrier(3);

Runnable worker = () -> {
    try {
        // 执行部分工作
        barrier.await();
        // 继续执行
    } catch (Exception e) {
        Thread.currentThread().interrupt();
    }
};

new Thread(worker).start();
new Thread(worker).start();
new Thread(worker).start();
```

特点：
- 可重复使用
- 支持更复杂的同步需求

## 3. 锁机制

### ReentrantLock
```java
// 基本用法示例
ReentrantLock lock = new ReentrantLock();

lock.lock();
try {
    // 临界区代码
} finally {
    lock.unlock();
}
```

优势：
- 支持公平锁
- 可中断的获取锁
- 超时获取锁
- 可尝试获取锁

### ReadWriteLock
```java
// 示例：读写锁
ReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

// 读操作
readLock.lock();
try {
    // 读取数据
} finally {
    readLock.unlock();
}

// 写操作
writeLock.lock();
try {
    // 修改数据
} finally {
    writeLock.unlock();
}
```

使用场景：
- 读多写少的数据访问
- 提高并发性能
