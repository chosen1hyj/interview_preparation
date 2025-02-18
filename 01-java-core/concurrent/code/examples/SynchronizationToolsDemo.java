import java.util.concurrent.*;

public class SynchronizationToolsDemo {

    public static void main(String[] args) throws InterruptedException {
        // CountDownLatch 示例
        System.out.println("=== CountDownLatch 示例 ===");
        int threadCount = 3;
        CountDownLatch latch = new CountDownLatch(threadCount);
        
        Runnable worker = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 开始工作");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 完成工作");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        };
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(worker).start();
        }
        
        latch.await();
        System.out.println("所有线程完成工作");
        
        
        // CyclicBarrier 示例
        System.out.println("\n=== CyclicBarrier 示例 ===");
        int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties, () -> 
            System.out.println("所有线程到达屏障点")
        );
        
        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 到达屏障点");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " 继续执行");
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        };
        
        for (int i = 0; i < parties; i++) {
            new Thread(task).start();
            Thread.sleep(500);
        }
    }
}
