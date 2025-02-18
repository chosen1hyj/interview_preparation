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
[
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

输出：
[
  ["5","3","4","6","7","8","9","1","2"],
  ["6","7","2","1","9","5","3","4","8"],
  ["1","9","8","3","4","2","5","6","7"],
  ["8","5","9","7","6","1","4","2","3"],
  ["4","2","6","8","5","3","7","9","1"],
  ["7","1","3","9","2","4","8","5","6"],
  ["9","6","1","5","3","7","2","8","4"],
  ["2","8","7","4","1","9","6","3","5"],
  ["3","4","5","2","8","6","1","7","9"]
]
```

## 解法：回溯算法

### 思路
1. 遍历每个空格尝试填入数字
2. 使用三个布尔数组记录行列和小宫格的使用情况
3. 回溯寻找可行解

```java
class Solution {
    public void solveSudoku(char[][] board) {
        boolean[][] rowUsed = new boolean[9][10];
        boolean[][] colUsed = new boolean[9][10];
        boolean[][][] boxUsed = new boolean[3][3][10];
        
        // 初始化已使用标记
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    rowUsed[i][num] = true;
                    colUsed[j][num] = true;
                    boxUsed[i/3][j/3][num] = true;
                }
            }
        }
        
        backtrack(board, rowUsed, colUsed, boxUsed, 0, 0);
    }
    
    private boolean backtrack(char[][] board, boolean[][] rowUsed, 
                               boolean[][] colUsed, boolean[][][] boxUsed,
                               int row, int col) {
        // 找到下一个空格
        while (row < 9 && board[row][col] != '.') {
            row = col == 8 ? row + 1 : row;
            col = col == 8 ? 0 : col + 1;
        }
        
        // 如果已经遍历完所有格子
        if (row == 9) return true;
        
        // 尝试填入数字
        for (int num = 1; num <= 9; num++) {
            if (!rowUsed[row][num] && !colUsed[col][num] &&
                !boxUsed[row/3][col/3][num]) {
                
                // 做选择
                board[row][col] = (char)(num + '0');
                rowUsed[row][num] = true;
                colUsed[col][num] = true;
                boxUsed[row/3][col/3][num] = true;
                
                // 进入下一层决策树
                if (backtrack(board, rowUsed, colUsed, boxUsed, row, col)) {
                    return true;
                }
                
                // 撤销选择
                board[row][col] = '.';
                rowUsed[row][num] = false;
                colUsed[col][num] = false;
                boxUsed[row/3][col/3][num] = false;
            }
        }
        
        return false;
    }
}
```

### 复杂度分析
- 时间复杂度：O(9^(n²))，其中n是数独边长
- 空间复杂度：O(n²)，辅助数组和递归栈空间

## 解题技巧

### 1. 状态标记
```java
// 三个维度的状态标记
boolean[][] rowUsed = new boolean[9][10];
boolean[][] colUsed = new boolean[9][10];
boolean[][][] boxUsed = new boolean[3][3][10];
```

### 2. 空格查找
```java
// 快速定位下一个空格
while (row < 9 && board[row][col] != '.') {
    row = col == 8 ? row + 1 : row;
    col = col == 8 ? 0 : col + 1;
}
```

### 3. 可行性判断
```java
// 综合判断三个条件
if (!rowUsed[row][num] && !colUsed[col][num] &&
    !boxUsed[row/3][col/3][num]) { ... }
```

## 相关题目
- [36. 有效的数独](https://leetcode.com/problems/valid-sudoku/)
- [51. N 皇后](https://leetcode.com/problems/n-queens/)
- [52. N 皇后 II](https://leetcode.com/problems/n-queens-ii/)

## 延伸思考

### 1. 变种问题
- 不规则数独？
- 更大尺寸数独？
- 特殊约束条件？

### 2. 优化方向
- 舞蹈链算法
- 启发式搜索
- 并行计算

### 3. 实际应用
- 游戏开发
- 约束满足问题
- 排列组合
- 人工智能

## 面试技巧

### 1. 解题步骤
1. 确定状态表示
2. 设计可行性判断
3. 实现回溯逻辑
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
for (int num = 0; num <= 9; num++) { ... }
// 应该从1开始
for (int num = 1; num <= 9; num++) { ... }
```

### 2. 状态恢复
```java
// 错误：遗漏撤销操作
board[row][col] = (char)(num + '0');
rowUsed[row][num] = true;
backtrack(...);
// 缺少撤销
board[row][col] = '.';
rowUsed[row][num] = false;
```

### 3. 小宫格计算
```java
// 错误：错误的小宫格索引
boxUsed[row][col][num] = true;
// 应该除以3取整
boxUsed[row/3][col/3][num] = true;
```

## 总结

### 1. 回溯要点
- 明确状态表示
- 正确维护状态
- 完整的撤销操作
- 处理边界情况

### 2. 优化方向
- 舞蹈链算法
- 启发式搜索
- BFS替代方案
- 空间优化

### 3. 技能提升
- 回溯思维
- 状态管理意识
- 优化能力
- 代码实现
