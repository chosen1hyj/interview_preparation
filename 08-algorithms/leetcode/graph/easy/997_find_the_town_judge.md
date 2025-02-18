# 997. 找到小镇的法官（Find the Town Judge）

[题目链接](https://leetcode.com/problems/find-the-town-judge/)

## 题目描述
在一个小镇里，有 n 个人标记为 1 到 n。传言称，这些人中有一个是小镇上的法官。

如果小镇的法官真的存在，那么：
1. 小镇的法官不相信任何人。
2. 每个人（除了小镇法官外）都相信小镇的法官。
3. 只有一个人同时满足条件 1 和条件 2。

给定数组 trust，其中 trust[i] = [ai, bi] 表示标记为 ai 的人信任标记为 bi 的人。

如果小镇法官存在并且可以确定他的身份，请返回该法官的标记；否则，返回 -1。

示例：
```
输入：n = 2, trust = [[1,2]]
输出：2

输入：n = 3, trust = [[1,3],[2,3],[3,1]]
输出：-1
```

## 解法：入度和出度统计

### 思路
1. 法官不相信任何人 -> 出度为0
2. 所有其他人都信任法官 -> 入度为n-1
3. 统计每个人的入度和出度，找到符合条件的人

```java
class Solution {
    public int findJudge(int n, int[][] trust) {
        // 创建入度和出度数组
        int[] inDegree = new int[n + 1];
        int[] outDegree = new int[n + 1];
        
        // 统计入度和出度
        for (int[] relation : trust) {
            outDegree[relation[0]]++;  // a信任别人
            inDegree[relation[1]]++;   // b被信任
        }
        
        // 寻找符合条件的法官
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == n - 1 && outDegree[i] == 0) {
                return i;
            }
        }
        
        return -1;
    }
}
```

### 复杂度分析
- 时间复杂度：O(E)，其中E是trust数组的长度
- 空间复杂度：O(N)，需要两个长度为N的数组

## 优化解法：使用单个数组

### 思路
可以使用一个数组同时记录入度和出度的差值，因为我们关心的是一个人被信任的次数与信任他人的次数之差。

```java
class Solution {
    public int findJudge(int n, int[][] trust) {
        // count[i] 表示 i 的入度-出度
        int[] count = new int[n + 1];
        
        for (int[] relation : trust) {
            count[relation[0]]--;  // 信任他人，减1
            count[relation[1]]++;  // 被信任，加1
        }
        
        // 法官的count值应该是n-1（被n-1个人信任，自己不信任任何人）
        for (int i = 1; i <= n; i++) {
            if (count[i] == n - 1) {
                return i;
            }
        }
        
        return -1;
    }
}
```

### 复杂度分析
- 时间复杂度：O(E)
- 空间复杂度：O(N)

## 题目重点
1. 理解图的入度和出度概念
2. 将实际问题转化为图的性质
3. 优化空间使用

## 解题技巧

### 1. 图的表示方法
- 不需要完整的邻接矩阵/邻接表
- 只需要统计入度和出度
- 可以使用数组优化空间

### 2. 数据结构选择
```java
// 使用数组而不是Map
int[] count = new int[n + 1];

// 使用单个数组代替两个数组
// count[i] = 入度[i] - 出度[i]
```

### 3. 边界条件处理
```java
// 处理n=1的特殊情况
if (n == 1 && trust.length == 0) {
    return 1;
}
```

## 相关题目
- [1557. 可以到达所有点的最少点数目](https://leetcode.com/problems/minimum-number-of-vertices-to-reach-all-nodes/)
- [841. 钥匙和房间](https://leetcode.com/problems/keys-and-rooms/)
- [802. 找到最终的安全状态](https://leetcode.com/problems/find-eventual-safe-states/)

## 延伸思考

### 1. 入度和出度的应用
- 识别特殊节点
- 寻找图中的关键点
- 判断图的特性

### 2. 图的表示优化
- 根据问题特点选择合适的表示方法
- 使用最简单的数据结构
- 考虑空间和时间的平衡

### 3. 实际应用场景
- 社交网络中的意见领袖识别
- 信任关系网络分析
- 依赖关系判断

## 面试技巧

### 1. 解题步骤
1. 分析问题特点
2. 选择合适的数据结构
3. 实现基本解法
4. 考虑优化方案

### 2. 代码书写
- 变量命名有意义
- 注释关键步骤
- 处理边界情况

### 3. 优化讨论
- 时间复杂度优化
- 空间复杂度优化
- 代码简洁性
