# 212. 单词搜索 II（Word Search II）

[题目链接](https://leetcode.com/problems/word-search-ii/)

## 题目描述
给定一个 m x n 的字符板和一个单词列表 words，找出所有同时在字符板上和单词列表中出现的单词。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中"相邻"单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

示例：
```
输入：
board = [
  ["o","a","a","n"],
  ["e","t","a","e"],
  ["i","h","k","r"],
  ["i","f","l","v"]
]
words = ["oath","pea","eat","rain"]

输出：["eat","oath"]
```

## 解法：Trie + DFS

### 思路
1. 构建Trie存储单词列表
2. 对board中的每个位置进行DFS搜索
3. 使用Trie进行前缀剪枝
4. 标记已访问位置避免重复使用

```java
class Solution {
    // Trie节点定义
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null;
    }
    
    // 构建Trie树
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.word = word; // 存储完整单词
        }
        return root;
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);
        int m = board.length, n = board[0].length;
        
        // 从每个位置开始DFS
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root, result);
            }
        }
        
        return result;
    }
    
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        // 边界检查
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            board[i][j] == '#') {
            return;
        }
        
        char c = board[i][j];
        int index = c - 'a';
        if (node.children[index] == null) return;
        
        node = node.children[index];
        if (node.word != null) {  // 找到一个单词
            result.add(node.word);
            node.word = null;     // 防止重复添加
        }
        
        // 标记当前位置为已访问
        board[i][j] = '#';
        
        // 搜索四个方向
        dfs(board, i + 1, j, node, result);
        dfs(board, i - 1, j, node, result);
        dfs(board, i, j + 1, node, result);
        dfs(board, i, j - 1, node, result);
        
        // 恢复当前位置
        board[i][j] = c;
    }
}
```

### 复杂度分析
- 时间复杂度：O(M * N * 4^L)，其中M和N是板的维度，L是单词的最大长度
- 空间复杂度：O(K)，其中K是所有单词的字符总数（Trie的空间）

## 优化版本

### 思路
1. 添加剪枝条件
2. 优化Trie结构
3. 减少不必要的搜索

```java
class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null;
        int count = 0;  // 记录前缀出现次数
    }
    
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
                node.count++;  // 增加前缀计数
            }
            node.word = word;
        }
        return root;
    }
    
    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        if (board == null || board.length == 0) return result;
        
        // 构建Trie
        TrieNode root = buildTrie(words);
        int m = board.length, n = board[0].length;
        
        // 从每个位置开始搜索
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root, result);
            }
        }
        
        return result;
    }
    
    private final int[][] DIRECTIONS = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    
    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
            board[i][j] == '#' || node.children[board[i][j] - 'a'] == null || 
            node.children[board[i][j] - 'a'].count == 0) {
            return;
        }
        
        char c = board[i][j];
        TrieNode next = node.children[c - 'a'];
        
        // 找到单词
        if (next.word != null) {
            result.add(next.word);
            next.word = null;  // 防止重复
            // 减少所有前缀计数
            updateCount(root, next.word, -1);
        }
        
        board[i][j] = '#';  // 标记访问
        
        // 搜索四个方向
        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            dfs(board, newI, newJ, next, result);
        }
        
        board[i][j] = c;  // 恢复
    }
    
    private void updateCount(TrieNode root, String word, int delta) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            node.count += delta;
        }
    }
}
```

## 题目重点
1. Trie的构建和使用
2. DFS的实现
3. 剪枝策略
4. 空间优化

## 解题技巧

### 1. Trie树优化
```java
// 使用前缀计数进行剪枝
class TrieNode {
    int count;  // 记录前缀出现次数
    
    void decreaseCount() {
        count--;
    }
}
```

### 2. 方向数组
```java
private final int[][] DIRECTIONS = {{1,0}, {-1,0}, {0,1}, {0,-1}};

// 使用方向数组简化代码
for (int[] dir : DIRECTIONS) {
    int newI = i + dir[0];
    int newJ = j + dir[1];
    dfs(board, newI, newJ, next, result);
}
```

### 3. 边界检查
```java
if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || 
    board[i][j] == '#' || node.count == 0) {
    return;
}
```

## 优化方向

### 1. 空间优化
- 使用字符数组替代String
- 复用已有空间
- 及时清理无用节点

### 2. 时间优化
- 增加剪枝条件
- 优化Trie结构
- 减少字符串操作

### 3. 代码优化
- 使用常量数组
- 提取公共方法
- 简化逻辑结构

## 相关题目
- [79. 单词搜索](https://leetcode.com/problems/word-search/)
- [208. 实现 Trie (前缀树)](https://leetcode.com/problems/implement-trie-prefix-tree/)
- [211. 添加与搜索单词](https://leetcode.com/problems/design-add-and-search-words-data-structure/)

## 面试技巧

### 1. 解题步骤
1. 分析问题特点
2. 确定数据结构
3. 实现基本功能
4. 考虑优化方案

### 2. 要点解释
- Trie树的作用
- DFS的实现方式
- 剪枝的必要性
- 空间时间权衡

### 3. 性能分析
- 时间复杂度分析
- 空间使用评估
- 优化可能性

## 实际应用

### 1. 实际场景
- 输入法联想
- 搜索引擎提示
- 文本检索系统

### 2. 扩展功能
- 模糊匹配
- 大规模数据处理
- 动态更新支持

### 3. 工程实践
- 代码可维护性
- 异常处理
- 性能监控
