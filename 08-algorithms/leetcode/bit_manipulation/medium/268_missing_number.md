# 丢失的数字 (中等)

## 题目描述
给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。

要求：
你能否实现线性时间复杂度的算法，并且仅使用常数级别的额外空间？

## 示例
输入: nums = [3,0,1]
输出: 2

## 解法
```java
public int missingNumber(int[] nums) {
    int n = nums.length;
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    
    for (int num : nums) {
        actualSum += num;
    }
    
    return expectedSum - actualSum;
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(1)

## 关键点
- 数学求和公式
- 缺失值计算
- 空间优化
