import java.lang.management.ManagementFactory;

public class ProcessThreadDemo {
    public static void main(String[] args) {
        // 获取当前进程信息
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("进程ID：" + processName.split("@")[0]);
        
        // 获取当前进程的内存信息
        Runtime runtime = Runtime.getRuntime();
        System.out.println("进程最大可用内存：" + runtime.maxMemory() / 1024 / 1024 + "MB");
        System.out.println("进程总内存：" + runtime.totalMemory() / 1024 / 1024 + "MB");
        System.out.println("进程空闲内存：" + runtime.freeMemory() / 1024 / 1024 + "MB");

        // 创建并启动三个线程
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                // 获取当前线程信息
                Thread currentThread = Thread.currentThread();
                System.out.println("\n线程信息：");
                System.out.println("线程ID：" + currentThread.getId());
                System.out.println("线程名称：" + currentThread.getName());
                System.out.println("线程优先级：" + currentThread.getPriority());
                System.out.println("线程状态：" + currentThread.getState());
                System.out.println("是否守护线程：" + currentThread.isDaemon());
                
                // 演示线程间共享进程资源
                SharedResource.increment();
                System.out.println("共享计数器值：" + SharedResource.getCounter());
            }).start();
        }
    }
}

class SharedResource {
    // 进程内的共享资源
    private static int counter = 0;
    
    public static synchronized void increment() {
        counter++;
        try {
            // 模拟一些操作
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static int getCounter() {
        return counter;
    }
}
