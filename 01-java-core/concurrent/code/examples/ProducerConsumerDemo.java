import java.util.concurrent.*;
import java.util.*;

public class ProducerConsumerDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        
        Runnable producer = () -> {
            try {
                int count = 1;
                while (true) {
                    String item = "Item-" + count++;
                    queue.put(item);
                    System.out.println(Thread.currentThread().getName() + " 生产: " + item);
                    Thread.sleep(new Random().nextInt(1000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Runnable consumer = () -> {
            try {
                while (true) {
                    String item = queue.take();
                    System.out.println(Thread.currentThread().getName() + " 消费: " + item);
                    Thread.sleep(new Random().nextInt(2000));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        
        Thread producerThread = new Thread(producer, "生产者");
        Thread consumerThread1 = new Thread(consumer, "消费者1");
        Thread consumerThread2 = new Thread(consumer, "消费者2");
        
        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();
        
        Thread.sleep(10000); // 运行一段时间
        producerThread.interrupt();
        consumerThread1.interrupt();
        consumerThread2.interrupt();
    }
}
