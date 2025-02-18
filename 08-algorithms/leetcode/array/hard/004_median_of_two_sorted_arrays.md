# 4. 寻找两个正序数组的中位数（Median of Two Sorted Arrays）

[题目链接](https://leetcode.com/problems/median-of-two-sorted-arrays/)

## 题目描述
给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。

算法的时间复杂度应该为 O(log (m+n))。

示例：
```
输入：nums1 = [1,3], nums2 = [2]
输出：2.00000
解释：合并数组 = [1,2,3]，中位数 2

输入：nums1 = [1,2], nums2 = [3,4]
输出：2.50000
解释：合并数组 = [1,2,3,4]，中位数 (2 + 3) / 2 = 2.5
```

## 解法一：合并数组（简单但不符合要求）

### 思路
1. 合并两个有序数组
2. 找到中位数位置的元素

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;
        
        // 合并两个有序数组
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        // 处理剩余元素
        while (i < nums1.length) {
            merged[k++] = nums1[i++];
        }
        while (j < nums2.length) {
            merged[k++] = nums2[j++];
        }
        
        // 计算中位数
        int mid = merged.length / 2;
        if (merged.length % 2 == 0) {
            return (merged[mid - 1] + merged[mid]) / 2.0;
        } else {
            return merged[mid];
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(m+n)
- 空间复杂度：O(m+n)

## 解法二：二分查找（最优解）

### 思路
1. 将问题转化为寻找第k小的数
2. 使用二分查找，每次排除k/2个数
3. 递归处理，直到找到目标数

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        
        if (total % 2 == 0) {
            // 如果总长度为偶数，需要找第 total/2 和 total/2+1 个数的平均值
            return (findKth(nums1, 0, nums2, 0, total/2) + 
                    findKth(nums1, 0, nums2, 0, total/2 + 1)) / 2.0;
        } else {
            // 如果总长度为奇数，需要找第 (total/2)+1 个数
            return findKth(nums1, 0, nums2, 0, total/2 + 1);
        }
    }
    
    private double findKth(int[] nums1, int start1, int[] nums2, int start2, int k) {
        // 如果数组1已经用完，直接从数组2中返回第k个数
        if (start1 >= nums1.length) {
            return nums2[start2 + k - 1];
        }
        // 如果数组2已经用完，直接从数组1中返回第k个数
        if (start2 >= nums2.length) {
            return nums1[start1 + k - 1];
        }
        // 如果k=1，返回两个数组当前位置的最小值
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        
        // 比较两个数组第k/2个数的大小
        int mid = k/2;
        int mid1 = start1 + mid - 1 < nums1.length ? nums1[start1 + mid - 1] : Integer.MAX_VALUE;
        int mid2 = start2 + mid - 1 < nums2.length ? nums2[start2 + mid - 1] : Integer.MAX_VALUE;
        
        if (mid1 < mid2) {
            // 排除nums1的前k/2个数
            return findKth(nums1, start1 + mid, nums2, start2, k - mid);
        } else {
            // 排除nums2的前k/2个数
            return findKth(nums1, start1, nums2, start2 + mid, k - mid);
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(log(m+n))
- 空间复杂度：O(log(m+n))，递归调用栈的深度

## 题目重点
1. 要求时间复杂度为O(log(m+n))
2. 两个数组都是有序的
3. 需要考虑奇偶两种情况

## 解题技巧

### 1. 二分查找的思路
- 每次排除k/2个数
- 比较两个数组中第k/2个数的大小
- 递归缩小查找范围

### 2. 边界条件处理
- 数组为空的情况
- 数组长度为1的情况
- 两个数组长度差异很大的情况

### 3. 优化方向
- 始终在较短的数组上二分查找
- 利用数组有序的特性
- 提前处理特殊情况

## 相似题目
- [295. 数据流的中位数](https://leetcode.com/problems/find-median-from-data-stream/)
- [480. 滑动窗口中位数](https://leetcode.com/problems/sliding-window-median/)

## 易错点
1. 边界条件的处理
2. 奇偶情况的判断
3. 二分查找的终止条件
4. 整数溢出问题

## 补充知识：二分查找

### 1. 二分查找的基本模板
```java
int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
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

### 2. 二分查找的变形
1. **查找第一个等于target的位置**
```java
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] >= target) {
        right = mid - 1;
    } else {
        left = mid + 1;
    }
}
return left;
```

2. **查找最后一个等于target的位置**
```java
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] <= target) {
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
return right;
```

### 3. 二分查找的注意事项
- 循环条件：left <= right 还是 left < right
- 中间位置计算：避免整数溢出
- 边界更新：mid + 1 还是 mid
- 返回值的选择：left 还是 right

### 4. 二分查找的应用场景
- 有序数组中的查找
- 旋转数组中的查找
- 查找峰值
- 二分答案
