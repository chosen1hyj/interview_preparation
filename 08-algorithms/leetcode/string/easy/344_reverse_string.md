# 344. 反转字符串（Reverse String）

[题目链接](https://leetcode.com/problems/reverse-string/)

## 题目描述
编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。

不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这个问题。

示例：
```
输入：["h","e","l","l","o"]
输出：["o","l","l","e","h"]
```

## 解法：双指针

### 思路
1. 使用左右指针分别指向两端
2. 交换两端元素
3. 向中间移动指针

```java
class Solution {
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        
        while (left < right) {
            // 交换两端元素
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            
            // 移动指针
            left++;
            right--;
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，其中n是字符串长度
- 空间复杂度：O(1)

## 解题技巧

### 1. 双指针
```java
// 左右指针向中间移动
int left = 0, right = s.length - 1;
while (left < right) { ... }
```

### 2. 原地交换
```java
// 交换两端元素
char temp = s[left];
s[left] = s[right];
s[right] = temp;
```

### 3. 边界处理
```java
// 检查边界条件
if (s == null || s.length <= 1) return;
```

## 相关题目
- [541. 反转字符串 II](https://leetcode.com/problems/reverse-string-ii/)
- [557. 反转字符串中的单词 III](https://leetcode.com/problems/reverse-words-in-a-string-iii/)
- [917. 仅仅反转字母](https://leetcode.com/problems/reverse-only-letters/)

## 延伸思考

### 1. 变种问题
- 带条件反转？
- 部分反转？
- 特殊字符处理？

### 2. 优化方向
- 递归实现
- 字符集扩展
- 并行计算

### 3. 实际应用
- 文本处理
- 数据加密
- 字符串操作
- 编码转换

## 面试技巧

### 1. 解题步骤
1. 确定指针位置
2. 设计交换逻辑
3. 实现边界判断
4. 收集最终结果

### 2. 代码实现
- 完善的边界检查
- 正确的状态维护
- 清晰的搜索逻辑
- 准确的结果统计

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 边界条件
```java
// 错误：越界访问
while (left <= right) { ... }
// 应该是小于
while (left < right) { ... }
```

### 2. 交换逻辑
```java
// 错误：遗漏临时变量
s[left] = s[right];
s[right] = s[left];
// 应该使用临时变量
char temp = s[left];
s[left] = s[right];
s[right] = temp;
```

### 3. 空值判断
```java
// 错误：遗漏空值检查
// if (s == null || s.length <= 1) return;
// 应该添加空值判断
```

## 总结

### 1. 双指针要点
- 明确指针初始位置
- 正确交换元素
- 合理移动指针
- 处理边界情况

### 2. 优化方向
- 递归实现
- 字符集扩展
- BFS替代方案
- 空间优化

### 3. 技能提升
- 指针思维
- 边界意识
- 优化能力
- 代码实现
