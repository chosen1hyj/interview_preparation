# 98. 验证二叉搜索树（Validate Binary Search Tree）

[题目链接](https://leetcode.com/problems/validate-binary-search-tree/)

## 题目描述
给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。

有效二叉搜索树定义如下：
- 节点的左子树只包含小于当前节点的数。
- 节点的右子树只包含大于当前节点的数。
- 所有左子树和右子树自身必须也是二叉搜索树。

示例：
```
输入：
    2
   / \
  1   3
输出：true

输入：
    5
   / \
  1   4
     / \
    3   6
输出：false
解释：根节点的值是 5 ，但是右子树的值 3 小于 5
```

## 解法一：递归（带范围）

### 思路
通过递归时传递当前节点值的允许范围（最小值和最大值），保证每个节点都在合法范围内。

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Integer min, Integer max) {
        // 空节点视为BST
        if (node == null) {
            return true;
        }
        
        // 检查当前节点值是否在允许范围内
        if ((min != null && node.val <= min) || 
            (max != null && node.val >= max)) {
            return false;
        }
        
        // 递归验证左右子树
        // 左子树的所有节点值必须小于当前节点值
        // 右子树的所有节点值必须大于当前节点值
        return validate(node.left, min, node.val) && 
               validate(node.right, node.val, max);
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，每个节点访问一次
- 空间复杂度：O(h)，h为树的高度，递归栈的开销

## 解法二：中序遍历

### 思路
BST的中序遍历结果应该是一个递增序列。通过中序遍历，检查每个节点的值是否大于前一个节点的值。

```java
class Solution {
    private Integer prevVal = null;
    
    public boolean isValidBST(TreeNode root) {
        return inorderTraversal(root);
    }
    
    private boolean inorderTraversal(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // 遍历左子树
        if (!inorderTraversal(node.left)) {
            return false;
        }
        
        // 检查当前节点值是否大于前一个值
        if (prevVal != null && node.val <= prevVal) {
            return false;
        }
        prevVal = node.val;
        
        // 遍历右子树
        return inorderTraversal(node.right);
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(h)，h为树的高度

## 解法三：迭代版中序遍历

### 思路
使用栈实现中序遍历，同时检查递增性。

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        Integer prevVal = null;
        
        while (!stack.isEmpty() || curr != null) {
            // 遍历到最左节点
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            curr = stack.pop();
            
            // 检查是否递增
            if (prevVal != null && curr.val <= prevVal) {
                return false;
            }
            prevVal = curr.val;
            
            curr = curr.right;
        }
        
        return true;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(h)

## 题目重点
1. BST的定义和性质
2. 理解递归解法中范围的传递
3. 中序遍历的应用
4. 处理边界情况和整数溢出

## 解题技巧

### 1. 明确BST的性质
- 左子树的所有节点值 < 根节点值
- 右子树的所有节点值 > 根节点值
- 中序遍历得到递增序列

### 2. 递归时传递范围
```java
// 左子树节点值范围：(min, root.val)
validate(node.left, min, node.val)

// 右子树节点值范围：(root.val, max)
validate(node.right, node.val, max)
```

### 3. 使用Long处理整数边界
```java
// 处理整数边界情况
private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) && 
           validate(node.right, node.val, max);
}
```

## 常见错误

### 1. 只检查直接子节点
```java
// 错误写法：只检查直接子节点
return (root.left == null || root.left.val < root.val) &&
       (root.right == null || root.right.val > root.val);
```

### 2. 忽略相等的情况
```java
// 错误写法：忽略了等于的情况
if (node.val < min || node.val > max)

// 正确写法：应该包含等于
if (node.val <= min || node.val >= max)
```

### 3. 递归范围错误
```java
// 错误写法：范围设置错误
validate(node.left, min, max) &&
validate(node.right, min, max)
```

## 相关题目
- [700. 二叉搜索树中的搜索](https://leetcode.com/problems/search-in-a-binary-search-tree/)
- [701. 二叉搜索树中的插入操作](https://leetcode.com/problems/insert-into-a-binary-search-tree/)
- [235. 二叉搜索树的最近公共祖先](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)

## 延伸思考

### 1. BST的其他应用
- 有序数据的存储
- 范围查询
- 快速查找/插入/删除

### 2. BST的变体
- 平衡BST（AVL树）
- 红黑树
- B树和B+树

### 3. BST相关操作的优化
- 插入优化
- 删除优化
- 查找优化

## 面试技巧

### 1. 解题思路展示
- 先说明BST的性质
- 提出多种可能的解法
- 分析各种解法的优缺点

### 2. 代码实现要点
- 边界条件处理
- 整数溢出考虑
- 代码的可维护性

### 3. 优化讨论
- 时间复杂度优化
- 空间复杂度优化
- 代码简洁性优化
