public class SingletonDemo {

    public static void main(String[] args) {
        Runnable task = () -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " 获取实例: " + instance);
        };
        
        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");
        Thread t3 = new Thread(task, "线程3");
        
        t1.start();
        t2.start();
        t3.start();
    }
}

class Singleton {
    private volatile static Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) { // 第一次检查
            synchronized (Singleton.class) {
                if (instance == null) { // 第二次检查
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
