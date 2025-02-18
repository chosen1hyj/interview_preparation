# 在排序数组中查找元素的第一个和最后一个位置 (中等)

## 题目描述
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 target，返回 [-1, -1]。

要求：你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。

## 示例
输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]

## 解法
```java
public int[] searchRange(int[] nums, int target) {
    int[] result = new int[]{-1, -1};
    
    // 寻找左边界
    result[0] = findBoundary(nums, target, true);
    // 寻找右边界
    result[1] = findBoundary(nums, target, false);
    
    return result;
}

private int findBoundary(int[] nums, int target, boolean isLeft) {
    int left = 0;
    int right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            if (isLeft) {
                // 搜索左边界时，收缩右边界
                if (mid == left || nums[mid - 1] != target) {
                    return mid;
                }
                right = mid - 1;
            } else {
                // 搜索右边界时，收缩左边界
                if (mid == right || nums[mid + 1] != target) {
                    return mid;
                }
                left = mid + 1;
            }
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
1. 二分查找的变形：寻找边界
2. 分别处理左右边界
3. 边界条件的判断
4. 相等情况下的处理策略
