# 2. 两数相加（Add Two Numbers）

[题目链接](https://leetcode.com/problems/add-two-numbers/)

## 题目描述
给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：
```
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807
```

## 解法：模拟加法过程

### 思路
1. 同时遍历两个链表，模拟加法进位过程
2. 注意处理进位和链表长度不同的情况
3. 最后处理可能的额外进位

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // 虚拟头节点
        ListNode curr = dummy;
        int carry = 0; // 进位
        
        // 同时遍历两个链表
        while (l1 != null || l2 != null || carry != 0) {
            // 获取两个链表当前位的值
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            
            // 计算和与进位
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            
            // 创建新节点
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            
            // 移动指针
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }
        
        return dummy.next;
    }
}
```

### 复杂度分析
- 时间复杂度：O(max(m,n))，其中m和n是两个链表的长度
- 空间复杂度：O(max(m,n))，需要创建新的链表存储结果

## 题目重点
1. 处理进位
2. 处理不等长链表
3. 最后的进位处理

## 解题技巧

### 1. 使用虚拟头节点
- 简化头节点的处理
- 统一操作逻辑

### 2. 处理特殊情况
```java
// 处理链表为空的情况
int val1 = l1 != null ? l1.val : 0;
int val2 = l2 != null ? l2.val : 0;

// 处理进位
while (l1 != null || l2 != null || carry != 0)
```

### 3. 推荐写法
1. 使用虚拟头节点
2. while循环条件包含carry
3. 使用条件运算符处理空节点

## 易错点
1. 忘记处理最后的进位
2. 未正确处理链表长度不同的情况
3. 返回结果时忘记去掉虚拟头节点

## 相关题目
- [445. 两数相加 II](https://leetcode.com/problems/add-two-numbers-ii/)
- [43. 字符串相乘](https://leetcode.com/problems/multiply-strings/)
- [66. 加一](https://leetcode.com/problems/plus-one/)
- [67. 二进制求和](https://leetcode.com/problems/add-binary/)

## 延伸：大数加法

### 1. 链表实现的优势
- 天然按位存储
- 逆序存储便于计算
- 不受位数限制

### 2. 其他实现方式的对比
1. **字符串实现**
   - 需要额外处理进位
   - 需要反转字符串或者从后向前遍历
   - 实现相对复杂

2. **数组实现**
   - 需要预估结果的位数
   - 可能需要处理数组扩容
   - 不如链表灵活

### 3. 实际应用
1. 大数计算器
2. 金融计算
3. 科学计算

## 面试技巧

### 1. 讨论要点
- 链表的基本操作
- 进位的处理方式
- 代码的简洁性
- 边界情况的处理

### 2. 优化方向
- 代码结构优化
- 变量命名规范
- 注释的添加
- 异常情况处理

### 3. 扩展思考
- 如何处理负数？
- 如何处理小数？
- 如何优化空间复杂度？
- 如何处理更多数字的相加？

## 总结

### 实现步骤
1. 创建虚拟头节点
2. 同时遍历两个链表
3. 处理每一位的加法和进位
4. 创建新节点存储结果
5. 处理最后的进位
6. 返回结果链表

### 关键点
- 虚拟头节点的使用
- 进位的处理
- 链表遍历的终止条件
- 空值的处理

### 代码优化
- 使用更清晰的变量命名
- 添加必要的注释
- 使用条件运算符简化代码
- 保持代码结构的清晰性
