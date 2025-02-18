# 并查集基础知识

## 基本概念

### 1. 数据结构定义
```java
class UnionFind {
    private int[] parent;
    private int[] rank;
    
    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }
    
    // 查找根节点（带路径压缩）
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    // 合并两个集合
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;
        
        if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
    
    // 判断是否连通
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

## 实战应用

### 1. 图的连通分量
```java
// 计算连通分量数量
int countComponents(int n, int[][] edges) {
    UnionFind uf = new UnionFind(n);
    for (int[] edge : edges) {
        uf.union(edge[0], edge[1]);
    }
    
    Set<Integer> roots = new HashSet<>();
    for (int i = 0; i < n; i++) {
        roots.add(uf.find(i));
    }
    return roots.size();
}
```

### 2. 冗余连接
```java
// 找到冗余连接
int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    UnionFind uf = new UnionFind(n + 1);
    
    for (int[] edge : edges) {
        if (!uf.union(edge[0], edge[1])) {
            return edge;
        }
    }
    return new int[0];
}
```

### 3. 岛屿数量
```java
// 使用并查集解决岛屿数量问题
int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int m = grid.length;
    int n = grid[0].length;
    UnionFind uf = new UnionFind(m * n);
    int count = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == '1') {
                count++;
            }
        }
    }
    
    int[][] directions = {{0,1},{1,0}};
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == '1') {
                for (int[] dir : directions) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    
                    if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == '1') {
                        if (uf.union(i * n + j, x * n + y)) {
                            count--;
                        }
                    }
                }
            }
        }
    }
    return count;
}
```

## 性能优化

### 1. 路径压缩
- 在find操作中将节点直接连接到根节点
- 减少树的高度

### 2. 按秩合并
- 根据树的高度决定合并方向
- 避免生成过高的树

## 注意事项

### 1. 边界处理
- 初始化检查
- 空数据处理
- 越界判断

### 2. 性能考量
- 时间复杂度接近O(1)
- 空间复杂度O(n)

### 3. 常见陷阱
- 忘记路径压缩
- 合并时未考虑秩
- 连通性判断错误
