# 深度优先搜索（Depth First Search）

## 基本概念
深度优先搜索是一种用于遍历或搜索树或图的算法。它从起始节点开始，沿着一条路径尽可能深地搜索，直到到达叶子节点或目标节点，然后回溯到前一个节点继续搜索。

## 核心思想
1. 访问：访问当前节点
2. 标记：标记已访问节点
3. 递归：对相邻未访问节点递归执行DFS

## 典型应用场景
- 图的连通性判断
- 拓扑排序
- 寻找路径
- 迷宫求解
- 网络爬虫
- 组合问题

## 实现方式
### 递归实现
```java
public void dfs(Node node) {
    if (node == null || visited.contains(node)) return;
    
    visited.add(node);
    process(node);
    
    for (Node neighbor : node.neighbors) {
        dfs(neighbor);
    }
}
```

### 迭代实现（使用栈）
```java
public void dfsIterative(Node start) {
    Stack<Node> stack = new Stack<>();
    stack.push(start);
    visited.add(start);
    
    while (!stack.isEmpty()) {
        Node node = stack.pop();
        process(node);
        
        for (Node neighbor : node.neighbors) {
            if (!visited.contains(neighbor)) {
                stack.push(neighbor);
                visited.add(neighbor);
            }
        }
    }
}
```

## 优化技巧
1. 剪枝：提前终止不必要的搜索分支
2. 缓存：存储中间结果避免重复计算
3. 排序：优先搜索更有可能成功的分支
4. 状态压缩：使用位运算等技术优化空间使用

## 注意事项
1. 避免死循环
2. 正确维护访问状态
3. 处理边界情况
4. 考虑递归深度限制

## 时间复杂度分析
- O(V+E)，其中V是顶点数，E是边数

## 空间复杂度分析
- 递归实现：O(h)，h是递归深度
- 迭代实现：O(V)

## 实际应用
1. 社交网络分析
2. 路径规划
3. 游戏AI
4. 数据挖掘
5. 网络安全

## 面试技巧
1. 明确递归定义
2. 确定base case
3. 合理设计状态表示
4. 优化剪枝条件

## 常见错误
1. 忘记标记访问状态
2. 错误的状态维护
3. 不完整的约束检查
4. 边界条件处理不当

## 总结
深度优先搜索虽然可能在最坏情况下需要遍历所有节点，但其系统化的搜索方式和灵活的实现方式使其在解决许多图论和组合问题时非常有效。掌握其基本框架和优化技巧，可以应对各种复杂的搜索问题。
