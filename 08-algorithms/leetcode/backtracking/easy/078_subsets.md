# 78. 子集（Subsets）

[题目链接](https://leetcode.com/problems/subsets/)

## 题目描述
给定一个不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。

解集不能包含重复的子集。你可以按任意顺序返回解集。

示例：
```
输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

输入：nums = [0]
输出：[[],[0]]
```

## 解法：回溯算法

### 思路
1. 使用回溯算法生成所有子集
2. 每个元素有两种选择：选或不选
3. 递归地构建所有可能组合

```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, 
                           List<Integer> path, List<List<Integer>> result) {
        // 添加当前路径到结果
        result.add(new ArrayList<>(path));
        
        // 尝试每个可能的选择
        for (int i = start; i < nums.length; i++) {
            // 做出选择
            path.add(nums[i]);
            
            // 递归
            backtrack(nums, i + 1, path, result);
            
            // 撤销选择
            path.remove(path.size() - 1);
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(2^n)，其中n是数组长度
- 空间复杂度：O(n)，递归栈的最大深度

## 解题技巧

### 1. 回溯框架
```java
// 标准回溯模板
backtrack(路径, 选择列表) {
    if (满足结束条件) {
        添加结果;
        return;
    }
    
    for (选择 : 选择列表) {
        做出选择;
        backtrack(新路径, 新选择列表);
        撤销选择;
    }
}
```

### 2. 路径维护
```java
// 正确处理路径
path.add(nums[i]);       // 做出选择
backtrack(...);         // 递归
path.remove(path.size()-1);  // 撤销选择
```

### 3. 结果收集
```java
// 收集所有中间状态
result.add(new ArrayList<>(path));
```

## 相关题目
- [90. 子集 II](https://leetcode.com/problems/subsets-ii/)
- [77. 组合](https://leetcode.com/problems/combinations/)
- [46. 全排列](https://leetcode.com/problems/permutations/)

## 延伸思考

### 1. 变种问题
- 包含重复元素的情况？
- 返回特定大小的子集？
- 限制子集和？

### 2. 优化方向
- 迭代实现
- 位运算方法
- 空间使用优化

### 3. 实际应用
- 数据挖掘
- 特征选择
- 排列组合
- 密码破解

## 面试技巧

### 1. 解题步骤
1. 确定递归函数定义
2. 设计base case
3. 维护路径状态
4. 收集所有中间结果

### 2. 代码实现
- 清晰的递归结构
- 正确的状态维护
- 完善的边界处理
- 合理的结果收集

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 状态维护
```java
// 错误：直接添加引用
result.add(path);  // 应该创建新的副本
```

### 2. 边界处理
```java
// 错误：索引越界
for (int i = start; i <= nums.length; i++)  // 应该是 i < nums.length
```

### 3. 回溯操作
```java
// 错误：忘记撤销选择
path.add(nums[i]);
backtrack(...);
// 缺少 path.remove(path.size()-1);
```

## 总结

### 1. 回溯要点
- 明确递归定义
- 正确维护状态
- 收集中间结果
- 处理边界情况

### 2. 优化方向
- 剪枝优化
- 迭代实现
- 位运算方法
- 空间优化

### 3. 技能提升
- 递归思维
- 状态管理
- 代码实现
- 优化意识
