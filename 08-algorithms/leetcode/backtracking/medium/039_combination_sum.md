# 39. 组合总和（Combination Sum）

[题目链接](https://leetcode.com/problems/combination-sum/)

## 题目描述
给定一个无重复元素的数组 candidates 和一个目标数 target，找出 candidates 中所有可以使数字和为 target 的组合。

candidates 中的数字可以无限制重复选取。

示例：
```
输入：candidates = [2,3,6,7], target = 7
输出：[[2,2,3],[7]]

输入：candidates = [2,3,5], target = 8
输出：[[2,2,2,2],[2,3,3],[3,5]]
```

## 解法：回溯算法

### 思路
1. 使用回溯算法寻找所有可能组合
2. 允许重复使用元素
3. 剪枝优化搜索空间

```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates); // 排序优化剪枝
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int remain, int start,
                           List<Integer> path, List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remain) break; // 剪枝
            
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i, path, result);
            path.remove(path.size() - 1);
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(2^n)，最坏情况下的递归深度
- 空间复杂度：O(target/min)，递归栈的最大深度

## 解题技巧

### 1. 剪枝优化
```java
// 排序后提前终止
if (candidates[i] > remain) break;
```

### 2. 元素复用
```java
// 允许重复使用当前元素
backtrack(..., i, ...);
```

### 3. 提前返回
```java
// 找到解立即返回
if (remain == 0) {
    result.add(new ArrayList<>(path));
    return;
}
```

## 相关题目
- [40. 组合总和 II](https://leetcode.com/problems/combination-sum-ii/)
- [216. 组合总和 III](https://leetcode.com/problems/combination-sum-iii/)
- [377. 组合总和 IV](https://leetcode.com/problems/combination-sum-iv/)

## 延伸思考

### 1. 变种问题
- 每个元素只能使用一次？
- 返回组合数量而非具体组合？
- 添加额外约束条件？

### 2. 优化方向
- 更有效的剪枝策略
- 迭代实现方式
- 动态规划解法

### 3. 实际应用
- 货币找零
- 资源分配
- 投资组合
- 装箱问题

## 面试技巧

### 1. 解题步骤
1. 确定递归函数定义
2. 设计剪枝条件
3. 处理元素复用
4. 收集有效解

### 2. 代码实现
- 合理的排序预处理
- 有效的剪枝优化
- 正确的状态维护
- 完善的边界处理

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 剪枝条件
```java
// 错误：没有正确剪枝
if (remain < 0) return;  // 应该在循环中提前break
```

### 2. 元素复用
```java
// 错误：不允许重复使用元素
backtrack(..., i + 1, ...);  // 应该是i而不是i+1
```

### 3. 结果收集
```java
// 错误：直接添加引用
result.add(path);  // 应该创建新的副本
```

## 总结

### 1. 回溯要点
- 明确递归定义
- 设计剪枝条件
- 处理元素复用
- 收集有效解

### 2. 优化方向
- 排序预处理
- 剪枝优化
- 状态压缩
- 空间优化

### 3. 技能提升
- 递归思维
- 剪枝意识
- 优化能力
- 代码实现
