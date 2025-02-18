# 贪心算法基础知识

## 基本概念

### 1. 贪心算法的定义
- 在对问题求解时，总是做出在当前看来最好的选择
- 不从整体最优考虑，只关注局部最优解
- 期望通过局部最优选择导致全局最优解

### 2. 贪心算法的特点
- 算法简单且高效
- 推导过程难，正确性证明复杂
- 不是所有问题都能用贪心解决
- 需要证明局部最优能导致全局最优

## 贪心算法应用场景

### 1. 活动选择问题
```java
// 按结束时间排序，选择不冲突的活动
public int maxActivities(int[] start, int[] end) {
    // 按结束时间排序
    sortByEndTime(start, end);
    
    int count = 1;  // 第一个活动一定选
    int lastEnd = end[0];
    
    for (int i = 1; i < end.length; i++) {
        if (start[i] >= lastEnd) {
            count++;
            lastEnd = end[i];
        }
    }
    
    return count;
}
```

### 2. 区间调度问题
```java
// 重叠区间的合并
public int[][] merge(int[][] intervals) {
    // 按开始时间排序
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> merged = new ArrayList<>();
    merged.add(intervals[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        int[] last = merged.get(merged.size() - 1);
        if (intervals[i][0] <= last[1]) {
            // 有重叠，合并区间
            last[1] = Math.max(last[1], intervals[i][1]);
        } else {
            // 无重叠，添加新区间
            merged.add(intervals[i]);
        }
    }
    
    return merged.toArray(new int[merged.size()][]);
}
```

### 3. 分配问题
```java
// 分糖果问题
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);  // 胃口值
    Arrays.sort(s);  // 糖果大小
    
    int child = 0, cookie = 0;
    while (child < g.length && cookie < s.length) {
        if (s[cookie] >= g[child]) {
            child++;
        }
        cookie++;
    }
    
    return child;
}
```

## 贪心策略的设计

### 1. 基本步骤
1. 将问题分解为若干个子问题
2. 找出适合的贪心策略
3. 求解每个子问题的局部最优解
4. 将局部最优解组合成原问题的解

### 2. 常见贪心策略
- 按最小/最大元素选择
- 按比例排序
- 按差值排序
- 按频率排序

### 3. 正确性证明
- 归纳法证明
- 反证法证明
- 与其他解法对比证明

## 实现技巧

### 1. 排序预处理
```java
// 根据特定规则排序
Arrays.sort(arr, (a, b) -> {
    // 自定义比较规则
    return compare(a, b);
});
```

### 2. 优先队列使用
```java
// 维护动态最值
PriorityQueue<Integer> pq = new PriorityQueue<>();
// 或大顶堆
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
```

### 3. 区间处理
```java
// 区间按起点或终点排序
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);  // 按起点
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // 按终点
```

## 常见问题类型

### 1. 区间问题
- 区间选择
- 区间合并
- 区间覆盖

### 2. 排序贪心
- 按某种规则排序后进行选择
- 基于排序的调度问题

### 3. 数值贪心
- 最大/最小值选择
- 数值分配问题

### 4. 字符串贪心
- 字符串重构
- 字符串匹配

## 与其他算法的比较

### 1. 贪心 vs 动态规划
- 贪心：每步只看局部最优
- DP：考虑所有可能的情况
- 贪心效率高但不一定最优
- DP保证最优但复杂度高

### 2. 贪心 vs 回溯
- 贪心：不回退，一次选择
- 回溯：可以回退，尝试所有可能
- 贪心更高效但可能次优
- 回溯保证找到最优解

## 面试技巧

### 1. 解题步骤
1. 分析问题是否适合贪心
2. 设计并证明贪心策略
3. 考虑特殊情况
4. 实现代码

### 2. 常见陷阱
- 贪心策略不够严谨
- 没有考虑特殊情况
- 排序规则设计错误
- 边界条件处理不当

### 3. 代码实现
- 合理使用排序
- 正确处理边界情况
- 注意代码效率
- 考虑代码可读性

## 实战建议

### 1. 练习顺序
1. 基础贪心问题
2. 区间类问题
3. 排序贪心问题
4. 复杂贪心问题

### 2. 重要概念
- 贪心策略的设计
- 正确性的证明
- 特殊情况的处理
- 最优解的保证

### 3. 解题方法
- 画图帮助理解
- 手动模拟过程
- 举反例验证
- 考虑边界情况
### 典型例题

#### LeetCode 455 - 分发饼干 (简单)
- 贪心匹配
```java
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);
    Arrays.sort(s);
    
    int i = 0, j = 0;
    while (i < g.length && j < s.length) {
        if (s[j] >= g[i]) {
            i++;
        }
        j++;
    }
    return i;
}
```

#### LeetCode 55 - 跳跃游戏 (中等)
- 最远可达位置
```java
public boolean canJump(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false;
        maxReach = Math.max(maxReach, i + nums[i]);
        if (maxReach >= nums.length - 1) return true;
    }
    return false;
}
```

#### LeetCode 435 - 无重叠区间 (困难)
- 区间调度
```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // 按结束时间排序
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 1;  // 至少保留一个区间
    int end = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= end) {
            count++;
            end = intervals[i][1];
        }
    }
    
    return intervals.length - count;
}
```

### 4. 注意事项
