# 链表基础知识

## 链表的基本概念

### 1. 定义和特点
- 由节点（Node）组成的线性数据结构
- 每个节点包含数据和指向下一个节点的指针
- 不要求连续的内存空间
- 插入和删除操作时间复杂度为O(1)
- 访问元素时间复杂度为O(n)

### 2. 常见的链表类型

#### 单向链表
```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
```

#### 双向链表
```java
class DoublyListNode {
    int val;
    DoublyListNode next;
    DoublyListNode prev;
    DoublyListNode(int x) {
        val = x;
        next = null;
        prev = null;
    }
}
```

#### 循环链表
```java
// 尾节点的next指向头节点
tail.next = head;
```

## 常见链表操作

### 1. 基本操作
```java
class LinkedList {
    // 在头部插入节点
    public ListNode addAtHead(ListNode head, int val) {
        ListNode newNode = new ListNode(val);
        newNode.next = head;
        return newNode;
    }
    
    // 在尾部插入节点
    public ListNode addAtTail(ListNode head, int val) {
        if (head == null) {
            return new ListNode(val);
        }
        ListNode curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new ListNode(val);
        return head;
    }
    
    // 删除节点
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) return null;
        if (head.val == val) return head.next;
        
        ListNode curr = head;
        while (curr.next != null && curr.next.val != val) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
        }
        return head;
    }
}
```

### 2. 常用技巧

#### 虚拟头节点
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
// 处理链表...
return dummy.next;
```

#### 快慢指针
```java
// 找到链表中点
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow 即为中点
```

#### 链表反转
```java
public ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

## 常见链表问题类型

### 1. 基本操作类
- 插入节点
- 删除节点
- 查找节点
- 修改节点

### 2. 链表转换类
- 反转链表
- 重排链表
- 合并链表
- 拆分链表

### 3. 环相关
- 检测环
- 找环起点
- 环的长度

### 4. 双指针应用
- 找中点
- 找倒数第K个节点
- 相交节点

## 解题技巧

### 1. 使用虚拟头节点
- 统一处理头节点和其他节点
- 避免空指针异常
- 简化代码逻辑

### 2. 双指针技巧
- 快慢指针找中点
- 双指针找倒数第K个
- 多指针合并链表

### 3. 画图辅助
- 理清指针关系
- 避免指针丢失
- 处理复杂情况

### 4. 考虑边界情况
- 空链表
- 单节点链表
- 两个节点的链表

## 面试要点

### 1. 注意事项
- 不要丢失节点
- 保持正确的指针指向
- 考虑边界条件
- 注意环的存在

### 2. 常见陷阱
- 指针更新顺序
- 边界条件判断
- 环的处理
- 内存泄漏

### 3. 优化方向
- 使用常数额外空间
- 一次遍历完成
- 维护必要指针
- 简化代码结构

## 实战建议

### 1. 基础题型
- 链表反转
- 环的检测
- 合并有序链表
- 删除指定节点

### 2. 进阶题型
- K个一组反转
- 排序链表
- 复制带随机指针的链表
- LRU缓存

### 3. 刷题建议
- 先掌握基本操作
- 理解双指针应用
- 注重边界处理
- 画图帮助思考

### 4. 练习顺序
1. 基本操作题
2. 反转类题目
3. 环相关题目
4. 双指针应用
5. 复杂操作题
