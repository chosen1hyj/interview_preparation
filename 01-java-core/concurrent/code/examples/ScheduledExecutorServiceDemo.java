import java.util.concurrent.*;
import java.util.*;

public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 延迟执行示例
        System.out.println("=== 延迟执行 ===");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        Runnable task1 = () -> System.out.println("延迟任务执行: " + new Date());
        
        System.out.println("任务提交时间: " + new Date());
        scheduler.schedule(task1, 3, TimeUnit.SECONDS);
        Thread.sleep(5000); // 等待任务完成
        scheduler.shutdown();
        
        
        // 固定频率执行示例
        System.out.println("\n=== 固定频率执行 ===");
        ScheduledExecutorService fixedRateScheduler = Executors.newScheduledThreadPool(2);
        
        Runnable periodicTask = () -> {
            System.out.println("周期任务执行: " + new Date());
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        };
        
        fixedRateScheduler.scheduleAtFixedRate(periodicTask, 1, 2, TimeUnit.SECONDS);
        Thread.sleep(10000); // 运行一段时间
        fixedRateScheduler.shutdown();
        
        
        // 固定延迟执行示例
        System.out.println("\n=== 固定延迟执行 ===");
        ScheduledExecutorService fixedDelayScheduler = Executors.newScheduledThreadPool(2);
        
        Runnable delayedTask = () -> {
            System.out.println("延迟任务执行: " + new Date());
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
        };
        
        fixedDelayScheduler.scheduleWithFixedDelay(delayedTask, 1, 2, TimeUnit.SECONDS);
        Thread.sleep(10000); // 运行一段时间
        fixedDelayScheduler.shutdown();
    }
}
