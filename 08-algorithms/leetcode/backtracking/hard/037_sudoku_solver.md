# 37. 解数独（Sudoku Solver）

[题目链接](https://leetcode.com/problems/sudoku-solver/)

## 题目描述
编写一个程序，通过填充空格来解决数独问题。

一个数独的解法需遵循如下规则：
1. 数字 1-9 在每一行只能出现一次。
2. 数字 1-9 在每一列只能出现一次。
3. 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。

空白格用 '.' 表示。

示例：
```
输入：
board = [
    ["5","3",".",".","7",".",".",".","."],
    ["6",".",".","1","9","5",".",".","."],
    [".","9","8",".",".",".",".","6","."],
    ["8",".",".",".","6",".",".",".","3"],
    ["4",".",".","8",".","3",".",".","1"],
    ["7",".",".",".","2",".",".",".","6"],
    [".","6",".",".",".",".","2","8","."],
    [".",".",".","4","1","9",".",".","5"],
    [".",".",".",".","8",".",".","7","9"]
]

输出：修改输入的 board 使其成为有效数独解
```

## 解法：回溯算法

### 思路
1. 使用回溯算法尝试所有可能数字
2. 维护行、列和块的状态
3. 剪枝优化搜索空间

```java
class Solution {
    public void solveSudoku(char[][] board) {
        boolean[][] rows = new boolean[9][9];
        boolean[][] cols = new boolean[9][9];
        boolean[][][] blocks = new boolean[3][3][9];
        
        // 初始化状态
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    rows[i][num] = true;
                    cols[j][num] = true;
                    blocks[i/3][j/3][num] = true;
                }
            }
        }
        
        backtrack(board, rows, cols, blocks, 0, 0);
    }
    
    private boolean backtrack(char[][] board, boolean[][] rows,
                               boolean[][] cols, boolean[][][] blocks,
                               int row, int col) {
        // 找到下一个空位置
        while (row < 9 && board[row][col] != '.') {
            col++;
            if (col == 9) {
                col = 0;
                row++;
            }
        }
        
        if (row == 9) return true; // 找到解
        
        for (int num = 0; num < 9; num++) {
            if (!rows[row][num] && !cols[col][num] && 
                !blocks[row/3][col/3][num]) {
                
                // 做出选择
                board[row][col] = (char)(num + '1');
                rows[row][num] = true;
                cols[col][num] = true;
                blocks[row/3][col/3][num] = true;
                
                if (backtrack(board, rows, cols, blocks, row, col)) {
                    return true;
                }
                
                // 撤销选择
                board[row][col] = '.';
                rows[row][num] = false;
                cols[col][num] = false;
                blocks[row/3][col/3][num] = false;
            }
        }
        
        return false;
    }
}
```

### 复杂度分析
- 时间复杂度：O(9^(n*n))，最坏情况下的递归深度
- 空间复杂度：O(n^2)，状态数组的空间使用

## 解题技巧

### 1. 状态维护
```java
// 使用布尔数组维护状态
boolean[][] rows = new boolean[9][9];
boolean[][] cols = new boolean[9][9];
boolean[][][] blocks = new boolean[3][3][9];
```

### 2. 位置遍历
```java
// 寻找下一个空位置
while (row < 9 && board[row][col] != '.') {
    col++;
    if (col == 9) {
        col = 0;
        row++;
    }
}
```

### 3. 提前返回
```java
// 找到解立即返回
if (backtrack(...)) {
    return true;
}
```

## 相关题目
- [36. 有效的数独](https://leetcode.com/problems/valid-sudoku/)
- [51. N 皇后](https://leetcode.com/problems/n-queens/)
- [52. N 皇后 II](https://leetcode.com/problems/n-queens-ii/)

## 延伸思考

### 1. 变种问题
- 不规则形状的数独？
- 更大尺寸的数独？
- 添加额外约束条件？

### 2. 优化方向
- 启发式搜索
- 舞蹈链算法
- 并行计算

### 3. 实际应用
- 游戏AI
- 排班系统
- 资源分配
- 图着色问题

## 面试技巧

### 1. 解题步骤
1. 确定递归函数定义
2. 设计状态表示
3. 处理约束条件
4. 实现提前返回

### 2. 代码实现
- 合理的状态维护
- 有效的剪枝优化
- 正确的位置遍历
- 完善的边界处理

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 状态维护
```java
// 错误：忘记更新状态
board[row][col] = (char)(num + '1');
// 缺少更新其他状态数组
```

### 2. 位置遍历
```java
// 错误：没有正确处理换行
if (col == 9) {
    col = 0;
    row++;  // 忘记增加行号
}
```

### 3. 回溯操作
```java
// 错误：忘记撤销选择
board[row][col] = '.';
// 缺少恢复其他状态
```

## 总结

### 1. 回溯要点
- 明确递归定义
- 维护多种状态
- 处理约束条件
- 实现提前返回

### 2. 优化方向
- 启发式搜索
- 舞蹈链算法
- 并行计算
- 状态压缩

### 3. 技能提升
- 复杂状态管理
- 约束处理能力
- 优化意识
- 代码实现
