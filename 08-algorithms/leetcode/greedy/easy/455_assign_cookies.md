# 455. 分发饼干（Assign Cookies）

[题目链接](https://leetcode.com/problems/assign-cookies/)

## 题目描述
假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。

对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足的最小的饼干尺寸；并且每块饼干 j，都有一个尺寸 s[j]。如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i，这个孩子会得到满足。你的目标是尽量满足越多数量的孩子，并输出这个最大数值。

示例：
```
输入: g = [1,2,3], s = [1,1]
输出: 1
解释: 
你有两个饼干，尺寸都是1。
你有三个孩子，胃口分别是1, 2, 3。
虽然你有两个小尺寸的饼干，但因为他们的尺寸都是1，只能让胃口为1的孩子满足。
所以你应该输出1。
```

## 解法：贪心算法

### 思路
1. 对孩子的胃口和饼干尺寸排序
2. 使用双指针匹配
3. 优先满足小胃口的孩子

```java
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        
        int child = 0;
        int cookie = 0;
        
        while (child < g.length && cookie < s.length) {
            if (s[cookie] >= g[child]) {
                child++;
            }
            cookie++;
        }
        
        return child;
    }
}
```

### 复杂度分析
- 时间复杂度：O(nlogn + mlogm)，其中n是孩子数量，m是饼干数量
- 空间复杂度：O(1)

## 解题技巧

### 1. 排序优化
```java
// 先对两个数组排序
Arrays.sort(g);
Arrays.sort(s);
```

### 2. 双指针
```java
// 同时遍历两个数组
while (child < g.length && cookie < s.length) { ... }
```

### 3. 贪心策略
```java
// 优先满足小胃口的孩子
if (s[cookie] >= g[child]) {
    child++;
}
```

## 相关题目
- [135. 分发糖果](https://leetcode.com/problems/candy/)
- [435. 无重叠区间](https://leetcode.com/problems/non-overlapping-intervals/)
- [605. 种花问题](https://leetcode.com/problems/can-place-flowers/)

## 延伸思考

### 1. 变种问题
- 每个孩子可以分多个饼干？
- 饼干可以切分？
- 不同口味偏好？

### 2. 优化方向
- 二分查找
- 计数排序
- 并行计算

### 3. 实际应用
- 资源分配
- 库存管理
- 任务调度

## 面试技巧

### 1. 解题步骤
1. 确定排序顺序
2. 设计双指针
3. 实现贪心逻辑
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

### 1. 边界条件
```java
// 错误：越界访问
while (child <= g.length && cookie <= s.length) { ... }
// 应该是小于
while (child < g.length && cookie < s.length) { ... }
```

### 2. 排序顺序
```java
// 错误：忘记排序
// Arrays.sort(g);
// Arrays.sort(s);
// 应该先对两个数组排序
```

### 3. 匹配逻辑
```java
// 错误：错误的匹配条件
if (s[cookie] > g[child]) { ... }
// 应该是大于等于
if (s[cookie] >= g[child]) { ... }
```

## 总结

### 1. 贪心要点
- 明确最优子结构
- 正确排序
- 合理匹配
- 处理边界情况

### 2. 优化方向
- 计数排序
- 二分查找
- BFS替代方案
- 空间优化

### 3. 技能提升
- 贪心思维
- 排序意识
- 优化能力
- 代码实现
