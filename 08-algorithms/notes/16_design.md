# 设计题基础知识

## 基本概念

### 1. 设计原则
- 单一职责原则 (SRP)
- 开闭原则 (OCP)
- 里氏替换原则 (LSP)
- 接口隔离原则 (ISP)
- 依赖倒置原则 (DIP)

### 2. 常见设计模式
```java
// 单例模式
class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// 工厂模式
interface Product {}
class ConcreteProductA implements Product {}
class ConcreteProductB implements Product {}

class Factory {
    public Product createProduct(String type) {
        switch(type) {
            case "A": return new ConcreteProductA();
            case "B": return new ConcreteProductB();
            default: throw new IllegalArgumentException();
        }
    }
}
```

## 系统设计

### 1. LRU缓存
```java
class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }
    
    private Map<Integer, Node> map = new HashMap<>();
    private int capacity;
    private Node head = new Node(0,0);
    private Node tail = new Node(0,0);
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        remove(node);
        insert(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        if (map.size() == capacity) {
            remove(tail.prev);
        }
        insert(new Node(key, value));
    }
    
    private void remove(Node node) {
        map.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void insert(Node node) {
        map.put(node.key, node);
        Node headNext = head.next;
        head.next = node;
        node.prev = head;
        node.next = headNext;
        headNext.prev = node;
    }
}
```

### 2. LFU缓存
```java
class LFUCache {
    class Node {
        int key, val, freq;
        Node(int k, int v) { key = k; val = v; freq = 1; }
    }
    
    private Map<Integer, Node> map = new HashMap<>();
    private Map<Integer, LinkedHashSet<Node>> freqMap = new HashMap<>();
    private int capacity;
    private int minFreq;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        updateFreq(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            updateFreq(node);
        } else {
            if (map.size() == capacity) {
                evict();
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addFreq(newNode);
        }
    }
    
    private void updateFreq(Node node) {
        LinkedHashSet<Node> set = freqMap.get(node.freq);
        set.remove(node);
        if (set.isEmpty()) {
            freqMap.remove(node.freq);
            if (minFreq == node.freq) {
                minFreq++;
            }
        }
        node.freq++;
        addFreq(node);
    }
    
    private void addFreq(Node node) {
        freqMap.computeIfAbsent(node.freq, k -> new LinkedHashSet<>()).add(node);
        minFreq = Math.min(minFreq, node.freq);
    }
    
    private void evict() {
        LinkedHashSet<Node> set = freqMap.get(minFreq);
        Node toRemove = set.iterator().next();
        set.remove(toRemove);
        if (set.isEmpty()) {
            freqMap.remove(minFreq);
        }
        map.remove(toRemove.key);
    }
}
```

## 实战应用

### 1. 数据结构设计
```java
// 设计一个支持增量操作的数组
class CustomArray {
    private int[] arr;
    private int[] inc;
    
    public CustomArray(int n) {
        arr = new int[n];
        inc = new int[n];
    }
    
    public void update(int start, int end, int val) {
        inc[start] += val;
        if (end + 1 < arr.length) {
            inc[end+1] -= val;
        }
    }
    
    public int[] getResult() {
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                arr[i] += inc[i];
            } else {
                inc[i] += inc[i-1];
                arr[i] += inc[i];
            }
        }
        return arr;
    }
}
```

### 2. 复杂系统设计
```java
// 设计一个消息队列系统
class MessageQueue {
    private Map<String, Deque<String>> topicMap = new HashMap<>();
    private Map<String, Integer> offsetMap = new HashMap<>();
    
    // 发布消息
    public void publish(String topic, String message) {
        topicMap.computeIfAbsent(topic, k -> new LinkedList<>()).offer(message);
    }
    
    // 订阅消息
    public String consume(String topic, String consumerId) {
        Deque<String> queue = topicMap.get(topic);
        if (queue == null || queue.isEmpty()) return null;
        
        Integer offset = offsetMap.getOrDefault(consumerId+"#"+topic, 0);
        if (offset >= queue.size()) return null;
        
        List<String> list = new ArrayList<>(queue);
        String message = list.get(offset);
        offsetMap.put(consumerId+"#"+topic, offset+1);
        return message;
    }
    
    // 提交偏移量
    public void commitOffset(String consumerId, String topic, int offset) {
        offsetMap.put(consumerId+"#"+topic, offset);
    }
}
```

## 注意事项

### 1. 边界处理
- 容量限制
- 并发控制
- 异常处理

### 2. 性能优化
- 时间复杂度分析
- 空间复杂度优化
- 缓存策略选择

### 3. 常见陷阱
- 死锁风险
- 内存泄漏
- 线程安全问题
