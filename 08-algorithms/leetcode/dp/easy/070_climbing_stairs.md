# 70. 爬楼梯（Climbing Stairs）

[题目链接](https://leetcode.com/problems/climbing-stairs/)

## 题目描述
假设你正在爬楼梯。需要 n 阶你才能到达楼顶。

每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

示例：
```
输入：n = 2
输出：2
解释：有两种方法可以爬到楼顶。
1. 1 阶 + 1 阶
2. 2 阶

输入：n = 3
输出：3
解释：有三种方法可以爬到楼顶。
1. 1 阶 + 1 阶 + 1 阶
2. 1 阶 + 2 阶
3. 2 阶 + 1 阶
```

## 解法一：动态规划

### 思路
1. 定义状态：dp[i]表示爬到第i阶的方法数
2. 状态转移：dp[i] = dp[i-1] + dp[i-2]
3. 初始条件：dp[1] = 1, dp[2] = 2

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        
        return dp[n];
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(n)

## 解法二：空间优化

### 思路
由于我们只需要前两个状态，可以使用两个变量代替数组。

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        int prev2 = 1;  // dp[i-2]
        int prev1 = 2;  // dp[i-1]
        int curr = 0;   // dp[i]
        
        for (int i = 3; i <= n; i++) {
            curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        
        return curr;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(1)

## 解法三：数学方法

### 思路
这个问题实际上是斐波那契数列，可以使用通项公式直接计算。

```java
class Solution {
    public int climbStairs(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibn = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
        return (int)(fibn / sqrt5);
    }
}
```

### 复杂度分析
- 时间复杂度：O(logn)，用于计算幂
- 空间复杂度：O(1)

## 题目重点
1. 理解动态规划的基本概念
2. 找出状态转移方程
3. 考虑空间优化的可能性
4. 认识到与斐波那契数列的关系

## 解题技巧

### 1. 状态定义
```java
// dp[i]表示爬到第i阶的方法数
int[] dp = new int[n + 1];
```

### 2. 状态转移
```java
// 当前状态由前两个状态决定
dp[i] = dp[i-1] + dp[i-2]
```

### 3. 初始化
```java
dp[1] = 1;  // 爬到第1阶只有1种方法
dp[2] = 2;  // 爬到第2阶有2种方法
```

## 类似题目
- [509. 斐波那契数](https://leetcode.com/problems/fibonacci-number/)
- [746. 使用最小花费爬楼梯](https://leetcode.com/problems/min-cost-climbing-stairs/)
- [1137. 第 N 个泰波那契数](https://leetcode.com/problems/n-th-tribonacci-number/)

## 延伸思考

### 1. 其他变种
- 如果可以爬1、2、3阶？
- 如果每阶有代价？
- 如果有些台阶不能走？

### 2. 优化方向
- 使用矩阵快速幂
- 使用记忆化搜索
- 空间压缩技巧

### 3. 实际应用
- 排列组合问题
- 路径规划
- 状态转移系统

## 面试技巧

### 1. 解题步骤
1. 先说出暴力解法
2. 推导出DP解法
3. 考虑空间优化
4. 提出数学解法

### 2. 要点讲解
- DP的状态定义
- 转移方程的推导
- 优化方案的分析
- 与其他问题的联系

### 3. 代码实现
- 变量命名清晰
- 注释说明完整
- 处理边界情况
- 考虑代码简洁性

## 常见错误

### 1. 状态定义错误
```java
// 错误：没有考虑到所有情况
dp[i] = dp[i-1];  // 漏掉了跨两步的情况
```

### 2. 边界处理错误
```java
// 错误：没有处理n<=2的情况
for (int i = 1; i <= n; i++)
```

### 3. 初始化错误
```java
// 错误：初始值设置错误
dp[0] = 1;  // 应该是dp[1] = 1
```

## 总结

### 1. 解题思路
- 识别DP特征
- 定义状态含义
- 推导转移方程
- 考虑优化方案

### 2. 关键点
- 状态定义清晰
- 转移方程正确
- 初始化准确
- 优化意识

### 3. 学习收获
- DP入门基础
- 空间优化技巧
- 数学思维培养
- 代码实现能力
