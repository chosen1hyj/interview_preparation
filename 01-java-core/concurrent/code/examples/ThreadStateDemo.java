public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程
        Thread thread = new Thread(() -> {
            System.out.println("线程开始执行");
            try {
                Thread.sleep(2000); // 线程进入TIMED_WAITING状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行结束");
        });

        // 展示NEW状态
        System.out.println("1. 初始状态：" + thread.getState());

        // 启动线程，进入RUNNABLE状态
        thread.start();
        System.out.println("2. 启动后状态：" + thread.getState());

        // 主线程睡眠1秒，让子线程有足够时间进入TIMED_WAITING状态
        Thread.sleep(1000);
        System.out.println("3. 睡眠中状态：" + thread.getState());

        // 等待线程结束
        thread.join();
        System.out.println("4. 结束后状态：" + thread.getState());

        // 演示BLOCKED状态
        Object lock = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread2获取锁");
            }
        });

        thread1.start();
        Thread.sleep(100); // 确保thread1先获取锁
        thread2.start();
        Thread.sleep(100); // 给thread2一些时间进入BLOCKED状态
        System.out.println("5. 等待锁状态：" + thread2.getState());
    }
}
