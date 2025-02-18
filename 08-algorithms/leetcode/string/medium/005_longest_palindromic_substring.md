# 5. 最长回文子串（Longest Palindromic Substring）

[题目链接](https://leetcode.com/problems/longest-palindromic-substring/)

## 题目描述
给你一个字符串 s，找到 s 中最长的回文子串。

示例：
```
输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。
```

## 解法：中心扩展法

### 思路
1. 枚举每个可能的回文中心
2. 向两边扩展判断
3. 记录最长回文子串

```java
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文
            int len1 = expandAroundCenter(s, i, i);
            // 偶数长度回文
            int len2 = expandAroundCenter(s, i, i + 1);
            // 取最大值
            int len = Math.max(len1, len2);
            
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n²)，其中n是字符串长度
- 空间复杂度：O(1)

## 解题技巧

### 1. 中心扩展
```java
// 扩展判断回文
while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) { ... }
```

### 2. 边界计算
```java
// 计算边界位置
start = i - (len - 1) / 2;
end = i + len / 2;
``` 

### 3. 奇偶处理
```java
// 处理奇数和偶数长度
int len1 = expandAroundCenter(s, i, i);     // 奇数
int len2 = expandAroundCenter(s, i, i + 1); // 偶数
``` 

## 相关题目
- [647. 回文子串](https://leetcode.com/problems/palindromic-substrings/)
- [9. 回文数](https://leetcode.com/problems/palindrome-number/)
- [234. 回文链表](https://leetcode.com/problems/palindrome-linked-list/)

## 延伸思考

### 1. 变种问题
- 带权重的回文？
- 最大乘积回文？
- 特殊字符处理？

### 2. 优化方向
- 动态规划
- Manacher算法
- 并行计算

### 3. 实际应用
- 文本处理
- 数据加密
- 字符串匹配
- 编码转换

## 面试技巧

### 1. 解题步骤
1. 确定中心位置
2. 设计扩展逻辑
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
while (left >= 0 && right <= s.length()) { ... }
// 应该是小于
while (left >= 0 && right < s.length()) { ... }
```

### 2. 奇偶处理
```java
// 错误：遗漏偶数情况
int len = expandAroundCenter(s, i, i);
// 应该同时处理奇数和偶数
int len1 = expandAroundCenter(s, i, i);
int len2 = expandAroundCenter(s, i, i + 1);
```

### 3. 返回值
```java
// 错误：返回错误区间
return s.substring(start, end);
// 应该包含end位置
return s.substring(start, end + 1);
```

## 总结

### 1. 中心扩展要点
- 明确中心位置
- 正确扩展判断
- 合理移动指针
- 处理边界情况

### 2. 优化方向
- 动态规划
- Manacher算法
- BFS替代方案
- 空间优化

### 3. 技能提升
- 回文思维
- 边界意识
- 优化能力
- 代码实现
