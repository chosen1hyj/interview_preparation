# 实现 Trie (前缀树) (中等)

## 题目描述
Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。

请你实现 Trie 类：
- Trie() 初始化前缀树对象。
- void insert(String word) 向前缀树中插入字符串 word。
- boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false。
- boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix，返回 true；否则，返回 false。

## 示例
```
输入
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
输出
[null, null, true, false, true, null, true]
```

## 解法
```java
class Trie {
    private class TrieNode {
        private TrieNode[] links;
        private boolean isEnd;
        
        public TrieNode() {
            links = new TrieNode[26];
        }
        
        public boolean containsKey(char ch) {
            return links[ch - 'a'] != null;
        }
        
        public TrieNode get(char ch) {
            return links[ch - 'a'];
        }
        
        public void put(char ch, TrieNode node) {
            links[ch - 'a'] = node;
        }
        
        public void setEnd() {
            isEnd = true;
        }
        
        public boolean isEnd() {
            return isEnd;
        }
    }
    
    private TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                node.put(ch, new TrieNode());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }
    
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }
    
    private TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (node.containsKey(ch)) {
                node = node.get(ch);
            } else {
                return null;
            }
        }
        return node;
    }
}
```

## 复杂度分析
- 时间复杂度: 
  - insert: O(m)，其中m是单词长度
  - search: O(m)
  - startsWith: O(m)
- 空间复杂度: O(N*M)，其中N是单词数量，M是单词的平均长度

## 关键点
1. Trie树的节点设计
2. 字符串的插入和查找方法
3. 前缀匹配的实现
4. 空间和时间的权衡

## 应用场景
1. 自动补全
2. 拼写检查
3. IP路由表查找
4. 字符串检索
