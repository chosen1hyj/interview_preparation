# 206. 反转链表（Reverse Linked List）

[题目链接](https://leetcode.com/problems/reverse-linked-list/)

## 题目描述
给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。

示例：
```
输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]
```

## 解法一：迭代法

### 思路
使用三个指针（prev、curr、next）遍历链表，逐个修改节点的next指针方向。

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            // 保存下一个节点
            ListNode next = curr.next;
            // 反转当前节点的指针
            curr.next = prev;
            // 移动prev和curr指针
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(1)

## 解法二：递归法

### 思路
通过递归调用，先反转后面的子链表，再处理当前节点。

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        // 基本情况：空链表或只有一个节点
        if (head == null || head.next == null) {
            return head;
        }
        
        // 递归反转子链表
        ListNode reversedList = reverseList(head.next);
        
        // 处理当前节点
        head.next.next = head;
        head.next = null;
        
        return reversedList;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(n)，递归调用栈的空间

## 题目重点
1. 注意保存next节点，避免断链
2. 理解指针反转的过程
3. 处理特殊情况（空链表、单节点链表）

## 解题技巧

### 1. 画图理解
```
原始链表：1 -> 2 -> 3 -> null
反转过程：
第一步：null <- 1    2 -> 3 -> null
第二步：null <- 1 <- 2    3 -> null
第三步：null <- 1 <- 2 <- 3
```

### 2. 指针操作要点
1. 保存next节点
2. 修改当前节点指向
3. 移动指针
4. 注意操作顺序

### 3. 递归要点
1. 确定基本情况
2. 找到递归关系
3. 处理当前节点

## 易错点
1. 忘记保存next节点导致断链
2. 返回错误的头节点
3. 递归时未正确处理基本情况

## 相关题目
- [92. 反转链表 II](https://leetcode.com/problems/reverse-linked-list-ii/)
- [25. K 个一组翻转链表](https://leetcode.com/problems/reverse-nodes-in-k-group/)

## 总结

### 迭代法步骤
1. 初始化prev为null，curr为head
2. 遍历链表：
   - 保存next节点
   - 反转指针
   - 移动prev和curr
3. 返回prev作为新的头节点

### 递归法步骤
1. 确定基本情况（空链表或单节点）
2. 递归处理子链表
3. 处理当前节点
   - 设置next节点的next指针
   - 将当前节点的next设为null
4. 返回反转后的头节点

### 优化建议
1. 对于短链表，迭代法更好（空间复杂度更低）
2. 如果担心递归栈溢出，使用迭代法
3. 如果代码简洁性更重要，可以考虑递归法
