# 数组中的第K个最大元素 (中等)

## 题目描述
给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。

请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

## 示例
输入: [3,2,1,5,6,4], k = 2
输出: 5

## 解法一：快速选择
```java
public int findKthLargest(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length - 1, nums.length - k);
}

private int quickSelect(int[] nums, int left, int right, int k) {
    if (left == right) return nums[left];
    
    int pivot = partition(nums, left, right);
    
    if (pivot == k) {
        return nums[k];
    } else if (pivot < k) {
        return quickSelect(nums, pivot + 1, right, k);
    } else {
        return quickSelect(nums, left, pivot - 1, k);
    }
}

private int partition(int[] nums, int left, int right) {
    int pivot = nums[right];
    int i = left;
    
    for (int j = left; j < right; j++) {
        if (nums[j] <= pivot) {
            swap(nums, i, j);
            i++;
        }
    }
    
    swap(nums, i, right);
    return i;
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

## 解法二：优先队列
```java
public int findKthLargest(int[] nums, int k) {
    // 创建一个最小堆
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    
    // 维护一个大小为k的最小堆
    for (int num : nums) {
        pq.offer(num);
        if (pq.size() > k) {
            pq.poll();
        }
    }
    
    return pq.peek();
}
```

## 复杂度分析
- 解法一（快速选择）：
  - 时间复杂度：平均O(n)，最坏O(n²)
  - 空间复杂度：O(1)
- 解法二（优先队列）：
  - 时间复杂度：O(nlogk)
  - 空间复杂度：O(k)

## 关键点
1. 快速选择算法的应用
2. 最小堆的维护技巧
3. 基于比较的选择算法
4. 优化常数时间的策略
