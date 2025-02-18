# 删除排序链表中的重复元素 II (中等)

## 题目描述
给定一个已排序的链表的头 head ，删除原始链表中所有重复数字的节点，只留下不同的数字。返回已排序的链表也必须是排序的。

## 示例
```
输入: head = [1,2,3,3,4,4,5]
输出: [1,2,5]

输入: head = [1,1,1,2,3]
输出: [2,3]
```

## 解法
```java
public ListNode deleteDuplicates(ListNode head) {
    if (head == null) return null;
    
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    
    while (head != null && head.next != null) {
        if (head.val == head.next.val) {
            // 跳过重复节点
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            prev.next = head.next;
        } else {
            prev = prev.next;
        }
        head = head.next;
    }
    
    return dummy.next;
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(1)

## 关键点
1. 使用虚拟头节点
2. 快速跳过重复节点
3. 正确维护指针关系

## 进阶思考
1. 如何处理未排序链表？
2. 能否优化空间复杂度？
3. 考虑递归解法
