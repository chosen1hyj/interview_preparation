# 912. 排序数组（Sort an Array）

[题目链接](https://leetcode.com/problems/sort-an-array/)

## 题目描述
给你一个整数数组 nums，请你将该数组升序排列。

示例：
```
输入：nums = [5,2,3,1]
输出：[1,2,3,5]

输入：nums = [5,1,1,2,0,0]
输出：[0,0,1,1,2,5]
```

## 解法：归并排序

### 思路
1. 使用分治思想实现归并排序
2. 将数组不断二分，直到每个子数组只有一个元素
3. 合并两个有序数组

```java
class Solution {
    public int[] sortArray(int[] nums) {
        if (nums.length < 2) return nums;
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }
    
    private void mergeSort(int[] nums, int left, int right) {
        if (left >= right) return;
        
        int mid = left + (right - left) / 2;
        
        // 分解
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        
        // 合并
        merge(nums, left, mid, right);
    }
    
    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        
        while (i <= mid) temp[k++] = nums[i++];
        while (j <= right) temp[k++] = nums[j++];
        
        System.arraycopy(temp, 0, nums, left, temp.length);
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn)
- 空间复杂度：O(n)

## 解题技巧

### 1. 分解过程
```java
// 递归分解
int mid = left + (right - left) / 2;
mergeSort(nums, left, mid);
mergeSort(nums, mid + 1, right);
```

### 2. 合并过程
```java
// 合并两个有序数组
while (i <= mid && j <= right) {
    if (nums[i] <= nums[j]) {
        temp[k++] = nums[i++];
    } else {
        temp[k++] = nums[j++];
    }
}
```

### 3. 边界处理
```java
// 处理剩余元素
while (i <= mid) temp[k++] = nums[i++];
while (j <= right) temp[k++] = nums[j++];
```

## 相关题目
- [75. 颜色分类](https://leetcode.com/problems/sort-colors/)
- [215. 数组中的第K个最大元素](https://leetcode.com/problems/kth-largest-element-in-an-array/)
- [347. 前 K 个高频元素](https://leetcode.com/problems/top-k-frequent-elements/)

## 延伸思考

### 1. 变种问题
- 自定义排序规则？
- 稳定性要求？
- 空间优化？

### 2. 优化方向
- 迭代实现
- 小数组插入排序
- 并行计算

### 3. 实际应用
- 数据库排序
- 文件系统
- 搜索引擎
- 数据分析

## 面试技巧

### 1. 解题步骤
1. 确定递归函数定义
2. 设计基准情况
3. 实现分解逻辑
4. 完成合并操作

### 2. 代码实现
- 清晰的递归结构
- 正确的边界处理
- 高效的合并过程
- 完善的特殊情况处理

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 边界条件
```java
// 错误：忘记处理剩余元素
while (i <= mid) temp[k++] = nums[i++];
while (j <= right) temp[k++] = nums[j++];
```

### 2. 数组越界
```java
// 错误：数组下标计算错误
int mid = (left + right) / 2;  // 可能溢出
// 应该使用
int mid = left + (right - left) / 2;
```

### 3. 数据拷贝
```java
// 错误：忘记将临时数组复制回原数组
System.arraycopy(temp, 0, nums, left, temp.length);
```

## 总结

### 1. 分治要点
- 明确递归定义
- 正确划分问题
- 高效合并结果
- 处理边界情况

### 2. 优化方向
- 迭代实现
- 小数组优化
- 并行计算
- 空间优化

### 3. 技能提升
- 递归思维
- 分治意识
- 优化能力
- 代码实现
