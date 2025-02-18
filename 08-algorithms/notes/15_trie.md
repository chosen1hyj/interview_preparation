# 前缀树基础知识

## 基本概念

### 1. 数据结构定义
```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

class Trie {
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    // 插入单词
    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }
    
    // 查找单词
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEndOfWord;
    }
    
    // 判断前缀是否存在
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }
    
    // 辅助方法：查找节点
    private TrieNode searchNode(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current = current.children.get(c);
            if (current == null) return null;
        }
        return current;
    }
}
```

## 实战应用

### 1. 单词搜索
```java
// 使用前缀树优化单词搜索
List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String word : words) {
        trie.insert(word);
    }
    
    Set<String> res = new HashSet<>();
    int m = board.length;
    int n = board[0].length;
    
    boolean[][] visited = new boolean[m][n];
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            dfs(board, i, j, "", trie.root, res, visited);
        }
    }
    
    return new ArrayList<>(res);
}

void dfs(char[][] board, int i, int j, String path,
         TrieNode node, Set<String> res, boolean[][] visited) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
    if (visited[i][j]) return;
    
    char c = board[i][j];
    node = node.children.get(c);
    if (node == null) return;
    
    path += c;
    if (node.isEndOfWord) {
        res.add(path);
    }
    
    visited[i][j] = true;
    
    // 四个方向搜索
    dfs(board, i+1, j, path, node, res, visited);
    dfs(board, i-1, j, path, node, res, visited);
    dfs(board, i, j+1, path, node, res, visited);
    dfs(board, i, j-1, path, node, res, visited);
    
    visited[i][j] = false;
}
```

### 2. 最长公共前缀
```java
// 查找字符串数组的最长公共前缀
String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    
    Trie trie = new Trie();
    for (String s : strs) {
        trie.insert(s);
    }
    
    StringBuilder sb = new StringBuilder();
    TrieNode node = trie.root;
    
    while (node != null && node.children.size() == 1 && !node.isEndOfWord) {
        Character c = node.children.keySet().iterator().next();
        sb.append(c);
        node = node.children.get(c);
    }
    
    return sb.toString();
}
```

## 性能优化

### 1. 空间优化
- 使用数组代替HashMap
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEndOfWord = false;
}
```

### 2. 查询优化
- 缓存中间结果
- 提前终止无效查询

## 注意事项

### 1. 边界处理
- 空字符串处理
- 特殊字符判断
- 大小写敏感性

### 2. 性能考量
- 时间复杂度：插入和查找均为O(L)，L为单词长度
- 空间复杂度：O(N*M)，N为单词数量，M为平均长度

### 3. 常见陷阱
- 忘记标记单词结束
- 字符映射错误
- 内存泄漏风险
