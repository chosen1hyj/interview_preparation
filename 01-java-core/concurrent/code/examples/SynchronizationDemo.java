import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizationDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. 演示线程不安全
        Counter unsafeCounter = new UnsafeCounter();
        testCounter("线程不安全计数器", unsafeCounter);

        // 2. 演示synchronized
        Counter syncCounter = new SynchronizedCounter();
        testCounter("synchronized计数器", syncCounter);

        // 3. 演示volatile（注意：volatile不能保证复合操作的原子性）
        Counter volatileCounter = new VolatileCounter();
        testCounter("volatile计数器", volatileCounter);

        // 4. 演示ReentrantLock
        Counter lockCounter = new LockCounter();
        testCounter("ReentrantLock计数器", lockCounter);

        // 5. 演示原子类
        Counter atomicCounter = new AtomicCounter();
        testCounter("Atomic计数器", atomicCounter);

        // 6. 演示ReadWriteLock
        ReadWriteCounter rwCounter = new ReadWriteCounter();
        testReadWriteCounter(rwCounter);
    }

    // 测试计数器
    private static void testCounter(String name, Counter counter) throws InterruptedException {
        System.out.println("\n测试" + name + "==========");
        Thread[] threads = new Thread[10];
        
        // 创建10个线程，每个线程对计数器增加1000次
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("预期结果：10000");
        System.out.println("实际结果：" + counter.getCount());
    }

    // 测试读写锁
    private static void testReadWriteCounter(ReadWriteCounter counter) throws InterruptedException {
        System.out.println("\n测试ReadWriteLock==========");
        Thread[] readThreads = new Thread[3];
        Thread[] writeThreads = new Thread[2];

        // 创建写线程
        for (int i = 0; i < writeThreads.length; i++) {
            writeThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.increment();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            writeThreads[i].start();
        }

        // 创建读线程
        for (int i = 0; i < readThreads.length; i++) {
            readThreads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    System.out.println("读取值：" + counter.getCount());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThreads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : writeThreads) {
            thread.join();
        }
        for (Thread thread : readThreads) {
            thread.join();
        }
    }
}

// 接口定义
interface Counter {
    void increment();
    int getCount();
}

// 1. 线程不安全的计数器
class UnsafeCounter implements Counter {
    private int count = 0;

    @Override
    public void increment() {
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }
}

// 2. 使用synchronized的计数器
class SynchronizedCounter implements Counter {
    private int count = 0;

    @Override
    public synchronized void increment() {
        count++;
    }

    @Override
    public synchronized int getCount() {
        return count;
    }
}

// 3. 使用volatile的计数器（注意这个实现仍然是线程不安全的）
class VolatileCounter implements Counter {
    private volatile int count = 0;

    @Override
    public void increment() {
        count++; // 非原子操作
    }

    @Override
    public int getCount() {
        return count;
    }
}

// 4. 使用ReentrantLock的计数器
class LockCounter implements Counter {
    private final Lock lock = new ReentrantLock();
    private int count = 0;

    @Override
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}

// 5. 使用AtomicInteger的计数器
class AtomicCounter implements Counter {
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void increment() {
        count.incrementAndGet();
    }

    @Override
    public int getCount() {
        return count.get();
    }
}

// 6. 使用ReadWriteLock的计数器
class ReadWriteCounter {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int count = 0;

    public void increment() {
        lock.writeLock().lock();
        try {
            count++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}
