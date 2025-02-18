import java.util.concurrent.*;
import java.util.*;

public class PhaserDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Phaser 示例 ===");
        Phaser phaser = new Phaser(3);
        
        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 第一阶段开始");
                phaser.arriveAndAwaitAdvance();
                
                System.out.println(Thread.currentThread().getName() + " 第二阶段开始");
                Thread.sleep(new Random().nextInt(1000));
                phaser.arriveAndAwaitAdvance();
                
                if (new Random().nextBoolean()) {
                    System.out.println(Thread.currentThread().getName() + " 注销");
                    phaser.arriveAndDeregister();
                } else {
                    System.out.println(Thread.currentThread().getName() + " 第三阶段开始");
                    Thread.sleep(new Random().nextInt(1000));
                    phaser.arriveAndAwaitAdvance();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Thread t1 = new Thread(task, "线程1");
        Thread t2 = new Thread(task, "线程2");
        Thread t3 = new Thread(task, "线程3");
        
        t1.start();
        t2.start();
        t3.start();
        
        t1.join();
        t2.join();
        t3.join();
    }
}
