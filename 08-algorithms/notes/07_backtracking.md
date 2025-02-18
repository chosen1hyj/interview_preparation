# 回溯算法（Backtracking）

## 基本概念
回溯算法是一种通过递归方式系统地搜索问题解的算法。它采用试探性方法，在每一步尝试所有可能的选择，当发现当前选择不满足条件时，就回退到上一步重新选择。

## 核心思想
1. 选择：从多个选项中做出一个选择
2. 约束：检查当前选择是否满足约束条件
3. 目标：判断是否达到目标状态
4. 回溯：撤销选择，返回上一步

## 典型应用场景
- 排列组合问题
- 子集生成
- 数独求解
- N皇后问题
- 图着色问题
- 组合优化问题

## 实现框架
```java
public void backtrack(路径, 选择列表) {
    if (满足结束条件) {
        添加结果;
        return;
    }
    
    for (选择 : 选择列表) {
        做出选择;
        backtrack(路径, 新的选择列表);
        撤销选择;
    }
}
```

## 优化技巧
1. 剪枝：提前排除不可能的分支
2. 缓存：存储中间结果避免重复计算
3. 排序：先处理更有可能成功的分支
4. 状态压缩：使用位运算等技术优化空间使用

## 注意事项
1. 确定终止条件
2. 正确维护状态
3. 避免重复计算
4. 处理边界情况

## 时间复杂度分析
通常为指数级复杂度，具体取决于：
- 解空间大小
- 约束条件数量
- 剪枝效果

## 空间复杂度分析
主要由递归深度决定，通常为O(n)，其中n是问题规模

## 实际应用
1. 游戏AI决策
2. 路径规划
3. 资源分配
4. 排班调度
5. 数据分析

## 面试技巧
1. 明确递归函数定义
2. 确定base case
3. 合理设计状态表示
4. 优化剪枝条件
5. 考虑迭代实现

## 常见错误
1. 忘记撤销选择
2. 错误的状态维护
3. 不完整的约束检查
4. 边界条件处理不当
### 典型例题

#### LeetCode 78 - 子集 (简单)
- 组合生成
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, int start, List<Integer> path, 
                       List<List<Integer>> result) {
    result.add(new ArrayList<>(path));
    
    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        backtrack(nums, i + 1, path, result);
        path.remove(path.size() - 1);
    }
}
```

#### LeetCode 39 - 组合总和 (中等)
- 数字组合
```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
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
        if (candidates[i] > remain) break;
        
        path.add(candidates[i]);
        backtrack(candidates, remain - candidates[i], i, path, result);
        path.remove(path.size() - 1);
    }
}
```

#### LeetCode 37 - 解数独 (困难)
- 约束满足
```java
public void solveSudoku(char[][] board) {
    solve(board);
}

private boolean solve(char[][] board) {
    for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
            if (board[i][j] != '.') continue;
            
            for (char c = '1'; c <= '9'; c++) {
                if (isValid(board, i, j, c)) {
                    board[i][j] = c;
                    
                    if (solve(board)) return true;
                    
                    board[i][j] = '.';
                }
            }
            return false;
        }
    }
    return true;
}

private boolean isValid(char[][] board, int row, int col, char c) {
    for (int i = 0; i < 9; i++) {
        // 检查行
        if (board[i][col] == c) return false;
        // 检查列
        if (board[row][i] == c) return false;
        // 检查3x3子方块
        int boxRow = 3 * (row / 3) + i / 3;
        int boxCol = 3 * (col / 3) + i % 3;
        if (board[boxRow][boxCol] == c) return false;
    }
    return true;
}
```

## 总结
