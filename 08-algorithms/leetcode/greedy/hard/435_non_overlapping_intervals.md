# 435. 无重叠区间（Non-overlapping Intervals）

[题目链接](https://leetcode.com/problems/non-overlapping-intervals/)

## 题目描述
给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。

注意：
1. 可以认为区间的终点总是大于其起点。
2. 区间 [1,2] 和 [2,3] 的边界相互"接触"，但没有相互重叠。

示例：
```
输入: [[1,2],[2,3],[3,4],[1,3]]
输出: 1
解释: 移除 [1,3] 后，剩下的区间没有重叠。

输入: [[1,2],[1,2],[1,2]]
输出: 2
解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
```

## 解法一：贪心算法（按结束时间排序）

### 思路
1. 将区间按结束时间排序
2. 贪心地选择结束时间最早的区间
3. 统计需要移除的区间数量

```java
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // 按结束时间排序
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        
        int removals = 0;
        int prevEnd = intervals[0][1];
        
        // 遍历所有区间
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prevEnd) {
                // 当前区间与前一个区间重叠，需要移除
                removals++;
            } else {
                // 不重叠，更新结束时间
                prevEnd = intervals[i][1];
            }
        }
        
        return removals;
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn)，主要是排序的时间复杂度
- 空间复杂度：O(1)，只使用了常数额外空间

## 解法二：贪心算法（按开始时间排序）

### 思路
也可以按开始时间排序，在重叠时保留结束时间较早的区间。

```java
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // 按开始时间排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        int removals = 0;
        int prevEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prevEnd) {
                // 重叠时保留结束时间较早的区间
                removals++;
                prevEnd = Math.min(prevEnd, intervals[i][1]);
            } else {
                prevEnd = intervals[i][1];
            }
        }
        
        return removals;
    }
}
```

## 题目重点
1. 区间问题的贪心策略选择
2. 排序方式的选择
3. 重叠区间的判断
4. 最优解的证明

## 解题技巧

### 1. 排序策略
```java
// 按结束时间排序
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

// 按开始时间排序
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
```

### 2. 重叠判断
```java
// 判断两个区间是否重叠
if (intervals[i][0] < prevEnd) {
    // 重叠
}
```

### 3. 贪心选择
```java
// 在重叠时选择结束时间较早的区间
prevEnd = Math.min(prevEnd, intervals[i][1]);
```

## 相关题目
- [56. 合并区间](https://leetcode.com/problems/merge-intervals/)
- [452. 用最少数量的箭引爆气球](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)
- [1288. 删除被覆盖区间](https://leetcode.com/problems/remove-covered-intervals/)

## 延伸思考

### 1. 变种问题
- 如果要求保留特定区间？
- 如果区间有权重？
- 如果允许部分重叠？

### 2. 优化方向
- 排序方式的选择
- 空间使用优化
- 特殊情况处理

### 3. 实际应用
- 会议室安排
- 资源调度
- 任务规划

## 面试技巧

### 1. 解题步骤
1. 分析问题特点
2. 选择排序策略
3. 设计贪心算法
4. 证明解的正确性

### 2. 代码实现
- 合理的排序方式
- 清晰的重叠判断
- 正确的贪心选择
- 完善的边界处理

### 3. 复杂度分析
- 排序的影响
- 空间使用分析
- 优化可能性

## 常见错误

### 1. 排序策略错误
```java
// 错误：没有考虑到最优排序策略
Arrays.sort(intervals);  // 应该按结束时间或开始时间排序
```

### 2. 重叠判断错误
```java
// 错误：判断条件写反
if (intervals[i][0] > prevEnd) {
    removals++;
}
```

### 3. 更新逻辑错误
```java
// 错误：更新条件不当
prevEnd = intervals[i][1];  // 应该在不重叠时才更新
```

## 总结

### 1. 问题分析
- 区间重叠特点
- 贪心策略选择
- 排序方式影响
- 最优解证明

### 2. 解题思路
- 排序预处理
- 贪心选择
- 重叠处理
- 结果统计

### 3. 代码实现
- 排序实现
- 遍历处理
- 边界条件
- 结果返回

### 4. 优化方向
- 选择最优排序
- 优化空间使用
- 处理特殊情况
- 提高代码可读性
