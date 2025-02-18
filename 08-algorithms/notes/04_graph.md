# 图论基础知识

## 基本概念

### 1. 图的定义
- 由顶点（vertex）和边（edge）组成
- 可以是有向的或无向的
- 边可以有权重（weighted）或无权重（unweighted）

### 2. 图的表示方法

#### 邻接矩阵
```java
// 使用二维数组表示图
int[][] graph = new int[V][V];  // V为顶点数
// graph[i][j] = 1 表示存在从i到j的边
// graph[i][j] = weight 表示从i到j的边的权重
```

优缺点：
- 优点：
  - 直观、简单
  - 查询两点间是否有边快速O(1)
  - 适合密集图
- 缺点：
  - 空间复杂度O(V²)
  - 不适合稀疏图
  - 添加顶点需要调整矩阵大小

#### 邻接表
```java
// 使用List数组表示图
List<Integer>[] graph = new List[V];
// 或者使用Map
Map<Integer, List<Integer>> graph = new HashMap<>();

// 初始化
for (int i = 0; i < V; i++) {
    graph[i] = new ArrayList<>();
}
```

优缺点：
- 优点：
  - 空间效率高，适合稀疏图
  - 容易找到顶点的所有邻居
  - 添加顶点容易
- 缺点：
  - 查询两点间是否有边较慢
  - 删除边操作较复杂

## 基本操作

### 1. 图的遍历

#### DFS（深度优先搜索）
```java
public void dfs(int[][] graph, int vertex, boolean[] visited) {
    // 标记当前顶点为已访问
    visited[vertex] = true;
    System.out.print(vertex + " ");
    
    // 遍历所有相邻顶点
    for (int i = 0; i < graph.length; i++) {
        if (graph[vertex][i] == 1 && !visited[i]) {
            dfs(graph, i, visited);
        }
    }
}
```

#### BFS（广度优先搜索）
```java
public void bfs(int[][] graph, int start) {
    boolean[] visited = new boolean[graph.length];
    Queue<Integer> queue = new LinkedList<>();
    
    visited[start] = true;
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        System.out.print(vertex + " ");
        
        // 访问所有未访问的相邻顶点
        for (int i = 0; i < graph.length; i++) {
            if (graph[vertex][i] == 1 && !visited[i]) {
                visited[i] = true;
                queue.offer(i);
            }
        }
    }
}
```

### 2. 最短路径算法

#### Dijkstra算法
```java
public int[] dijkstra(int[][] graph, int start) {
    int n = graph.length;
    int[] dist = new int[n];
    boolean[] visited = new boolean[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;
    
    for (int i = 0; i < n - 1; i++) {
        // 找到未访问的最短距离顶点
        int minVertex = getMinDistVertex(dist, visited);
        visited[minVertex] = true;
        
        // 更新相邻顶点的距离
        for (int j = 0; j < n; j++) {
            if (!visited[j] && graph[minVertex][j] != 0 &&
                dist[minVertex] != Integer.MAX_VALUE &&
                dist[minVertex] + graph[minVertex][j] < dist[j]) {
                dist[j] = dist[minVertex] + graph[minVertex][j];
            }
        }
    }
    
    return dist;
}

private int getMinDistVertex(int[] dist, boolean[] visited) {
    int min = Integer.MAX_VALUE;
    int minVertex = -1;
    
    for (int i = 0; i < dist.length; i++) {
        if (!visited[i] && dist[i] < min) {
            min = dist[i];
            minVertex = i;
        }
    }
    
    return minVertex;
}
```

#### Floyd-Warshall算法
```java
public void floydWarshall(int[][] graph) {
    int V = graph.length;
    int[][] dist = new int[V][V];
    
    // 初始化距离矩阵
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
            dist[i][j] = graph[i][j];
        }
    }
    
    // 通过k顶点更新所有点对最短路径
    for (int k = 0; k < V; k++) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][k] != Integer.MAX_VALUE &&
                    dist[k][j] != Integer.MAX_VALUE &&
                    dist[i][k] + dist[k][j] < dist[i][j]) {
                    dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
    }
}
```

### 3. 最小生成树

#### Kruskal算法
```java
public class KruskalMST {
    class Edge implements Comparable<Edge> {
        int src, dest, weight;
        
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }
    
    class DisjointSet {
        int[] parent, rank;
        
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int px = find(x), py = find(y);
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
    
    public List<Edge> kruskal(List<Edge> edges, int V) {
        Collections.sort(edges);
        List<Edge> mst = new ArrayList<>();
        DisjointSet ds = new DisjointSet(V);
        
        for (Edge edge : edges) {
            int x = ds.find(edge.src);
            int y = ds.find(edge.dest);
            
            if (x != y) {
                mst.add(edge);
                ds.union(x, y);
            }
        }
        
        return mst;
    }
}
```

## 常见图算法

### 1. 拓扑排序
```java
public List<Integer> topologicalSort(int[][] graph) {
    int V = graph.length;
    int[] inDegree = new int[V];
    List<Integer> result = new ArrayList<>();
    
    // 计算入度
    for (int i = 0; i < V; i++) {
        for (int j = 0; j < V; j++) {
            if (graph[i][j] == 1) {
                inDegree[j]++;
            }
        }
    }
    
    Queue<Integer> queue = new LinkedList<>();
    // 将入度为0的顶点加入队列
    for (int i = 0; i < V; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        result.add(vertex);
        
        // 减少相邻顶点的入度
        for (int i = 0; i < V; i++) {
            if (graph[vertex][i] == 1) {
                inDegree[i]--;
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
        }
    }
    
    return result.size() == V ? result : new ArrayList<>();
}
```

### 2. 强连通分量（Kosaraju算法）
```java
public class KosarajuSCC {
    private void dfs(List<List<Integer>> graph, int v, boolean[] visited,
                    Stack<Integer> stack) {
        visited[v] = true;
        for (int u : graph.get(v)) {
            if (!visited[u]) {
                dfs(graph, u, visited, stack);
            }
        }
        stack.push(v);
    }
    
    private void reverseDfs(List<List<Integer>> reversed, int v,
                          boolean[] visited, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int u : reversed.get(v)) {
            if (!visited[u]) {
                reverseDfs(reversed, u, visited, component);
            }
        }
    }
    
    public List<List<Integer>> getSCC(List<List<Integer>> graph) {
        int V = graph.size();
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        
        // 第一次DFS，获得结束时间顺序
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(graph, i, visited, stack);
            }
        }
        
        // 创建反向图
        List<List<Integer>> reversed = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            reversed.add(new ArrayList<>());
        }
        for (int i = 0; i < V; i++) {
            for (int j : graph.get(i)) {
                reversed.get(j).add(i);
            }
        }
        
        // 第二次DFS，找强连通分量
        Arrays.fill(visited, false);
        List<List<Integer>> scc = new ArrayList<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                reverseDfs(reversed, v, visited, component);
                scc.add(component);
            }
        }
        
        return scc;
    }
}
```

## 常见问题类型

### 1. 图的遍历类
- DFS应用
- BFS应用
- 拓扑排序

### 2. 最短路径类
- 单源最短路径
- 所有点对最短路径
- 特定条件下的路径问题

### 3. 连通性问题
- 强连通分量
- 割点和桥
- 二分图判定

### 4. 环相关
- 环的检测
- 最小环
- 欧拉回路

## 解题技巧

### 1. 建图技巧
- 选择合适的图表示方法
- 处理边的方向性
- 处理权重信息

### 2. 搜索优化
- 剪枝
- 记忆化
- 双向搜索

### 3. 特殊图处理
- 二分图
- 树形图
- 有向无环图(DAG)

## 常见错误

### 1. 访问标记
- 忘记标记已访问节点
- 忘记重置访问状态
- 访问标记的时机不当

### 2. 边界条件
- 图为空
- 只有一个节点
- 存在自环或重边

### 3. 算法选择
- 使用不适合的算法
- 忽略图的特性
- 效率考虑不周

## 面试技巧

### 1. 分析问题
- 确定图的类型
- 识别问题特点
- 选择合适算法

### 2. 代码实现
- 模块化编程
- 清晰的变量命名
- 注释关键步骤

### 3. 优化方向
- 时间复杂度优化
- 空间复杂度优化
- 代码简洁性

## 实战建议

### 1. 练习顺序
1. 图的表示和遍历
2. 最短路径问题
3. 拓扑排序
4. 连通性问题
5. 复杂图算法

### 2. 重点掌握
- DFS和BFS的应用
- Dijkstra算法
- 拓扑排序
- 并查集操作

### 3. 进阶方向
- 网络流
- 二分图匹配
- 欧拉图和哈密顿图
- 平面图算法
