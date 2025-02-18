# 二分查找 (简单)

## 题目描述
给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。

## 示例
输入: nums = [-1,0,3,5,9,12], target = 9
输出: 4
解释: 9 出现在 nums 中并且下标为 4

## 解法
```java
public int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) {
        return -1;
    }
    
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) {
        // 防止整数溢出
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

## 复杂度分析
- 时间复杂度: O(logn)
- 空间复杂度: O(1)

## 关键点
1. 循环条件：left <= right
2. 中点计算：防止整数溢出
3. 区间更新：包含或不包含mid
4. 边界处理：空数组和目标值不存在的情况
