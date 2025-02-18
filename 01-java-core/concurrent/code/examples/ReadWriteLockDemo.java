import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.*;

public class ReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();
        
        Runnable reader = () -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String key = "key" + (new Random().nextInt(3) + 1);
                    String value = cache.read(key);
                    System.out.println(Thread.currentThread().getName() + " 读取: " + key + "=" + value);
                    Thread.sleep(new Random().nextInt(1000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Runnable writer = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    String key = "key" + (i + 1);
                    String value = "value" + (i + 1);
                    cache.write(key, value);
                    System.out.println(Thread.currentThread().getName() + " 写入: " + key + "=" + value);
                    Thread.sleep(new Random().nextInt(2000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        ExecutorService pool = Executors.newFixedThreadPool(5);
        
        // 启动多个读线程
        for (int i = 0; i < 3; i++) {
            pool.submit(reader);
        }
        
        // 启动一个写线程
        pool.submit(writer);
        
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }
}

class Cache {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<String, String> map = new HashMap<>();
    
    public String read(String key) {
        lock.readLock().lock();
        try {
            simulateLatency();
            return map.getOrDefault(key, "null");
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void write(String key, String value) {
        lock.writeLock().lock();
        try {
            simulateLatency();
            map.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    private void simulateLatency() {
        try { Thread.sleep(new Random().nextInt(500)); } catch (InterruptedException e) {}
    }
}
