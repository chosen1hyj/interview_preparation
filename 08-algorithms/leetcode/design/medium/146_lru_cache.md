# LRU 缓存 (中等)

## 题目描述
请你设计并实现一个满足 LRU (最近最少使用) 缓存约束的数据结构。

实现 LRUCache 类：
- LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
- int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1。
- void put(int key, int value) 如果关键字已经存在，则变更其值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据，从而为新的数据留出空间。

要求：在 O(1) 时间复杂度内完成这两种操作。

## 示例
```
输入
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
输出
[null, null, null, 1, null, -1, null, -1, 3, 4]
```

## 解法
```java
class LRUCache {
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private int capacity;
    private Map<Integer, Node> cache;
    private Node head;
    private Node tail;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // 使用伪头部和伪尾部节点
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 将节点移到头部
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        Node node = cache.get(key);
        
        if (node == null) {
            // 创建新节点
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            
            if (cache.size() > capacity) {
                // 删除尾部节点
                Node tail = removeTail();
                cache.remove(tail.key);
            }
        } else {
            // 更新现有节点的值
            node.value = value;
            moveToHead(node);
        }
    }
    
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
    
    private Node removeTail() {
        Node res = tail.prev;
        removeNode(res);
        return res;
    }
}
```

## 复杂度分析
- 时间复杂度: O(1)，所有操作都是常数时间
- 空间复杂度: O(capacity)，哈希表和双向链表的空间

## 关键点
1. 哈希表 + 双向链表的组合使用
2. 伪头部和伪尾部节点的设计
3. 链表节点的移动操作
4. 缓存满时的删除策略

## 相关题目
- 146. LRU缓存 (中等)
- 460. LFU缓存 (困难)
- 432. 全O(1)的数据结构 (困难)
