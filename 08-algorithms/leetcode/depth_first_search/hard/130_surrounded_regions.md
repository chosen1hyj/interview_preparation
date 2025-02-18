# 130. 被围绕的区域（Surrounded Regions）

[题目链接](https://leetcode.com/problems/surrounded-regions/)

## 题目描述
给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。

找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。

示例：
```
输入：board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
输出：[["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
解释：
被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 
任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
```

## 解法：逆向思维DFS

### 思路
1. 从边界开始DFS标记所有与边界相连的'O'
2. 将剩余的'O'替换为'X'
3. 恢复边界相连的标记

```java
class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        
        int rows = board.length;
        int cols = board[0].length;
        
        // 第一步：标记边界相连的'O'
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') dfs(board, i, 0);
            if (board[i][cols-1] == 'O') dfs(board, i, cols-1);
        }
        
        for (int j = 0; j < cols; j++) {
            if (board[0][j] == 'O') dfs(board, 0, j);
            if (board[rows-1][j] == 'O') dfs(board, rows-1, j);
        }
        
        // 第二步：替换和恢复
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }
    
    private void dfs(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || 
            j < 0 || j >= board[0].length || 
            board[i][j] != 'O') return;
        
        // 标记与边界相连的'O'
        board[i][j] = '#';
        
        // 四个方向搜索
        dfs(board, i+1, j);
        dfs(board, i-1, j);
        dfs(board, i, j+1);
        dfs(board, i, j-1);
    }
}
```

### 复杂度分析
- 时间复杂度：O(m×n)，其中m是行数，n是列数
- 空间复杂度：O(m×n)，最坏情况下的递归深度

## 解题技巧

### 1. 逆向思维
```java
// 标记边界相连的'O'
if (board[i][j] == 'O') board[i][j] = '#';
// 最后恢复
if (board[i][j] == '#') board[i][j] = 'O';
```

### 2. 边界处理
```java
// 处理四条边界
for (int i = 0; i < rows; i++) {
    if (board[i][0] == 'O') dfs(board, i, 0);
    if (board[i][cols-1] == 'O') dfs(board, i, cols-1);
}

for (int j = 0; j < cols; j++) {
    if (board[0][j] == 'O') dfs(board, 0, j);
    if (board[rows-1][j] == 'O') dfs(board, rows-1, j);
}
```

### 3. 方向遍历
```java
// 四个方向的搜索
dfs(board, i+1, j); // 下
dfs(board, i-1, j); // 上
dfs(board, i, j+1); // 右
dfs(board, i, j-1); // 左
```

## 相关题目
- [200. 岛屿数量](https://leetcode.com/problems/number-of-islands/)
- [695. 岛屿的最大面积](https://leetcode.com/problems/max-area-of-island/)
- [463. 岛屿的周长](https://leetcode.com/problems/island-perimeter/)

## 延伸思考

### 1. 变种问题
- 返回被修改的单元格数量？
- 支持多种字符？
- 统计独立区域数量？

### 2. 优化方向
- 迭代实现
- 并查集方法
- 广度优先搜索

### 3. 实际应用
- 图像处理
- 区域划分
- 游戏开发
- 地图渲染

## 面试技巧

### 1. 解题步骤
1. 确定搜索范围
2. 设计访问标记
3. 实现DFS逻辑
4. 完成状态转换

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
// 错误：忽略边界处理
for (int i = 1; i < rows-1; i++) { ... }
// 应该处理四条边界
```

### 2. 状态标记
```java
// 错误：直接修改原值
if (board[i][j] == 'O') board[i][j] = 'X';
// 应该先标记特殊值
if (board[i][j] == 'O') board[i][j] = '#';
```

### 3. 方向遍历
```java
// 错误：遗漏某些方向
dfs(board, i+1, j);
dfs(board, i-1, j);
// 缺少左右方向
```

## 总结

### 1. DFS要点
- 明确搜索范围
- 正确标记状态
- 完整的方向遍历
- 处理边界情况

### 2. 优化方向
- 迭代实现
- 并查集方法
- BFS替代方案
- 空间优化

### 3. 技能提升
- 图搜索能力
- 状态管理意识
- 优化思维
- 代码实现
