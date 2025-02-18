# 分治算法（Divide and Conquer）

## 基本概念
分治算法是一种重要的算法设计技术，其核心思想是将一个复杂的问题分解成两个或更多的相同或相似的子问题，直到最后子问题可以简单地直接求解，原问题的解即子问题解的合并。

## 核心思想
1. 分解（Divide）：将原问题分解为若干个规模较小、相互独立的子问题
2. 解决（Conquer）：递归地解决这些子问题
3. 合并（Combine）：将子问题的解合并成原问题的解

## 典型应用场景
- 排序算法（快速排序、归并排序）
- 搜索算法（二分查找）
- 数值计算（大整数乘法、矩阵乘法）
- 几何问题（最近点对问题）
- 动态规划优化

## 实现框架
```java
public ReturnType divideAndConquer(Problem problem) {
    if (problem.size < threshold) {
        return solveDirectly(problem);
    }
    
    // 分解
    List<SubProblem> subProblems = split(problem);
    
    // 解决子问题
    List<ReturnType> subResults = new ArrayList<>();
    for (SubProblem sub : subProblems) {
        subResults.add(divideAndConquer(sub));
    }
    
    // 合并结果
    return merge(subResults);
}
```

## 优化技巧
1. 平衡划分：确保子问题规模尽可能相等
2. 避免重复计算：使用缓存存储中间结果
3. 提前终止：当找到解时立即返回
4. 尾递归优化：减少递归调用栈深度

## 注意事项
1. 确定基准情况
2. 正确划分问题
3. 高效合并结果
4. 处理边界条件

## 时间复杂度分析
通常遵循主定理（Master Theorem）：
T(n) = aT(n/b) + f(n)

## 空间复杂度分析
主要由递归深度决定，通常为O(logn)

## 实际应用
1. 数据处理
2. 图像处理
3. 信号处理
4. 并行计算
5. 网络路由

## 面试技巧
1. 明确递归定义
2. 确定base case
3. 设计划分策略
4. 实现合并逻辑

## 常见错误
1. 错误的基准条件
2. 不正确的划分方式
3. 忘记合并结果
4. 边界处理不当
### 典型例题

#### LeetCode 53 - 最大子序和 (简单)
- 分治实现
```java
public int maxSubArray(int[] nums) {
    return helper(nums, 0, nums.length - 1);
}

private int helper(int[] nums, int left, int right) {
    if (left == right) return nums[left];
    
    int mid = left + (right - left) / 2;
    
    // 左半部分最大值
    int leftMax = helper(nums, left, mid);
    // 右半部分最大值
    int rightMax = helper(nums, mid + 1, right);
    // 跨越中间的最大值
    int crossMax = crossSum(nums, left, right, mid);
    
    return Math.max(Math.max(leftMax, rightMax), crossMax);
}

private int crossSum(int[] nums, int left, int right, int mid) {
    // 从中间向左扩展
    int sum = 0;
    int leftSum = Integer.MIN_VALUE;
    for (int i = mid; i >= left; i--) {
        sum += nums[i];
        leftSum = Math.max(leftSum, sum);
    }
    
    // 从中间向右扩展
    sum = 0;
    int rightSum = Integer.MIN_VALUE;
    for (int i = mid + 1; i <= right; i++) {
        sum += nums[i];
        rightSum = Math.max(rightSum, sum);
    }
    
    return leftSum + rightSum;
}
```

#### LeetCode 912 - 排序数组 (中等)
- 归并排序
```java
public int[] sortArray(int[] nums) {
    if (nums.length < 2) return nums;
    mergeSort(nums, 0, nums.length - 1);
    return nums;
}

private void mergeSort(int[] nums, int left, int right) {
    if (left >= right) return;
    
    int mid = left + (right - left) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    merge(nums, left, mid, right);
}

private void merge(int[] nums, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    
    while (i <= mid && j <= right) {
        temp[k++] = nums[i] < nums[j] ? nums[i++] : nums[j++];
    }
    
    while (i <= mid) {
        temp[k++] = nums[i++];
    }
    
    while (j <= right) {
        temp[k++] = nums[j++];
    }
    
    System.arraycopy(temp, 0, nums, left, temp.length);
}
```

#### LeetCode 50 - Pow(x, n) (中等)
- 快速幂
```java
public double myPow(double x, int n) {
    if (n == 0) return 1;
    if (n < 0) {
        x = 1 / x;
        n = -n;
    }
    return fastPow(x, n);
}

private double fastPow(double x, long n) {
    if (n == 0) return 1.0;
    double half = fastPow(x, n / 2);
    if (n % 2 == 0) {
        return half * half;
    } else {
        return half * half * x;
    }
}
```

## 总结
