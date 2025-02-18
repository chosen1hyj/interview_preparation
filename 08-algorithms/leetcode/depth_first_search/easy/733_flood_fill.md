# 733. 图像渲染（Flood Fill）

[题目链接](https://leetcode.com/problems/flood-fill/)

## 题目描述
有一幅以二维整数数组表示的图画，每一个整数表示该位置处的像素值。数字代表颜色。

给你一个坐标 (sr, sc) 表示图像渲染的起始像素（行和列），以及一个新的颜色值 newColor，让你重新上色这幅图像。

为了完成上色工作，从初始坐标开始，将与初始像素相连且颜色相同的像素都涂成新的颜色。

两个像素相连的定义为：上下左右四个方向相邻。

示例：
```
输入: 
image = [[1,1,1],[1,1,0],[1,0,1]]
sr = 1, sc = 1, newColor = 2
输出: [[2,2,2],[2,2,0],[2,0,1]]
解释: 
在图像的正中间，(坐标(sr,sc)=(1,1)),
所有像素原本都是颜色 1。
在 (1,1) 处的颜色被替换为 2。
右侧的像素没有被替换，因为它们不相邻也不相同。
```

## 解法：深度优先搜索

### 思路
1. 从起始点开始DFS
2. 向四个方向扩展
3. 检查边界条件和颜色匹配

```java
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int originalColor = image[sr][sc];
        if (originalColor != newColor) {
            dfs(image, sr, sc, originalColor, newColor);
        }
        return image;
    }
    
    private void dfs(int[][] image, int r, int c, int originalColor, int newColor) {
        // 边界检查
        if (r < 0 || r >= image.length || 
            c < 0 || c >= image[0].length || 
            image[r][c] != originalColor) return;
        
        // 上色
        image[r][c] = newColor;
        
        // 四个方向搜索
        dfs(image, r+1, c, originalColor, newColor); // 下
        dfs(image, r-1, c, originalColor, newColor); // 上
        dfs(image, r, c+1, originalColor, newColor); // 右
        dfs(image, r, c-1, originalColor, newColor); // 左
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
if (r < 0 || r >= image.length || 
    c < 0 || c >= image[0].length || 
    image[r][c] != originalColor) return;
```

### 2. 颜色处理
```java
// 特殊情况处理
if (originalColor != newColor) {
    dfs(image, sr, sc, originalColor, newColor);
}
```

### 3. 方向遍历
```java
// 四个方向的搜索
dfs(image, r+1, c, originalColor, newColor); // 下
dfs(image, r-1, c, originalColor, newColor); // 上
dfs(image, r, c+1, originalColor, newColor); // 右
dfs(image, r, c-1, originalColor, newColor); // 左
```

## 相关题目
- [200. 岛屿数量](https://leetcode.com/problems/number-of-islands/)
- [695. 岛屿的最大面积](https://leetcode.com/problems/max-area-of-island/)
- [463. 岛屿的周长](https://leetcode.com/problems/island-perimeter/)

## 延伸思考

### 1. 变种问题
- 对角线方向也视为相邻？
- 返回影响的像素数量？
- 支持多种颜色？

### 2. 优化方向
- 迭代实现
- 并查集方法
- 广度优先搜索

### 3. 实际应用
- 图像处理
- 区域填充
- 游戏开发
- 地图渲染

## 面试技巧

### 1. 解题步骤
1. 确定搜索范围
2. 设计访问标记
3. 实现DFS逻辑
4. 统计影响区域

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
if (image[r][c] != originalColor) return;
// 缺少行列越界检查
```

### 2. 颜色处理
```java
// 错误：忽略原色等于新色的情况
dfs(image, sr, sc, originalColor, newColor);
// 应该先判断颜色是否相同
if (originalColor != newColor) { ... }
```

### 3. 方向遍历
```java
// 错误：遗漏某些方向
dfs(image, r+1, c, originalColor, newColor);
dfs(image, r-1, c, originalColor, newColor);
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
