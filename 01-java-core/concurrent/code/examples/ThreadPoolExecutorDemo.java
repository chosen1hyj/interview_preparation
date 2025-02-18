import java.util.concurrent.*;
import java.util.*;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) throws InterruptedException {
        // 固定大小线程池示例
        System.out.println("=== 固定大小线程池 ===");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            fixedPool.execute(() -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            });
        }
        fixedPool.shutdown();
        fixedPool.awaitTermination(5, TimeUnit.SECONDS);
        
        
        // 缓存线程池示例
        System.out.println("\n=== 缓存线程池 ===");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            cachedPool.submit(() -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            });
        }
        cachedPool.shutdown();
        cachedPool.awaitTermination(5, TimeUnit.SECONDS);
        
        
        // 自定义线程池示例
        System.out.println("\n=== 自定义线程池 ===");
        ThreadPoolExecutor customPool = new ThreadPoolExecutor(
            2, // corePoolSize
            4, // maximumPoolSize
            10, // keepAliveTime
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(2), // workQueue
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );
        
        for (int i = 0; i < 6; i++) {
            int taskId = i;
            try {
                customPool.execute(() -> {
                    System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                });
            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " 被拒绝");
            }
        }
        customPool.shutdown();
        customPool.awaitTermination(10, TimeUnit.SECONDS);
    }
}
