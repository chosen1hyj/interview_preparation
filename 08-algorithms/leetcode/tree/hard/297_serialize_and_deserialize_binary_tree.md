# 297. 二叉树的序列化与反序列化（Serialize and Deserialize Binary Tree）

[题目链接](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)

## 题目描述
序列化是将一个数据结构或者对象转换为一个字符串的过程，同时也可以将字符串反序列化为原先的数据结构或者对象。

设计一个算法来实现二叉树的序列化与反序列化。不限定你的序列/反序列化算法执行逻辑，只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。

示例：
```
你可以将以下二叉树：
    1
   / \
  2   3
     / \
    4   5

序列化为 "[1,2,3,null,null,4,5]"
```

## 解法：层序遍历（BFS）

### 思路
1. 序列化：使用层序遍历，将节点值按层序转换为字符串
2. 反序列化：根据字符串重建二叉树，使用队列辅助重建过程

```java
public class Codec {
    // 序列化
    public String serialize(TreeNode root) {
        if (root == null) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        // 删除最后一个逗号，添加结束括号
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
    
    // 反序列化
    public TreeNode deserialize(String data) {
        if (data.equals("[]")) return null;
        
        // 解析字符串
        String[] values = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode parent = queue.poll();
            
            // 处理左子节点
            if (!values[i].equals("null")) {
                parent.left = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(parent.left);
            }
            i++;
            
            // 处理右子节点
            if (i < values.length && !values[i].equals("null")) {
                parent.right = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(parent.right);
            }
            i++;
        }
        
        return root;
    }
}
```

### 复杂度分析
- 序列化：
  - 时间复杂度：O(n)
  - 空间复杂度：O(n)
- 反序列化：
  - 时间复杂度：O(n)
  - 空间复杂度：O(n)

## 解法二：前序遍历（DFS）

### 思路
使用前序遍历进行序列化和反序列化，用特殊字符标记空节点。

```java
public class Codec {
    private static final String NULL = "#";
    private static final String SEPARATOR = ",";
    
    // 序列化
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NULL).append(SEPARATOR);
            return;
        }
        
        sb.append(node.val).append(SEPARATOR);
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    // 反序列化
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(SEPARATOR)));
        return deserializeHelper(queue);
    }
    
    private TreeNode deserializeHelper(Queue<String> queue) {
        String value = queue.poll();
        if (value == null || value.equals(NULL)) {
            return null;
        }
        
        TreeNode node = new TreeNode(Integer.parseInt(value));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        
        return node;
    }
}
```

### 复杂度分析
- 序列化：
  - 时间复杂度：O(n)
  - 空间复杂度：O(h)，h为树的高度
- 反序列化：
  - 时间复杂度：O(n)
  - 空间复杂度：O(h)

## 题目重点
1. 理解序列化和反序列化的概念
2. 选择合适的遍历方式
3. 处理空节点的标记
4. 字符串格式的设计

## 解题技巧

### 1. 序列化格式设计
```java
// 使用明确的分隔符
private static final String SEPARATOR = ",";

// 使用特殊标记表示空节点
private static final String NULL = "#";

// 使用括号表示树的开始和结束
String result = "[" + serializedContent + "]";
```

### 2. 字符串处理
```java
// 分割字符串
String[] values = data.substring(1, data.length() - 1).split(",");

// 数字转换
int val = Integer.parseInt(value);

// 空值检查
if (value.equals("null") || value.equals("#"))
```

### 3. 队列使用
```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
TreeNode node = queue.poll();
```

## 常见错误

### 1. 格式处理错误
```java
// 错误：未处理边界字符
String[] values = data.split(",");

// 正确：去除边界字符后分割
String[] values = data.substring(1, data.length() - 1).split(",");
```

### 2. 空节点处理
```java
// 错误：忽略空节点
if (node != null) {
    queue.offer(node.left);
    queue.offer(node.right);
}

// 正确：空节点也需要入队
queue.offer(node.left);
queue.offer(node.right);
```

### 3. 整数转换异常
```java
// 错误：直接转换
int val = Integer.parseInt(value);

// 正确：先检查是否为空节点标记
if (!value.equals("null")) {
    int val = Integer.parseInt(value);
}
```

## 相关题目
- [449. 序列化和反序列化二叉搜索树](https://leetcode.com/problems/serialize-and-deserialize-bst/)
- [428. 序列化和反序列化N叉树](https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/)
- [331. 验证二叉树的前序序列化](https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/)

## 补充知识

### 1. 序列化的其他方案
- 前序+中序遍历序列
- 后序+中序遍历序列
- 括号表示法

### 2. 不同遍历方式的优缺点
1. **层序遍历（BFS）**
   - 优点：直观、易于理解
   - 缺点：可能包含多余的null值

2. **前序遍历（DFS）**
   - 优点：序列较短、实现简单
   - 缺点：需要特殊字符标记

3. **其他遍历组合**
   - 优点：可以唯一确定树结构
   - 缺点：需要多个遍历序列

## 面试技巧

### 1. 讨论思路
- 解释选择特定遍历方式的原因
- 分析不同方案的优缺点
- 考虑空间和时间的平衡

### 2. 代码优化
- 使用StringBuilder优化字符串操作
- 选择合适的数据结构
- 处理异常情况

### 3. 扩展思考
- 如何处理特殊字符
- 如何优化存储空间
- 如何处理大规模数据
