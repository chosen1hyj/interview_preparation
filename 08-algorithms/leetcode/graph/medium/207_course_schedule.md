# 207. 课程表（Course Schedule）

[题目链接](https://leetcode.com/problems/course-schedule/)

## 题目描述
你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses-1。

在选修某些课程之前需要一些先修课程。先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则必须先学习课程 bi 。

例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。

请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。

示例：
```
输入：numCourses = 2, prerequisites = [[1,0]]
输出：true
解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。这是可能的。

输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
输出：false
解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
```

## 解法一：拓扑排序（BFS）

### 思路
使用入度表和BFS实现拓扑排序，如果所有课程都能被访问，说明不存在环，可以完成所有课程。

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 构建邻接表和入度表
        List<List<Integer>> adjacency = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        // 初始化邻接表
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new ArrayList<>());
        }
        
        // 填充邻接表和入度表
        for (int[] prerequisite : prerequisites) {
            adjacency.get(prerequisite[1]).add(prerequisite[0]);
            inDegree[prerequisite[0]]++;
        }
        
        // 将所有入度为0的课程加入队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        // BFS拓扑排序
        int visited = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            visited++;
            
            // 将该课程的后续课程入度减1
            for (int successor : adjacency.get(course)) {
                inDegree[successor]--;
                if (inDegree[successor] == 0) {
                    queue.offer(successor);
                }
            }
        }
        
        return visited == numCourses;
    }
}
```

### 复杂度分析
- 时间复杂度：O(V + E)，其中 V 为课程数，E 为先修课程的要求数
- 空间复杂度：O(V + E)，用于存储邻接表和入度表

## 解法二：DFS检测环

### 思路
使用DFS检测图中是否存在环，如果存在环则说明课程关系中存在循环依赖。

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjacency = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new ArrayList<>());
        }
        
        // 构建邻接表
        for (int[] prerequisite : prerequisites) {
            adjacency.get(prerequisite[1]).add(prerequisite[0]);
        }
        
        // 0=未访问，1=访问中，2=已完成
        int[] visited = new int[numCourses];
        
        // 检查每个课程
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(i, adjacency, visited)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean hasCycle(int course, List<List<Integer>> adjacency, int[] visited) {
        // 当前课程正在被访问，说明有环
        if (visited[course] == 1) return true;
        // 当前课程已经被访问过，说明没有环
        if (visited[course] == 2) return false;
        
        // 标记当前课程正在被访问
        visited[course] = 1;
        
        // 递归访问所有后续课程
        for (int successor : adjacency.get(course)) {
            if (hasCycle(successor, adjacency, visited)) {
                return true;
            }
        }
        
        // 标记当前课程已经访问完成
        visited[course] = 2;
        return false;
    }
}
```

### 复杂度分析
- 时间复杂度：O(V + E)
- 空间复杂度：O(V + E)

## 题目重点
1. 图的表示方法（邻接表）
2. 拓扑排序的实现
3. 环检测的方法
4. 入度概念的应用

## 解题技巧

### 1. 建图方式
```java
// 使用邻接表表示图
List<List<Integer>> adjacency = new ArrayList<>();
for (int i = 0; i < numCourses; i++) {
    adjacency.add(new ArrayList<>());
}
```

### 2. 入度计算
```java
// 计算每个节点的入度
int[] inDegree = new int[numCourses];
for (int[] prerequisite : prerequisites) {
    inDegree[prerequisite[0]]++;
}
```

### 3. 拓扑排序模板
```java
Queue<Integer> queue = new LinkedList<>();
// 将入度为0的节点加入队列
for (int i = 0; i < numCourses; i++) {
    if (inDegree[i] == 0) {
        queue.offer(i);
    }
}

while (!queue.isEmpty()) {
    int course = queue.poll();
    visited++;
    // 更新相邻节点的入度
    for (int successor : adjacency.get(course)) {
        inDegree[successor]--;
        if (inDegree[successor] == 0) {
            queue.offer(successor);
        }
    }
}
```

## 相关题目
- [210. 课程表 II](https://leetcode.com/problems/course-schedule-ii/)
- [802. 找到最终的安全状态](https://leetcode.com/problems/find-eventual-safe-states/)
- [1462. 课程表 IV](https://leetcode.com/problems/course-schedule-iv/)

## 延伸思考

### 1. 实际应用场景
- 任务调度
- 编译依赖关系
- 项目构建顺序

### 2. 其他解决方案
- Kahn算法
- 有向无环图（DAG）验证
- 并查集方法

### 3. 优化方向
- 减少空间使用
- 提高检测效率
- 处理大规模数据

## 面试技巧

### 1. 讨论要点
- 图的表示方法选择
- 算法的时空复杂度
- 不同解法的优缺点

### 2. 解题步骤
1. 分析问题是否可以转化为图
2. 选择合适的算法（BFS/DFS）
3. 实现基本解法
4. 考虑优化方案

### 3. 代码质量
- 清晰的变量命名
- 模块化的代码结构
- 完善的注释说明
