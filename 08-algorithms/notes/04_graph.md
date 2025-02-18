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

## 典型算法实现

### 1. Prim算法（最小生成树）
```java
public class PrimMST {
    public List<int[]> primMST(int[][] graph) {
        int V = graph.length;
        boolean[] inMST = new boolean[V];
        int[] key = new int[V];
        int[] parent = new int[V];
        Arrays.fill(key, Integer.MAX_VALUE);
        
        // 从顶点0开始
        key[0] = 0;
        parent[0] = -1;
        
        for (int count = 0; count < V - 1; count++) {
            // 找到最小key值的顶点
            int u = getMinKeyVertex(key, inMST);
            inMST[u] = true;
            
            // 更新相邻顶点
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !inMST[v] &&
                    graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        
        // 构建结果
        List<int[]> mst = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            mst.add(new int[]{parent[i], i, graph[i][parent[i]]});
        }
        return mst;
    }
    
    private int getMinKeyVertex(int[] key, boolean[] inMST) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < key.length; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}
```

时间复杂度：O(V²)
空间复杂度：O(V)

### 2. Bellman-Ford算法（单源最短路径）
```java
public class BellmanFord {
    public int[] bellmanFord(int[][] edges, int V, int start) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        
        // 松弛V-1次
        for (int i = 0; i < V - 1; i++) {
            for (int[] edge : edges) {
                int src = edge[0], dest = edge[1], weight = edge[2];
                if (dist[src] != Integer.MAX_VALUE &&
                    dist[src] + weight < dist[dest]) {
                    dist[dest] = dist[src] + weight;
                }
            }
        }
        
        // 检查负权环
        for (int[] edge : edges) {
            int src = edge[0], dest = edge[1], weight = edge[2];
            if (dist[src] != Integer.MAX_VALUE &&
                dist[src] + weight < dist[dest]) {
                throw new RuntimeException("Graph contains negative weight cycle");
            }
        }
        
        return dist;
    }
}
```

时间复杂度：O(VE)
空间复杂度：O(V)

## 典型例题

### LeetCode 207 - 课程表 (中等)
- 拓扑排序判断有向图是否有环
- 使用DFS或BFS实现拓扑排序
- 关键点：入度数组、邻接表

```java
// 示例代码片段
public boolean canFinish(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
        adj.add(new ArrayList<>());
    }
    for (int[] p : prerequisites) {
        adj.get(p[1]).add(p[0]);
        inDegree[p[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }
    
    int count = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        count++;
        for (int next : adj.get(course)) {
            if (--inDegree[next] == 0) {
                queue.offer(next);
            }
        }
    }
    return count == numCourses;
}
```

## 常见问题类型

### 1. 图的遍历类
- DFS应用：连通分量、路径查找、环检测
- BFS应用：最短路径、层序遍历
- 拓扑排序：课程表问题、任务调度

### 2. 最短路径类
- 单源最短路径：Dijkstra算法
- 所有点对最短路径：Floyd-Warshall算法
- 带限制的最短路径：Bellman-Ford算法

### 3. 连通性问题
- 强连通分量：Kosaraju算法
- 割点和桥：Tarjan算法
- 二分图判定：染色法

### 4. 环相关问题
- 环的检测：DFS/BFS
- 最小环：Floyd-Warshall变种
- 欧拉回路：Hierholzer算法

### 5. 最小生成树
- Kruskal算法
- Prim算法

### 6. 网络流问题
- 最大流：Ford-Fulkerson方法
- 最小割
