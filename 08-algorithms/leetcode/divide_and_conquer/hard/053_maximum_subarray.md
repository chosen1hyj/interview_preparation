# 53. 最大子数组和（Maximum Subarray）

[题目链接](https://leetcode.com/problems/maximum-subarray/)

## 题目描述
给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

示例：
```
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6。
```

## 解法：分治算法

### 思路
1. 将数组分为左右两部分
2. 最大子数组可能出现在：
   - 左半部分
   - 右半部分
   - 跨越中间的部分
3. 递归计算各部分的最大值

```java
class Solution {
    public int maxSubArray(int[] nums) {
        return divideAndConquer(nums, 0, nums.length - 1);
    }
    
    private int divideAndConquer(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        
        int mid = left + (right - left) / 2;
        
        // 左半部分最大值
        int leftMax = divideAndConquer(nums, left, mid);
        // 右半部分最大值
        int rightMax = divideAndConquer(nums, mid + 1, right);
        // 跨越中间的最大值
        int crossMax = findCrossingMax(nums, left, mid, right);
        
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private int findCrossingMax(int[] nums, int left, int mid, int right) {
        // 左侧最大和
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        
        // 右侧最大和
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        
        return leftSum + rightSum;
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn)
- 空间复杂度：O(logn)

## 解题技巧

### 1. 分治核心
```java
// 计算跨越中间的最大值
int crossMax = findCrossingMax(nums, left, mid, right);
return Math.max(Math.max(leftMax, rightMax), crossMax);
```

### 2. 跨越中间计算
```java
// 左侧最大和
for (int i = mid; i >= left; i--) {
    sum += nums[i];
    leftSum = Math.max(leftSum, sum);
}

// 右侧最大和
for (int i = mid + 1; i <= right; i++) {
    sum += nums[i];
    rightSum = Math.max(rightSum, sum);
}
```

## 相关题目
- [152. 乘积最大子数组](https://leetcode.com/problems/maximum-product-subarray/)
- [918. 环形子数组的最大和](https://leetcode.com/problems/maximum-sum-circular-subarray/)
- [643. 子数组最大平均数 I](https://leetcode.com/problems/maximum-average-subarray-i/)

## 延伸思考

### 1. 变种问题
- 包含k个元素的子数组？
- 返回子数组本身？
- 多维数组？

### 2. 优化方向
- 动态规划实现
- 线段树优化
- 并行计算

### 3. 实际应用
- 股票交易
- 数据分析
- 信号处理
- 金融建模

## 面试技巧

### 1. 解题步骤
1. 确定递归定义
2. 设计基准情况
3. 实现分解逻辑
4. 完成合并操作

### 2. 代码实现
- 清晰的递归结构
- 正确的边界处理
- 高效的计算过程
- 完善的特殊情况处理

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 边界条件
```java
// 错误：忘记处理单元素情况
if (left == right) return nums[left];
```

### 2. 中间计算
```java
// 错误：跨越中间计算不完整
int crossMax = nums[mid] + nums[mid+1];
// 应该分别计算左右两侧最大值
```

### 3. 最大值更新
```java
// 错误：最大值更新不正确
sum += nums[i];
max = sum;
// 应该比较取最大值
max = Math.max(max, sum);
```

## 总结

### 1. 分治要点
- 明确递归定义
- 正确划分问题
- 高效合并结果
- 处理边界情况

### 2. 优化方向
- 迭代实现
- 小数据优化
- 并行计算
- 空间优化

### 3. 技能提升
- 递归思维
- 分治意识
- 优化能力
- 代码实现
