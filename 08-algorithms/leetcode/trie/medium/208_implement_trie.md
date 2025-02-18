# 实现 Trie (前缀树) (中等)

## 题目描述
实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。

## 示例
Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // 返回 true
trie.search("app");     // 返回 false
trie.startsWith("app"); // 返回 true
trie.insert("app");   
trie.search("app");     // 返回 true

## 解法
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

## 复杂度分析
- 时间复杂度: O(L) 每个操作，其中 L 是单词长度
- 空间复杂度: O(N*M)，其中 N 是插入的单词数量，M 是平均长度

## 关键点
- 前缀树结构
- 插入操作
- 查找优化
- 空间效率
