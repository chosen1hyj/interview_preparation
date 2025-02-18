# 栈与队列基础知识

## 栈基础

### 1. 栈的特点
- 后进先出 (LIFO)
- 基本操作：push、pop、peek
- 常见实现：数组、链表

### 2. 常见栈操作的时间复杂度
- push: O(1)
- pop: O(1)
- peek: O(1)
- search: O(n)

### 3. 栈的应用场景
- 函数调用栈
- 表达式求值
- 括号匹配
- 深度优先搜索

## 队列基础

### 1. 队列的特点
- 先进先出 (FIFO)
- 基本操作：enqueue、dequeue、peek
- 双端队列 (Deque)

### 2. 常见队列操作的时间复杂度
- enqueue: O(1)
- dequeue: O(1)
- peek: O(1)

### 3. 队列的应用场景
- 广度优先搜索
- 缓存系统
- 任务调度

## 单调栈与单调队列

### 1. 单调栈
```java
// 维护递减栈
Stack<Integer> stack = new Stack<>();
for (int num : nums) {
    while (!stack.isEmpty() && stack.peek() < num) {
        stack.pop();
    }
    stack.push(num);
}
```

### 2. 单调队列
```java
// 双端队列实现滑动窗口最大值
Deque<Integer> deque = new LinkedList<>();
for (int i = 0; i < nums.length; i++) {
    // 移除过期元素
    if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
        deque.pollFirst();
    }
    
    // 维护单调性
    while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
        deque.pollLast();
    }
    
    deque.offerLast(i);
}
```

## 实战技巧

### 1. 栈的使用场景
- 最近相关性问题
- 嵌套结构处理
- 回溯算法

### 2. 队列的使用场景
- 层次遍历
- BFS搜索
- 时间序列处理

### 3. 注意事项
- 栈空判断
- 队列满判断
- 边界条件处理
