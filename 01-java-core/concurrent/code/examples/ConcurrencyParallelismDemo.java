import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyParallelismDemo {
    public static void main(String[] args) throws InterruptedException {
        // 演示并发（Concurrency）
        System.out.println("=== 并发执行示例 ===");
        // 创建单线程执行器，强制任务串行执行
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        long start = System.currentTimeMillis();
        
        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            singleThread.submit(() -> {
                System.out.println("并发任务" + taskId + "开始，线程：" + Thread.currentThread().getName());
                try {
                    // 模拟任务执行
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("并发任务" + taskId + "结束");
            });
        }
        
        singleThread.shutdown();
        singleThread.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("并发执行总时间：" + (System.currentTimeMillis() - start) + "ms\n");

        // 演示并行（Parallelism）
        System.out.println("=== 并行执行示例 ===");
        // 创建固定大小的线程池，实现真正的并行
        ExecutorService multiThread = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() // 使用CPU核心数
        );
        start = System.currentTimeMillis();
        
        // 提交多个任务
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            multiThread.submit(() -> {
                System.out.println("并行任务" + taskId + "开始，线程：" + Thread.currentThread().getName());
                try {
                    // 模拟任务执行
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("并行任务" + taskId + "结束");
            });
        }
        
        multiThread.shutdown();
        multiThread.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("并行执行总时间：" + (System.currentTimeMillis() - start) + "ms");
        
        // 输出CPU核心数
        System.out.println("\nCPU核心数：" + Runtime.getRuntime().availableProcessors());
    }
}
