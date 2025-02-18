# 200. 岛屿数量（Number of Islands）

[题目链接](https://leetcode.com/problems/number-of-islands/)

## 题目描述
给你一个由 '1'（陆地）和 '0'（水）组成的二维网格，请你计算网格中岛屿的数量。

岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

示例：
```
输入：grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
输出：1

输入：grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
输出：3
```

## 解法：深度优先搜索

### 思路
1. 遍历整个网格
2. 遇到陆地时启动DFS，标记所有相连陆地
3. 统计启动DFS的次数即为岛屿数量

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }
    
    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || 
            j < 0 || j >= grid[0].length || 
            grid[i][j] != '1') return;
        
        // 标记已访问
        grid[i][j] = '0';
        
        // 搜索四个方向
        dfs(grid, i+1, j);
        dfs(grid, i-1, j);
        dfs(grid, i, j+1);
        dfs(grid, i, j-1);
    }
}
```

### 复杂度分析
- 时间复杂度：O(m×n)，其中m是行数，n是列数
- 空间复杂度：O(m×n)，最坏情况下的递归深度

## 解题技巧

### 1. 边界检查
```java
// 完整的边界条件判断
if (i < 0 || i >= grid.length || 
    j < 0 || j >= grid[0].length || 
    grid[i][j] != '1') return;
```

### 2. 标记访问
```java
// 直接修改原数组标记已访问
grid[i][j] = '0';
```

### 3. 方向遍历
```java
// 四个方向的搜索
dfs(grid, i+1, j); // 下
dfs(grid, i-1, j); // 上
dfs(grid, i, j+1); // 右
dfs(grid, i, j-1); // 左
```

## 相关题目
- [695. 岛屿的最大面积](https://leetcode.com/problems/max-area-of-island/)
- [463. 岛屿的周长](https://leetcode.com/problems/island-perimeter/)
- [733. 图像渲染](https://leetcode.com/problems/flood-fill/)

## 延伸思考

### 1. 变种问题
- 对角线方向也视为相邻？
- 计算岛屿的总面积？
- 返回每个岛屿的坐标？

### 2. 优化方向
- 迭代实现
- 并查集方法
- 广度优先搜索

### 3. 实际应用
- 地理信息系统
- 区域划分
- 资源分配
- 图像处理

## 面试技巧

### 1. 解题步骤
1. 确定搜索范围
2. 设计访问标记
3. 实现DFS逻辑
4. 统计岛屿数量

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
// 错误：不完整的边界检查
if (grid[i][j] != '1') return;
// 缺少行列越界检查
```

### 2. 标记访问
```java
// 错误：忘记标记已访问
dfs(grid, i+1, j);
// 应该先标记当前节点
grid[i][j] = '0';
```

### 3. 方向遍历
```java
// 错误：遗漏某些方向
dfs(grid, i+1, j);
dfs(grid, i-1, j);
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
