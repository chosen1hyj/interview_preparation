# 动态规划基础知识

## 基本概念

### 1. 动态规划的特点
- 问题可以分解为相互重叠的子问题
- 子问题的解可以被重复使用
- 存在最优子结构
- 通常自底向上解决问题

### 2. 动态规划解题步骤
1. 定义状态（确定dp数组的含义）
2. 找出状态转移方程
3. 确定初始条件和边界情况
4. 确定遍历顺序
5. 实现代码

## 常见DP类型

### 1. 线性DP
```java
// 一维DP数组
dp[i] 表示以第i个元素结尾的某种状态

// 常见转移方程示例
dp[i] = Math.max(dp[i-1] + nums[i], nums[i])  // 最大子序和
dp[i] = dp[i-1] + dp[i-2]                     // 斐波那契数列
```

### 2. 区间DP
```java
// 二维DP数组
dp[i][j] 表示区间[i,j]的某种状态

// 常见转移方程示例
dp[i][j] = dp[i+1][j-1] && s.charAt(i) == s.charAt(j)  // 回文串判断
dp[i][j] = Math.min(dp[i][k] + dp[k+1][j]) + cost      // 区间合并
```

### 3. 背包DP
```java
// 0-1背包
dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weight[i]] + value[i])

// 完全背包
dp[i][j] = Math.max(dp[i-1][j], dp[i][j-weight[i]] + value[i])
```

### 4. 状态压缩DP
```java
// 使用二进制表示状态
dp[state] 表示某个状态下的结果

// 常见操作
state | (1 << i)    // 设置第i位为1
state & ~(1 << i)   // 设置第i位为0
state & (1 << i)    // 检查第i位是否为1
```

## 常见优化技巧

### 1. 空间优化
```java
// 原始二维DP
dp[i][j] = f(dp[i-1][j], dp[i][j-1])

// 优化为一维
dp[j] = f(dp[j], dp[j-1])
```

### 2. 状态压缩
```java
// 使用滚动数组
int[] dp = new int[2];
dp[i % 2] = f(dp[(i-1) % 2])
```

### 3. 初始化技巧
```java
// 处理边界情况
Arrays.fill(dp, Integer.MIN_VALUE);  // 求最大值时
Arrays.fill(dp, Integer.MAX_VALUE);  // 求最小值时
```

## 经典DP问题

### 1. 路径类问题
```java
// 不同路径
dp[i][j] = dp[i-1][j] + dp[i][j-1]

// 最小路径和
dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
```

### 2. 序列类问题
```java
// 最长递增子序列
dp[i] = max(dp[j] + 1) where j < i && nums[j] < nums[i]

// 最长公共子序列
dp[i][j] = dp[i-1][j-1] + 1 if s1[i] == s2[j]
dp[i][j] = max(dp[i-1][j], dp[i][j-1]) if s1[i] != s2[j]
```

### 3. 背包类问题
```java
// 0-1背包
for (int i = 1; i <= n; i++) {
    for (int j = W; j >= w[i]; j--) {
        dp[j] = Math.max(dp[j], dp[j-w[i]] + v[i]);
    }
}

// 完全背包
for (int i = 1; i <= n; i++) {
    for (int j = w[i]; j <= W; j++) {
        dp[j] = Math.max(dp[j], dp[j-w[i]] + v[i]);
    }
}
```

## 解题技巧

### 1. 状态定义
- 明确dp数组的含义
- 考虑是否需要额外维度
- 是否可以压缩状态

### 2. 转移方程
- 找出子问题之间的关系
- 考虑所有可能的转移
- 注意边界条件

### 3. 代码实现
```java
// 典型实现模板
// 1. 定义dp数组
int[] dp = new int[n + 1];

// 2. 初始化
dp[0] = 0;

// 3. 状态转移
for (int i = 1; i <= n; i++) {
    dp[i] = f(dp[i-1]);
}

// 4. 返回结果
return dp[n];
```

## 常见错误

### 1. 状态定义错误
- dp数组含义不清晰
- 维度选择不当
- 忽略重要状态

### 2. 转移方程错误
- 遗漏某些转移情况
- 转移方向错误
- 边界处理不当

### 3. 初始化错误
- 初始值设置错误
- 遗漏某些初始状态
- 边界条件处理不当

## 优化思路

### 1. 时间优化
- 减少状态数量
- 优化状态转移
- 使用辅助数据结构

### 2. 空间优化
- 状态压缩
- 滚动数组
- 原地修改

### 3. 代码优化
- 提前剪枝
- 使用备忘录
- 合理使用数据结构

## 实战建议

### 1. 练习顺序
1. 基础线性DP
2. 二维DP
3. 背包问题
4. 区间DP
5. 状态压缩DP

### 2. 解题步骤
1. 明确状态定义
2. 写出转移方程
3. 确定边界条件
4. 考虑优化方案
### 典型例题

#### LeetCode 70 - 爬楼梯 (简单)
- 斐波那契数列
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    int prev = 1, curr = 2;
    for (int i = 3; i <= n; i++) {
        int temp = curr;
        curr += prev;
        prev = temp;
    }
    return curr;
}
```

#### LeetCode 300 - 最长递增子序列 (中等)
- 二分查找优化
```java
public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int size = 0;
    for (int x : nums) {
        int i = 0, j = size;
        while (i != j) {
            int m = (i + j) / 2;
            if (tails[m] < x)
                i = m + 1;
            else
                j = m;
        }
        tails[i] = x;
        if (i == size) ++size;
    }
    return size;
}
```

#### LeetCode 72 - 编辑距离 (困难)
- 双字符串DP
```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m+1][n+1];
    
    // 初始化边界
    for (int i = 0; i <= m; i++) {
        dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
        dp[0][j] = j;
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
```

### 3. 提高方法
