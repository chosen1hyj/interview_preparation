# 25. K 个一组翻转链表（Reverse Nodes in k-Group）

[题目链接](https://leetcode.com/problems/reverse-nodes-in-k-group/)

## 题目描述
给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。

k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

不允许使用额外的内存空间，只能在原地修改链表。

示例：
```
输入：head = [1,2,3,4,5], k = 2
输出：[2,1,4,3,5]

输入：head = [1,2,3,4,5], k = 3
输出：[3,2,1,4,5]
```

## 解法：分组翻转

### 思路
1. 使用dummy节点处理头部
2. 每次找到k个节点，判断是否足够进行翻转
3. 对这k个节点进行翻转
4. 连接翻转后的子链表
5. 移动到下一组

```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (head != null) {
            // 1. 检查剩余节点是否足够k个
            ListNode tail = prev;
            for (int i = 0; i < k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return dummy.next; // 不足k个，返回
                }
            }
            
            // 2. 保存下一组的头节点
            ListNode next = tail.next;
            
            // 3. 翻转k个节点
            ListNode[] reversed = reverseList(head, tail);
            head = reversed[0];
            tail = reversed[1];
            
            // 4. 连接翻转后的子链表
            prev.next = head;
            tail.next = next;
            
            // 5. 移动指针到下一组
            prev = tail;
            head = next;
        }
        
        return dummy.next;
    }
    
    // 翻转链表的一部分，返回新的头和尾
    private ListNode[] reverseList(ListNode head, ListNode tail) {
        ListNode prev = tail.next;
        ListNode curr = head;
        while (prev != tail) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return new ListNode[]{tail, head};
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，其中n是链表长度
- 空间复杂度：O(1)，只使用常数级额外空间

## 题目重点
1. 分组处理链表
2. 翻转链表的子部分
3. 连接翻转后的部分
4. 处理最后不足k个节点的情况

## 解题技巧

### 1. 使用虚拟头节点
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

### 2. 检查长度
```java
// 检查剩余长度是否足够
ListNode tail = prev;
for (int i = 0; i < k; i++) {
    tail = tail.next;
    if (tail == null) {
        return dummy.next;
    }
}
```

### 3. 翻转子链表
```java
ListNode[] reversed = reverseList(head, tail);
head = reversed[0]; // 新的头
tail = reversed[1]; // 新的尾
```

## 易错点
1. 未正确处理不足k个节点的情况
2. 连接翻转后的链表时指针错误
3. 翻转过程中丢失节点
4. 返回错误的头节点

## 变形题目
- [206. 反转链表](https://leetcode.com/problems/reverse-linked-list/)
- [92. 反转链表 II](https://leetcode.com/problems/reverse-linked-list-ii/)
- [24. 两两交换链表中的节点](https://leetcode.com/problems/swap-nodes-in-pairs/)

## 总结

### 实现步骤
1. 创建虚拟头节点
2. 遍历链表：
   - 检查剩余节点数量
   - 找到当前组的尾节点
   - 翻转当前组
   - 连接翻转后的部分
3. 返回结果

### 关键点
1. **分组处理**
   - 确定每组的起点和终点
   - 判断剩余节点是否足够

2. **翻转操作**
   - 保存关键节点
   - 正确连接各部分
   - 更新相关指针

3. **边界处理**
   - 处理头尾节点
   - 处理最后一组
   - 处理不足k个的情况

### 优化方向
1. **代码组织**
   - 抽取复用的方法
   - 清晰的变量命名
   - 详细的注释说明

2. **性能优化**
   - 一次遍历完成
   - 减少额外空间使用
   - 优化指针操作

3. **代码健壮性**
   - 输入验证
   - 边界检查
   - 异常处理

### 面试要点
1. **考察重点**
   - 链表操作的基本功
   - 复杂问题的拆解能力
   - 代码的完整性

2. **扩展问题**
   - 如何处理循环链表？
   - 如何优化空间复杂度？
   - 如何处理并发场景？

3. **代码质量**
   - 清晰的逻辑结构
   - 优雅的实现方式
   - 完善的错误处理
