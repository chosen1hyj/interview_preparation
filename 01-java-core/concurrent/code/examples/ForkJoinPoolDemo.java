import java.util.concurrent.*;
import java.util.*;

public class ForkJoinPoolDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 计算 1 到 1000 的和
        System.out.println("=== 计算 1 到 1000 的和 ===");
        ForkJoinPool pool = new ForkJoinPool();
        
        long result = pool.invoke(new SumTask(1, 1000));
        System.out.println("计算结果: " + result);
        
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }
    
    // 定义递归任务
    static class SumTask extends RecursiveTask<Long> {
        private final long start;
        private final long end;
        
        SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= 100) {
                // 小任务直接计算
                long sum = 0;
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            } else {
                // 大任务拆分
                long mid = (start + end) / 2;
                SumTask leftTask = new SumTask(start, mid);
                SumTask rightTask = new SumTask(mid + 1, end);
                
                leftTask.fork(); // 异步执行
                return rightTask.compute() + leftTask.join();
            }
        }
    }
}
