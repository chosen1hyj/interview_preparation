import java.util.concurrent.*;
import java.util.*;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException {
        // 基本使用
        System.out.println("=== 基本使用 ===");
        CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return "Hello";
        }).thenApply(result -> result + " World")
          .thenAccept(System.out::println)
          .exceptionally(ex -> {
              ex.printStackTrace();
              return null;
          });
        
        Thread.sleep(2000);
        
        
        // 组合多个任务
        System.out.println("\n=== 组合多个任务 ===");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return "Task1";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            return "Task2";
        });
        
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
        combinedFuture.thenRun(() -> {
            try {
                System.out.println(future1.get() + " and " + future2.get() + " completed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        Thread.sleep(3000);
    }
}
