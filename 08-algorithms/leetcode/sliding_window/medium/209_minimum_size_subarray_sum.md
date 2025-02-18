# 长度最小的子数组 (中等)

## 题目描述
给定一个含有 n 个正整数的数组和一个正整数 target，找出该数组中满足其和 ≥ target 的长度最小的连续子数组，并返回其长度。如果不存在符合条件的子数组，返回 0。

## 示例
输入：target = 7, nums = [2,3,1,2,4,3]
输出：2
解释：子数组 [4,3] 是该条件下的长度最小的子数组。

## 解法
```java
public int minSubArrayLen(int target, int[] nums) {
    if (nums == null || nums.length == 0) {
        return 0;
    }
    
    int left = 0, right = 0;
    int sum = 0;
    int minLength = Integer.MAX_VALUE;
    
    while (right < nums.length) {
        sum += nums[right];
        right++;
        
        while (sum >= target) {
            minLength = Math.min(minLength, right - left);
            sum -= nums[left];
            left++;
        }
    }
    
    return minLength == Integer.MAX_VALUE ? 0 : minLength;
}
```

## 复杂度分析
- 时间复杂度: O(n)，每个元素最多被访问两次
- 空间复杂度: O(1)

## 关键点
1. 滑动窗口的应用
2. 注意窗口收缩条件
3. 和的维护方式
4. 最小长度的更新时机
