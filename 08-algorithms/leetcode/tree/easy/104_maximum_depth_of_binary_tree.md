# 104. 二叉树的最大深度（Maximum Depth of Binary Tree）

[题目链接](https://leetcode.com/problems/maximum-depth-of-binary-tree/)

## 题目描述
给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

示例：
```
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7

返回它的最大深度 3。
```

## 解法一：递归（DFS）

### 思路
利用递归，分别计算左右子树的深度，取较大值加1即为当前树的深度。

```java
class Solution {
    public int maxDepth(TreeNode root) {
        // 基本情况：空节点
        if (root == null) {
            return 0;
        }
        
        // 递归计算左右子树的深度
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        // 返回较大深度 + 1（算上当前节点）
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，其中n是节点数量
- 空间复杂度：O(h)，其中h是树的高度（递归调用栈的空间）
  - 最好情况（平衡树）：O(log n)
  - 最坏情况（线性树）：O(n)

## 解法二：迭代（BFS）

### 思路
使用层序遍历，记录遍历的层数即为树的深度。

```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            depth++; // 每处理一层，深度加1
            int levelSize = queue.size();
            
            // 处理当前层的所有节点
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // 将下一层的节点加入队列
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return depth;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，每个节点都需要入队出队一次
- 空间复杂度：O(w)，其中w是树的最大宽度
  - 最好情况：O(1)
  - 最坏情况（完全二叉树的最后一层）：O(n/2) ≈ O(n)

## 题目重点
1. 理解树的深度定义
2. 掌握递归和迭代两种解法
3. 理解DFS和BFS在树遍历中的应用

## 解题技巧

### 1. 递归三要素
- 终止条件：root == null
- 返回值：子树的最大深度
- 单层逻辑：取左右子树最大深度 + 1

### 2. BFS层序遍历模板
```java
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);

while (!queue.isEmpty()) {
    int levelSize = queue.size();
    for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        // 处理节点...
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

## 相关题目
- [111. 二叉树的最小深度](https://leetcode.com/problems/minimum-depth-of-binary-tree/)
- [559. N叉树的最大深度](https://leetcode.com/problems/maximum-depth-of-n-ary-tree/)
- [110. 平衡二叉树](https://leetcode.com/problems/balanced-binary-tree/)

## 延伸思考

### 1. 深度vs高度
- 深度：从根节点到该节点的边数
- 高度：从该节点到最远叶子节点的边数
- 树的最大深度 = 树的高度

### 2. DFS vs BFS
- DFS优点：代码简洁，空间复杂度可能更优
- BFS优点：直观理解层次结构，某些场景更高效
- 本题两种方法都可以，DFS更为简洁

### 3. 递归优化
- 尾递归优化（虽然Java不支持）
- 记忆化递归（本题不需要）
- 迭代代替递归

## 常见误区

### 1. 空树处理
```java
if (root == null) return 0;  // 不要忘记这个基本情况
```

### 2. 深度计算
```java
// 错误写法：忘记加1
return Math.max(maxDepth(root.left), maxDepth(root.right));

// 正确写法：当前节点要算上
return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
```

### 3. 层序遍历计数
```java
// 错误写法：在处理节点时增加深度
for (int i = 0; i < levelSize; i++) {
    depth++;  // 错误！这样会重复计算
    ...
}

// 正确写法：处理完整层后增加深度
while (!queue.isEmpty()) {
    depth++;  // 每层只加一次
    ...
}
```

## 面试技巧

### 1. 解法选择
- 优先展示递归解法（代码简洁）
- 主动提出迭代解法（显示思维全面）
- 分析两种解法的优劣

### 2. 代码优化
- 变量命名清晰
- 注释适度
- 考虑边界情况

### 3. 沟通要点
- 说明思路再写代码
- 解释复杂度分析
- 提出可能的优化方向
