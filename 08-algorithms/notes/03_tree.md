# 树结构基础知识

## 基本概念

### 1. 树的定义
- 由节点和边组成的无环连通图
- 每个节点有零个或多个子节点
- 没有父节点的节点称为根节点
- 没有子节点的节点称为叶节点

### 2. 二叉树的特点
```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
```

- 每个节点最多有两个子节点
- 区分左子树和右子树
- 可以为空

### 3. 常见的树类型

#### 二叉搜索树（BST）
- 左子树的所有节点值小于根节点
- 右子树的所有节点值大于根节点
- 左右子树都是BST
- 中序遍历得到升序序列

#### 平衡二叉树（AVL树）
- 任意节点的左右子树高度差不超过1
- 插入和删除时通过旋转维持平衡
- 查找、插入、删除的时间复杂度都是O(log n)

#### 完全二叉树
- 除最后一层外，其他层的节点数达到最大
- 最后一层的节点从左向右填充

#### 满二叉树
- 除叶节点外，每个节点都有两个子节点
- 所有叶节点都在同一层

## 树的遍历

### 1. 深度优先搜索（DFS）

#### 前序遍历（根-左-右）
```java
public void preorder(TreeNode root) {
    if (root == null) return;
    // 访问根节点
    System.out.print(root.val + " ");
    preorder(root.left);  // 遍历左子树
    preorder(root.right); // 遍历右子树
}
```

#### 中序遍历（左-根-右）
```java
public void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);   // 遍历左子树
    // 访问根节点
    System.out.print(root.val + " ");
    inorder(root.right);  // 遍历右子树
}
```

#### 后序遍历（左-右-根）
```java
public void postorder(TreeNode root) {
    if (root == null) return;
    postorder(root.left);  // 遍历左子树
    postorder(root.right); // 遍历右子树
    // 访问根节点
    System.out.print(root.val + " ");
}
```

### 2. 广度优先搜索（BFS）

#### 层序遍历
```java
public void levelOrder(TreeNode root) {
    if (root == null) return;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        System.out.println(); // 换行表示新的一层
    }
}
```

## 常见操作

### 1. 树的构建

#### 从数组构建完全二叉树
```java
public TreeNode buildFromArray(Integer[] arr) {
    if (arr == null || arr.length == 0) return null;
    TreeNode root = new TreeNode(arr[0]);
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    for (int i = 1; i < arr.length; i += 2) {
        TreeNode current = queue.poll();
        if (arr[i] != null) {
            current.left = new TreeNode(arr[i]);
            queue.offer(current.left);
        }
        if (i + 1 < arr.length && arr[i + 1] != null) {
            current.right = new TreeNode(arr[i + 1]);
            queue.offer(current.right);
        }
    }
    return root;
}
```

#### 从前序和中序遍历构建二叉树
```java
public TreeNode buildTree(int[] preorder, int[] inorder) {
    return buildTreeHelper(preorder, 0, preorder.length - 1,
                         inorder, 0, inorder.length - 1);
}

private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd,
                               int[] inorder, int inStart, int inEnd) {
    if (preStart > preEnd) return null;
    
    TreeNode root = new TreeNode(preorder[preStart]);
    int rootIndex = 0;
    for (int i = inStart; i <= inEnd; i++) {
        if (inorder[i] == root.val) {
            rootIndex = i;
            break;
        }
    }
    
    int leftSubtreeSize = rootIndex - inStart;
    
    root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftSubtreeSize,
                              inorder, inStart, rootIndex - 1);
    root.right = buildTreeHelper(preorder, preStart + leftSubtreeSize + 1, preEnd,
                               inorder, rootIndex + 1, inEnd);
    
    return root;
}
```

### 2. 树的修改

#### 插入节点（BST）
```java
public TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    
    return root;
}
```

#### 删除节点（BST）
```java
public TreeNode delete(TreeNode root, int val) {
    if (root == null) return null;
    
    if (val < root.val) {
        root.left = delete(root.left, val);
    } else if (val > root.val) {
        root.right = delete(root.right, val);
    } else {
        // 找到要删除的节点
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;
        
        // 有两个子节点，找右子树的最小值替换
        TreeNode minNode = findMin(root.right);
        root.val = minNode.val;
        root.right = delete(root.right, minNode.val);
    }
    
    return root;
}

private TreeNode findMin(TreeNode node) {
    while (node.left != null) {
        node = node.left;
    }
    return node;
}
```

## 常见问题类型

### 1. 遍历问题
- 各种遍历方式的实现
- 遍历序列的转换
- 特定遍历要求

### 2. 路径问题
- 路径和
- 最长路径
- 特定路径查找

### 3. 结构判断
- 对称性判断
- 平衡性检查
- 相同树判断

### 4. 修改结构
- 翻转/镜像
- 合并树
- 修剪树

## 解题技巧

### 1. 递归处理
- 明确递归函数的作用
- 确定基本情况
- 考虑返回值的处理

### 2. 辅助数据结构
- 使用栈实现迭代
- 使用队列进行层序遍历
- 使用Map存储额外信息

### 3. 树的性质利用
- BST的有序性
- 完全二叉树的性质
- 平衡树的特点

## 常见陷阱和注意事项

### 1. 边界情况
- 空树的处理
- 单节点树
- 满二叉树/完全二叉树

### 2. 递归陷阱
- 栈溢出
- 重复计算
- 返回值处理

### 3. 性能考虑
- 时间复杂度
- 空间复杂度
- 递归转迭代

## 实战建议

### 1. 刷题顺序
1. 基本遍历（前序、中序、后序）
2. 层序遍历
3. 简单路径问题
4. 树的修改问题
5. 复杂结构问题

### 2. 解题步骤
1. 分析树的特征
2. 选择合适的遍历方式
3. 设计递归函数
4. 处理边界情况
5. 优化解法

### 3. 调试技巧
1. 画图理解
2. 小规模测试
3. 打印中间结果
4. 检查递归出口
