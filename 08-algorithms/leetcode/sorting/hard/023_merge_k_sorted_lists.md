# 合并K个升序链表 (困难)

## 题目描述
给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。

## 示例
输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
合并后:
1->1->2->3->4->4->5->6

## 解法一：优先队列
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    
    // 创建一个优先队列，按节点值自然排序
    PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
    
    // 将所有链表的头节点加入队列
    for (ListNode head : lists) {
        if (head != null) {
            queue.offer(head);
        }
    }
    
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    
    // 不断从队列中取出最小节点
    while (!queue.isEmpty()) {
        tail.next = queue.poll();
        tail = tail.next;
        
        if (tail.next != null) {
            queue.offer(tail.next);
        }
    }
    
    return dummy.next;
}
```

## 解法二：分治法
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    return merge(lists, 0, lists.length - 1);
}

private ListNode merge(ListNode[] lists, int left, int right) {
    if (left == right) return lists[left];
    
    int mid = left + (right - left) / 2;
    ListNode l1 = merge(lists, left, mid);
    ListNode l2 = merge(lists, mid + 1, right);
    
    return mergeTwoLists(l1, l2);
}

private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }
    
    current.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

## 复杂度分析
- 解法一（优先队列）：
  - 时间复杂度：O(NlogK)，其中N是所有节点的总数，K是链表的数量
  - 空间复杂度：O(K)，优先队列中最多存K个节点
- 解法二（分治法）：
  - 时间复杂度：O(NlogK)
  - 空间复杂度：O(logK)，递归调用的栈空间

## 关键点
1. 优先队列的使用方法
2. 分治合并的思想
3. 链表操作的基本技巧
4. 空指针的处理
