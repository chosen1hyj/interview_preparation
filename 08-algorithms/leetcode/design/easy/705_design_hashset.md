# 设计哈希集合 (简单)

## 题目描述
不使用任何内建的哈希表库设计一个哈希集合（HashSet）。

实现 MyHashSet 类：
- void add(key) 向哈希集合中插入值 key。
- bool contains(key) 返回哈希集合中是否存在这个值 key。
- void remove(key) 将给定值 key 从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。

## 示例
```
MyHashSet hashSet = new MyHashSet();
hashSet.add(1);         
hashSet.add(2);         
hashSet.contains(1);    // 返回 true
hashSet.contains(3);    // 返回 false (未找到)
hashSet.add(2);          
hashSet.contains(2);    // 返回 true
hashSet.remove(2);      
hashSet.contains(2);    // 返回 false (已经被删除)
```

## 解法
```java
class MyHashSet {
    private static final int BASE = 769;
    private LinkedList[] data;

    public MyHashSet() {
        data = new LinkedList[BASE];
        for (int i = 0; i < BASE; ++i) {
            data[i] = new LinkedList<Integer>();
        }
    }
    
    public void add(int key) {
        int h = hash(key);
        Iterator<Integer> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            if (element == key) {
                return;
            }
        }
        data[h].offerLast(key);
    }
    
    public void remove(int key) {
        int h = hash(key);
        Iterator<Integer> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            if (element == key) {
                data[h].remove(element);
                return;
            }
        }
    }
    
    public boolean contains(int key) {
        int h = hash(key);
        Iterator<Integer> iterator = data[h].iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            if (element == key) {
                return true;
            }
        }
        return false;
    }
    
    private int hash(int key) {
        return key % BASE;
    }
}
```

## 复杂度分析
- 时间复杂度: 平均 O(1)，最坏 O(n/BASE)
- 空间复杂度: O(BASE + n)

## 关键点
1. 哈希函数的设计
2. 冲突解决方法（链地址法）
3. 基本操作的实现

## 进阶思考
1. 如何优化哈希函数？
2. 能否使用其他冲突解决方法？
3. 如何动态调整哈希表大小？
