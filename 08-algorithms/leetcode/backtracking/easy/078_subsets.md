# 78. 子集（Subsets）

[题目链接](https://leetcode.com/problems/subsets/)

## 题目描述
给定一个不含重复元素的整数数组 nums，返回该数组所有可能的子集。

示例：
```
输入：nums = [1,2,3]
输出：
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]
```

## 解法：回溯算法

### 思路
1. 使用回溯法构建所有子集
2. 每个元素都有两种选择：选或不选
3. 递归生成所有组合

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, 
                           List<Integer> current, List<List<Integer>> result) {
        // 添加当前子集
        result.add(new ArrayList<>(current));
        
        // 选择列表
        for (int i = start; i < nums.length; i++) {
            // 做选择
            current.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, i + 1, current, result);
            // 撤销选择
            current.remove(current.size() - 1);
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(2^n)，其中n是数组长度
- 空间复杂度：O(n)，递归栈深度

## 解题技巧

### 1. 回溯核心
```java
// 回溯三部曲
result.add(new ArrayList<>(current)); // 收集结果
current.add(nums[i]);                 // 做选择
backtrack(nums, i + 1, current, result); // 递归
current.remove(current.size() - 1);   // 撤销选择
```

### 2. 剪枝优化
```java
// 利用start参数避免重复
for (int i = start; i < nums.length; i++) { ... }
```

### 3. 结果收集
```java
// 深拷贝当前子集
result.add(new ArrayList<>(current));
```

## 相关题目
- [90. 子集 II](https://leetcode.com/problems/subsets-ii/)
- [46. 全排列](https://leetcode.com/problems/permutations/)
- [77. 组合](https://leetcode.com/problems/combinations/)

## 延伸思考

### 1. 变种问题
- 包含重复元素？
- 返回特定大小的子集？
- 字符串版本？

### 2. 优化方向
- 迭代实现
- 位运算方法
- 并行计算

### 3. 实际应用
- 数据挖掘
- 特征选择
- 密码学
- 排列组合

## 面试技巧

### 1. 解题步骤
1. 确定选择列表
2. 设计终止条件
3. 实现回溯逻辑
4. 收集结果集合

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
// 错误：忘记添加空集
if (start == nums.length) return;
// 应该先添加当前子集
result.add(new ArrayList<>(current));
```

### 2. 状态恢复
```java
// 错误：遗漏撤销操作
current.add(nums[i]);
backtrack(nums, i + 1, current, result);
// 缺少撤销
current.remove(current.size() - 1);
```

### 3. 结果收集
```java
// 错误：浅拷贝导致结果错误
result.add(current);
// 应该深拷贝
result.add(new ArrayList<>(current));
```

## 总结

### 1. 回溯要点
- 明确选择列表
- 正确维护状态
- 完整的撤销操作
- 处理边界情况

### 2. 优化方向
- 迭代实现
- 位运算替代
- BFS替代方案
- 空间优化

### 3. 技能提升
- 回溯思维
- 状态管理意识
- 优化能力
- 代码实现
