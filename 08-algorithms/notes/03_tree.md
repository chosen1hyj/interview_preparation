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

### 1. 最近公共祖先（LCA）

#### 定义
- 找到两个节点p和q的最低共同祖先
- 可能是节点本身

#### 解题思路
```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    
    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

- 时间复杂度：O(N)，每个节点访问一次
- 空间复杂度：O(H)，递归栈深度为树的高度

#### 注意事项
- 处理节点不存在的情况
- 考虑p和q相同的情况
- 特殊情况：p或q本身就是LCA

### 2. 遍历问题
- 各种遍历方式的实现
- 遍历序列的转换
- 特定遍历要求

### 2. 路径问题
- 路径和
- 最长路径
- 特定路径查找

### 3. 结构判断

#### 树的高度计算
```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}
```
- 时间复杂度：O(N)
- 空间复杂度：O(H)

#### 对称性判断
```java
public boolean isSymmetric(TreeNode root) {
    return isMirror(root, root);
}

private boolean isMirror(TreeNode t1, TreeNode t2) {
    if (t1 == null && t2 == null) return true;
    if (t1 == null || t2 == null) return false;
    return (t1.val == t2.val) 
        && isMirror(t1.left, t2.right)
        && isMirror(t1.right, t2.left);
}
```
- 时间复杂度：O(N)
- 空间复杂度：O(H)

#### 平衡性检查
```java
public boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}

private int checkHeight(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = checkHeight(node.left);
    if (leftHeight == -1) return -1;
    
    int rightHeight = checkHeight(node.right);
    if (rightHeight == -1) return -1;
    
    if (Math.abs(leftHeight - rightHeight) > 1) return -1;
    return Math.max(leftHeight, rightHeight) + 1;
}
```
- 时间复杂度：O(N)
- 空间复杂度：O(H)

- 对称性判断
- 平衡性检查
- 相同树判断

### 4. 路径和问题

#### 定义
- 判断是否存在从根节点到叶节点的路径，其节点值之和等于给定目标

#### 解题思路
```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    // 到达叶子节点时检查路径和
    if (root.left == null && root.right == null) {
        return targetSum == root.val;
    }
    
    return hasPathSum(root.left, targetSum - root.val) 
        || hasPathSum(root.right, targetSum - root.val);
}
```
- 时间复杂度：O(N)
- 空间复杂度：O(H)

#### 注意事项
- 处理空树的情况
- 考虑负数节点值
- 特殊情况：单节点树

### 5. 序列化与反序列化

#### 前序遍历方式
```java
// 序列化
public String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    serializeHelper(root, sb);
    return sb.toString();
}

private void serializeHelper(TreeNode node, StringBuilder sb) {
    if (node == null) {
        sb.append("#,");
        return;
    }
    
    sb.append(node.val).append(",");
    serializeHelper(node.left, sb);
    serializeHelper(node.right, sb);
}

// 反序列化
public TreeNode deserialize(String data) {
    Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
    return deserializeHelper(nodes);
}

private TreeNode deserializeHelper(Queue<String> nodes) {
    String val = nodes.poll();
    if (val.equals("#")) return null;
    
    TreeNode node = new TreeNode(Integer.valueOf(val));
    node.left = deserializeHelper(nodes);
    node.right = deserializeHelper(nodes);
    return node;
}
```

- 时间复杂度：O(N)
- 空间复杂度：O(N)

#### 注意事项
- 空节点的表示
- 分隔符的选择
- 数据格式的一致性

### 6. 修改结构
- 翻转/镜像
- 合并树
- 修剪树

### 边界条件处理

1. 空树检查
```java
if (root == null) {
    // 根据题目要求返回适当值
    return ...;
}
```

2. 单节点树处理
- 特殊情况：只有一个根节点
```java
if (root.left == null && root.right == null) {
    // 处理单节点树的情况
}
```

3. 负数节点值
- 在路径和问题中需要特别注意负数节点
```java
// 考虑负数路径和
if (currentSum < 0) {
    // 特殊处理
}
```

4. 不平衡树
- 左右子树高度差较大时的处理
```java
int leftHeight = getHeight(root.left);
int rightHeight = getHeight(root.right);

if (Math.abs(leftHeight - rightHeight) > 1) {
    // 处理不平衡情况
}
```

### 优化方向

1. 递归优化
- 尾递归优化
```java
public int maxDepth(TreeNode root, int currentDepth) {
    if (root == null) return currentDepth;
    return Math.max(maxDepth(root.left, currentDepth + 1),
                    maxDepth(root.right, currentDepth + 1));
}
```

2. 迭代替代递归
- 使用栈模拟递归
```java
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    
    Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
    stack.push(new Pair<>(root, 1));
    int maxDepth = 0;
    
    while (!stack.isEmpty()) {
        Pair<TreeNode, Integer> current = stack.pop();
        TreeNode node = current.getKey();
        int depth = current.getValue();
        
        maxDepth = Math.max(maxDepth, depth);
        
        if (node.left != null) {
            stack.push(new Pair<>(node.left, depth + 1));
        }
        if (node.right != null) {
            stack.push(new Pair<>(node.right, depth + 1));
        }
    }
    
    return maxDepth;
}
```

3. 缓存计算结果
- 记忆化搜索
```java
Map<TreeNode, Integer> cache = new HashMap<>();

public int getHeight(TreeNode node) {
    if (node == null) return 0;
    if (cache.containsKey(node)) return cache.get(node);
    
    int height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    cache.put(node, height);
    return height;
}
```

### 常见错误总结

1. 忘记处理空树
```java
// 错误示例
public int maxDepth(TreeNode root) {
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}

// 正确处理
public int maxDepth(TreeNode root) {
    if (root == null) return 0; // 必须处理空树
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
}
```

2. 返回值类型错误
- 注意返回值类型是否匹配
```java
// 错误示例
public boolean isValidBST(TreeNode root) {
    return validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
}

// 正确处理
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
```

3. 溢出问题
- 使用long类型避免整数溢出
```java
// 错误示例
public boolean isValidBST(TreeNode root, int min, int max) {
    // 可能发生溢出
}

// 正确处理
public boolean isValidBST(TreeNode root, long min, long max) {
    // 使用long类型
}
```

4. 忘记更新全局变量
```java
// 错误示例
private int maxSum = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    helper(root);
    return maxSum; // 可能忘记更新maxSum
}

// 正确处理
private int maxSum = Integer.MIN_VALUE;

public int maxPathSum(TreeNode root) {
    helper(root);
    return maxSum;
}

private int helper(TreeNode node) {
    if (node == null) return 0;
    
    int left = Math.max(0, helper(node.left));
    int right = Math.max(0, helper(node.right));
    
    maxSum = Math.max(maxSum, left + right + node.val); // 正确更新
    return Math.max(left, right) + node.val;
}
```

## 解题技巧
