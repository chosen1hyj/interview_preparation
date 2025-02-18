import java.util.concurrent.locks.*;

public class LockMechanismDemo {

    public static void main(String[] args) throws InterruptedException {
        // ReentrantLock 示例
        System.out.println("=== ReentrantLock 示例 ===");
        ReentrantLock lock = new ReentrantLock();
        int[] counter = {0};
        
        Runnable task = () -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    counter[0]++;
                    System.out.println(Thread.currentThread().getName() + ": " + counter[0]);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        };
        
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        
        // ReadWriteLock 示例
        System.out.println("\n=== ReadWriteLock 示例 ===");
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();
        
        int[] data = {0};
        
        Runnable reader = () -> {
            readLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 读取数据: " + data[0]);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                readLock.unlock();
            }
        };
        
        Runnable writer = () -> {
            writeLock.lock();
            try {
                data[0]++;
                System.out.println(Thread.currentThread().getName() + " 写入数据: " + data[0]);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                writeLock.unlock();
            }
        };
        
        Thread reader1 = new Thread(reader);
        Thread reader2 = new Thread(reader);
        Thread writerThread = new Thread(writer);
        
        reader1.start();
        reader2.start();
        writerThread.start();
        
        reader1.join();
        reader2.join();
        writerThread.join();
    }
}
