# 拓扑排序基础知识

## 基本概念

### 1. 定义
- 有向无环图 (DAG) 的线性排序
- 若存在边 u -> v，则排序中 u 必须在 v 之前

### 2. 常见表示方法
```java
// 邻接表表示图
List<List<Integer>> graph = new ArrayList<>();
int[] inDegree = new int[n];

for (int i = 0; i < n; i++) {
    graph.add(new ArrayList<>());
}

// 添加边
void addEdge(int from, int to) {
    graph.get(from).add(to);
    inDegree[to]++;
}
```

## 算法实现

### 1. Kahn算法
```java
List<Integer> topologicalSortKahn(int n) {
    List<Integer> res = new ArrayList<>();
    Queue<Integer> queue = new LinkedList<>();
    
    // 入度为0的节点入队
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        res.add(cur);
        
        for (int neighbor : graph.get(cur)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return res.size() == n ? res : new ArrayList<>();
}
```

### 2. DFS实现
```java
List<Integer> topologicalSortDFS(int n) {
    List<Integer> res = new ArrayList<>();
    boolean[] visited = new boolean[n];
    boolean[] visiting = new boolean[n];
    
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            if (!dfs(i, visited, visiting, res)) {
                return new ArrayList<>(); // 存在环
            }
        }
    }
    
    Collections.reverse(res);
    return res;
}

boolean dfs(int node, boolean[] visited, boolean[] visiting, List<Integer> res) {
    if (visiting[node]) return false; // 存在环
    if (visited[node]) return true;
    
    visiting[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!dfs(neighbor, visited, visiting, res)) {
            return false;
        }
    }
    
    visiting[node] = false;
    visited[node] = true;
    res.add(node);
    return true;
}
```

## 实战应用

### 1. 课程安排
```java
// 判断能否完成所有课程
boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        inDegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    int count = 0;
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        count++;
        
        for (int neighbor : graph.get(cur)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return count == numCourses;
}
```

### 2. 构建顺序
```java
// 返回构建顺序
int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        graph.add(new ArrayList<>());
    }
    
    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        inDegree[pre[0]]++;
    }
    
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
        if (inDegree[i] == 0) {
            queue.offer(i);
        }
    }
    
    int[] order = new int[numCourses];
    int index = 0;
    
    while (!queue.isEmpty()) {
        int cur = queue.poll();
        order[index++] = cur;
        
        for (int neighbor : graph.get(cur)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.offer(neighbor);
            }
        }
    }
    
    return index == numCourses ? order : new int[0];
}
```

## 注意事项

### 1. 边界处理
- 空图判断
- 孤立节点处理
- 环检测

### 2. 性能优化
- 邻接表优化
- 入度数组维护
- 缓存中间结果

### 3. 常见陷阱
- 图中存在环
- 多个入度为零的起点
- 不连通子图
