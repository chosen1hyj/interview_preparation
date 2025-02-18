# 435. 无重叠区间（Non-overlapping Intervals）

[题目链接](https://leetcode.com/problems/non-overlapping-intervals/)

## 题目描述
给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。

注意:
1. 可以认为区间的终点总是大于它的起点。
2. 区间 [1,2] 和 [2,3] 的边界相互"接触"，但没有相互重叠。

示例：
```
输入: [[1,2], [2,3], [3,4], [1,3]]
输出: 1
解释: 移除 [1,3] 后，剩下的区间没有重叠。
```

## 解法：贪心算法

### 思路
1. 按区间结束位置排序
2. 维护当前最右边界
3. 计数需要移除的区间

```java
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        // 按结束位置排序
        Arrays.sort(intervals, (a,b) -> a[1] - b[1]);
        
        int count = 0;
        int end = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                // 重叠，需要移除
                count++;
            } else {
                // 不重叠，更新end
                end = intervals[i][1];
            }
        }
        
        return count;
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn)，其中n是区间数量
- 空间复杂度：O(1)

## 解题技巧

### 1. 排序策略
```java
// 按结束位置升序排序
Arrays.sort(intervals, (a,b) -> a[1] - b[1]);
```

### 2. 贪心选择
```java
// 优先保留结束位置小的区间
if (intervals[i][0] < end) {
    count++;
} else {
    end = intervals[i][1];
}
```

### 3. 边界处理
```java
// 特殊情况处理
if (intervals.length == 0) return 0;
```

## 相关题目
- [452. 用最少数量的箭引爆气球](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/)
- [252. 会议室](https://leetcode.com/problems/meeting-rooms/)
- [253. 会议室 II](https://leetcode.com/problems/meeting-rooms-ii/)

## 延伸思考

### 1. 变种问题
- 区间权重？
- 最大重叠点？
- 动态添加区间？

### 2. 优化方向
- 线段树
- 扫描线算法
- 并行计算

### 3. 实际应用
- 任务调度
- 资源分配
- 日程安排
- 缓存管理

## 面试技巧

### 1. 解题步骤
1. 确定排序顺序
2. 设计贪心策略
3. 实现区间判断
4. 收集最终结果

### 2. 代码实现
- 完善的边界检查
- 正确的状态维护
- 清晰的搜索逻辑
- 准确的结果统计

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 排序条件
```java
// 错误：按起始位置排序
Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
// 应该按结束位置排序
Arrays.sort(intervals, (a,b) -> a[1] - b[1]);
```

### 2. 区间判断
```java
// 错误：错误的重叠条件
if (intervals[i][0] <= end) { ... }
// 应该是小于
if (intervals[i][0] < end) { ... }
```

### 3. 边界更新
```java
// 错误：遗漏更新
if (intervals[i][0] >= end) {
    // 缺少更新
    end = intervals[i][1];
}
```

## 总结

### 1. 贪心要点
- 明确最优子结构
- 正确排序
- 合理判断
- 处理边界情况

### 2. 优化方向
- 线段树
- 扫描线算法
- BFS替代方案
- 空间优化

### 3. 技能提升
- 贪心思维
- 排序意识
- 优化能力
- 代码实现
