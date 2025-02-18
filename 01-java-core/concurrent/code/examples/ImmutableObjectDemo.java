import java.util.*;
import java.util.concurrent.*;

public class ImmutableObjectDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建不可变对象
        ImmutableObject obj = new ImmutableObject(42, "Important Data");
        
        // 尝试修改不可变对象
        System.out.println("原始对象: " + obj);
        
        Runnable modifier = () -> {
            try {
                // 无法修改不可变对象
                // 只能创建新的对象
                ImmutableObject newObj = new ImmutableObject(obj.getValue() + 1, obj.getName() + "_modified");
                System.out.println(Thread.currentThread().getName() + " 新对象: " + newObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            pool.submit(modifier);
        }
        
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }
}

final class ImmutableObject {
    private final int value;
    private final String name;
    
    public ImmutableObject(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "ImmutableObject{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
