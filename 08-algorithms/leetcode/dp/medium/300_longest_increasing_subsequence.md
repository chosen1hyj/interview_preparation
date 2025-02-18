# 300. 最长递增子序列（Longest Increasing Subsequence）

[题目链接](https://leetcode.com/problems/longest-increasing-subsequence/)

## 题目描述
给你一个整数数组 nums，找到其中最长严格递增子序列的长度。

子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。

示例：
```
输入：nums = [10,9,2,5,3,7,101,18]
输出：4
解释：最长递增子序列是 [2,3,7,101]，因此长度为 4。
```

## 解法一：动态规划

### 思路
1. 定义状态：dp[i]表示以nums[i]结尾的最长递增子序列的长度
2. 状态转移：遍历i之前的所有位置j，如果nums[i] > nums[j]，则可以将nums[i]接在nums[j]后面
3. 转移方程：dp[i] = max(dp[j] + 1)，其中0 ≤ j < i且nums[i] > nums[j]

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);  // 每个元素自身就是一个长度为1的子序列
        int maxLen = 1;
        
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n²)
- 空间复杂度：O(n)

## 解法二：二分查找优化

### 思路
维护一个单调数组tails，tails[i]表示长度为i+1的递增子序列的最小末尾值。

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        
        for (int num : nums) {
            int left = 0, right = size;
            // 二分查找插入位置
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = num;
            if (left == size) size++;
        }
        
        return size;
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn)
- 空间复杂度：O(n)

## 解法三：贪心 + 二分查找（进一步优化）

### 思路
在解法二的基础上，使用更直观的写法，同时处理边界情况。

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        // d[i]表示长度为i的递增子序列的最小结尾值
        int[] d = new int[nums.length + 1];
        d[1] = nums[0];
        int len = 1;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > d[len]) {
                d[++len] = nums[i];
            } else {
                // 二分查找替换位置
                int pos = binarySearch(d, 1, len, nums[i]);
                d[pos] = nums[i];
            }
        }
        
        return len;
    }
    
    // 二分查找第一个大于等于target的位置
    private int binarySearch(int[] d, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (d[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
```

## 题目重点
1. 理解子序列和子串的区别
2. 掌握DP的状态定义和转移
3. 学会使用二分查找优化
4. 理解贪心策略的应用

## 解题技巧

### 1. 状态设计
```java
// 以nums[i]结尾的LIS长度
int[] dp = new int[nums.length];
Arrays.fill(dp, 1);  // 初始化为1
```

### 2. 二分查找模板
```java
private int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return left;
}
```

### 3. 贪心策略
```java
// 维护递增子序列的最小末尾值
if (nums[i] > last) {
    // 可以接在当前序列后面
    append(nums[i]);
} else {
    // 替换第一个大于等于nums[i]的值
    replaceFirstGreaterOrEqual(nums[i]);
}
```

## 相关题目
- [673. 最长递增子序列的个数](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)
- [674. 最长连续递增序列](https://leetcode.com/problems/longest-continuous-increasing-subsequence/)
- [354. 俄罗斯套娃信封问题](https://leetcode.com/problems/russian-doll-envelopes/)

## 延伸思考

### 1. 变种问题
- 最长不降子序列
- 最长递增子序列的个数
- 最长递增子序列的具体路径

### 2. 优化方向
- 使用二分查找降低时间复杂度
- 使用树状数组优化
- 处理重复元素

### 3. 实际应用
- 序列对比
- 数据趋势分析
- 套娃问题抽象

## 面试技巧

### 1. 解题步骤
1. 先说明暴力解法
2. 提出DP解法
3. 分析优化空间
4. 使用二分查找优化

### 2. 核心要点
- 理解子序列概念
- 掌握状态定义
- 会写二分查找
- 理解贪心思想

### 3. 代码实现
- 代码结构清晰
- 变量命名规范
- 注释充分
- 处理边界情况

## 常见错误

### 1. 状态定义错误
```java
// 错误：没有考虑以nums[i]结尾的限制
dp[i] = 到位置i的最长递增子序列长度
```

### 2. 转移条件错误
```java
// 错误：没有判断递增条件
dp[i] = Math.max(dp[i], dp[j] + 1);
```

### 3. 初始化错误
```java
// 错误：忘记初始化为1
int[] dp = new int[nums.length];
```

## 总结

### 1. 算法要点
- DP思想应用
- 贪心策略使用
- 二分查找优化
- 状态设计技巧

### 2. 优化思路
- O(n²) -> O(nlogn)
- 空间使用优化
- 代码简化
- 特殊情况处理

### 3. 学习收获
- DP技巧提升
- 二分查找应用
- 贪心思想理解
- 优化意识培养
