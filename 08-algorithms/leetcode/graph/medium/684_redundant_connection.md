# 冗余连接 (中等)

## 题目描述
在本问题中, 树是一个无向图，其中任何两个顶点只通过一条路径相连。换句话说，任何一个没有简单环路的连通图都是一棵树。

给你一个具有 n 个节点的树（从 1 到 n 标号），以及一个表示树的边的数组 edges，其中每个元素都是一个长度为 2 的数组 [u, v]，表示节点 u 和 v 之间有一条无向边。

返回最后添加的一条冗余边，这条边的存在使得树变成了有环的图。

## 示例
输入: [[1,2], [1,3], [2,3]]
输出: [2,3]
解释: 给出的图形成如下结构:
  1
 / \
2 - 3

## 解法
```java
// 并查集解法
class UnionFind {
    private int[] parent;
    
    public UnionFind(int n) {
        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;
        
        parent[rootY] = rootX;
        return true;
    }
}

public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    UnionFind uf = new UnionFind(n);
    
    for (int[] edge : edges) {
        if (!uf.union(edge[0], edge[1])) {
            return edge;
        }
    }
    
    return new int[0];
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(n)

## 关键点
- 并查集应用
- 路径压缩优化
- 按秩合并（可选）
