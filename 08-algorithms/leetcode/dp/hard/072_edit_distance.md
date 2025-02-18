# 72. 编辑距离（Edit Distance）

[题目链接](https://leetcode.com/problems/edit-distance/)

## 题目描述
给你两个单词 word1 和 word2，请返回将 word1 转换成 word2 所使用的最少操作数。

你可以对一个单词进行如下三种操作：
1. 插入一个字符
2. 删除一个字符
3. 替换一个字符

示例：
```
输入：word1 = "horse", word2 = "ros"
输出：3
解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
```

## 解法一：动态规划

### 思路
1. 定义状态：dp[i][j]表示word1前i个字符转换到word2前j个字符需要的最少操作数
2. 状态转移：
   - 如果word1[i] == word2[j]，则dp[i][j] = dp[i-1][j-1]
   - 否则，dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
     - dp[i-1][j-1] + 1: 替换操作
     - dp[i-1][j] + 1: 删除操作
     - dp[i][j-1] + 1: 插入操作

```java
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // dp[i][j] 表示word1前i个字符转换到word2前j个字符需要的最少操作数
        int[][] dp = new int[m + 1][n + 1];
        
        // 初始化
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;  // word2为空，需要删除i个字符
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;  // word1为空，需要插入j个字符
        }
        
        // 状态转移
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1], 
                                Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### 复杂度分析
- 时间复杂度：O(mn)，其中m和n分别是两个字符串的长度
- 空间复杂度：O(mn)

## 解法二：空间优化

### 思路
由于每次只需要前一行的状态，可以使用滚动数组优化空间。

```java
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // 确保word1是较短的字符串
        if (m > n) {
            return minDistance(word2, word1);
        }
        
        // 只使用一行DP数组
        int[] dp = new int[n + 1];
        
        // 初始化第一行
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }
        
        // 状态转移
        for (int i = 1; i <= m; i++) {
            int prev = dp[0];  // 保存左上角的值
            dp[0] = i;         // 第一个元素特殊处理
            
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];  // 保存未被覆盖的值
                
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[j] = prev;
                } else {
                    dp[j] = Math.min(prev, Math.min(dp[j], dp[j-1])) + 1;
                }
                
                prev = temp;  // 更新左上角的值
            }
        }
        
        return dp[n];
    }
}
```

### 复杂度分析
- 时间复杂度：O(mn)
- 空间复杂度：O(min(m,n))

## 题目重点
1. 理解编辑距离的概念
2. 掌握二维DP的状态定义和转移
3. 理解三种操作的等价性
4. 空间优化的思路

## 解题技巧

### 1. 状态定义
```java
// dp[i][j]表示word1的前i个字符转换到word2的前j个字符需要的最少操作数
int[][] dp = new int[m + 1][n + 1];
```

### 2. 三种操作的理解
```java
// 替换：dp[i-1][j-1] + 1
// 删除：dp[i-1][j] + 1
// 插入：dp[i][j-1] + 1
dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
```

### 3. 初始化
```java
// word1为空，需要插入
for (int j = 0; j <= n; j++) {
    dp[0][j] = j;
}

// word2为空，需要删除
for (int i = 0; i <= m; i++) {
    dp[i][0] = i;
}
```

## 相关题目
- [583. 两个字符串的删除操作](https://leetcode.com/problems/delete-operation-for-two-strings/)
- [712. 两个字符串的最小ASCII删除和](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/)
- [161. 相隔为1的编辑距离](https://leetcode.com/problems/one-edit-distance/)

## 延伸思考

### 1. 变种问题
- 只允许某些操作
- 不同操作的代价不同
- 多个字符串之间的编辑距离

### 2. 优化方向
- 空间优化
- 提前终止条件
- 特殊情况处理

### 3. 实际应用
- 拼写检查
- DNA序列比对
- 文本相似度计算

## 面试技巧

### 1. 解题步骤
1. 分析问题的基本操作
2. 设计状态和转移方程
3. 考虑初始化和边界
4. 优化空间复杂度

### 2. 核心要点
- DP状态定义
- 转移方程推导
- 空间优化思路
- 代码简化技巧

### 3. 代码实现
- 变量命名规范
- 注释清晰详细
- 代码结构优雅
- 边界处理完善

## 常见错误

### 1. 状态定义错误
```java
// 错误：没有考虑到前i个字符的概念
dp[i][j] = word1[i]转换到word2[j]的距离
```

### 2. 转移方程错误
```java
// 错误：遗漏某些操作
dp[i][j] = Math.min(dp[i-1][j-1], dp[i-1][j]) + 1;
```

### 3. 初始化错误
```java
// 错误：忘记初始化边界
for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
        // ...
    }
}
```

## 总结

### 1. 算法要点
- 二维DP设计
- 状态转移推导
- 空间优化技巧
- 边界条件处理

### 2. 优化思路
- 使用滚动数组
- 处理特殊情况
- 提前终止判断
- 选择较短串优化

### 3. 技能提升
- DP问题分析
- 空间优化意识
- 代码实现能力
- 实际应用思考
