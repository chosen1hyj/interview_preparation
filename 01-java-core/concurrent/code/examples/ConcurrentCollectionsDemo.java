import java.util.concurrent.*;
import java.util.*;

public class ConcurrentCollectionsDemo {

    public static void main(String[] args) throws InterruptedException {
        // ConcurrentHashMap 示例
        System.out.println("=== ConcurrentHashMap 示例 ===");
        Map<String, Integer> map = new ConcurrentHashMap<>();
        
        Runnable writer = () -> {
            for (int i = 0; i < 50; i++) {
                map.put("Key" + i, i);
            }
        };
        
        Runnable reader = () -> {
            for (int i = 0; i < 50; i++) {
                System.out.println("Read: " + map.get("Key" + i));
            }
        };
        
        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(reader);
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        // CopyOnWriteArrayList 示例
        System.out.println("\n=== CopyOnWriteArrayList 示例 ===");
        List<String> list = new CopyOnWriteArrayList<>();
        
        Runnable modifier = () -> {
            for (int i = 0; i < 10; i++) {
                list.add("Item" + i);
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        };
        
        Runnable iterator = () -> {
            for (String item : list) {
                System.out.println("Iterated: " + item);
                try { Thread.sleep(20); } catch (InterruptedException e) {}
            }
        };
        
        Thread t3 = new Thread(modifier);
        Thread t4 = new Thread(iterator);
        
        t3.start();
        t4.start();
        t3.join();
        t4.join();
    }
}
