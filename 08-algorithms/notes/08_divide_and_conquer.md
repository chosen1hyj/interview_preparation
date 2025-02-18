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

## 总结
分治算法虽然递归调用会带来额外开销，但其清晰的结构和可并行化的特性使其在解决许多复杂问题时非常有效。掌握其基本框架和优化技巧，可以应对各种复杂的算法设计问题。
