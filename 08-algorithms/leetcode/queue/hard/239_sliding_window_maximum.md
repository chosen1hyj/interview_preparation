# 滑动窗口最大值 (困难)

## 题目描述
给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值。

## 示例
输入: nums = [1,3,-1,-3,5,3,6,7], k = 3
输出: [3,3,5,5,6,7]

## 解法
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || k <= 0) return new int[0];
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>();
    
    for (int i = 0; i < nums.length; i++) {
        // 移除不在当前窗口范围的元素
        if (!deque.isEmpty() && deque.peek() < i - k + 1) {
            deque.poll();
        }
        
        // 移除所有小于当前元素的值
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.offer(i);
        
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peek()];
        }
    }
    
    return result;
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(k)

## 关键点
- 双端队列的应用
- 单调递减队列
- 滑动窗口技巧
