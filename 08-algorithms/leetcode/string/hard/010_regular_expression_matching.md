# 10. 正则表达式匹配（Regular Expression Matching）

[题目链接](https://leetcode.com/problems/regular-expression-matching/)

## 题目描述
给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。

'.' 匹配任意单个字符
'*' 匹配零个或多个前面的那一个元素

所谓匹配，是要涵盖整个字符串 s 的，而不是部分字符串。

示例：
```
输入：s = "aa", p = "a*"
输出：true
解释：因为 '*' 表示零个或多个，这里 'a*' 表示任意数量的 a ，包括零个。
```

## 解法：动态规划

### 思路
1. 定义状态转移方程
2. 处理特殊字符
3. 填充dp表

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        
        // dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // 初始化首行
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // 填充dp表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);
                
                if (pc == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    char prev = p.charAt(j - 2);
                    // 匹配0次
                    dp[i][j] = dp[i][j - 2];
                    // 匹配多次
                    if (prev == '.' || prev == sc) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                } else {
                    if (sc == pc) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### 复杂度分析
- 时间复杂度：O(mn)，其中m是字符串长度，n是模式串长度
- 空间复杂度：O(mn)

## 解题技巧

### 1. 动态规划
```java
// 状态定义
boolean[][] dp = new boolean[m + 1][n + 1];

// 状态转移
if (pc == '.') { ... }
else if (pc == '*') { ... }
else { ... }
```

### 2. 边界处理
```java
// 初始化
dp[0][0] = true;

// 首行处理
for (int j = 1; j <= n; j++) {
    if (p.charAt(j - 1) == '*') {
        dp[0][j] = dp[0][j - 2];
    }
}
```

### 3. 特殊字符
```java
// 处理'.'
if (pc == '.') { ... }

// 处理'*'
if (pc == '*') { ... }
```

## 相关题目
- [44. 通配符匹配](https://leetcode.com/problems/wildcard-matching/)
- [65. 有效数字](https://leetcode.com/problems/valid-number/)
- [72. 编辑距离](https://leetcode.com/problems/edit-distance/)

## 延伸思考

### 1. 变种问题
- 支持更多元字符？
- 最小匹配次数？
- 部分匹配？

### 2. 优化方向
- 空间优化
- NFA/DFA实现
- 并行计算

### 3. 实际应用
- 文本搜索
- 数据验证
- 字符串匹配
- 编译原理

## 面试技巧

### 1. 解题步骤
1. 确定状态定义
2. 设计转移方程
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
// 错误：遗漏初始化
// dp[0][0] = true;
// 应该添加初始值
```

### 2. 特殊字符
```java
// 错误：错误处理'*'
if (pc == '*') {
    dp[i][j] = dp[i][j - 1];
}
// 应该考虑匹配0次和多次
```

### 3. 状态转移
```java
// 错误：错误的状态转移
dp[i][j] = dp[i - 1][j - 1];
// 应该根据字符类型分别处理
```

## 总结

### 1. 动态规划要点
- 明确状态定义
- 正确状态转移
- 合理边界处理
- 优化空间使用

### 2. 优化方向
- 空间优化
- NFA/DFA实现
- BFS替代方案
- 状态压缩

### 3. 技能提升
- DP思维
- 边界意识
- 优化能力
- 代码实现
