# 55. 跳跃游戏（Jump Game）

[题目链接](https://leetcode.com/problems/jump-game/)

## 题目描述
给定一个非负整数数组 nums，你最初位于数组的第一个下标。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标。

示例：
```
输入：nums = [2,3,1,1,4]
输出：true
解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。

输入：nums = [3,2,1,0,4]
输出：false
解释：无论如何，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
```

## 解法一：贪心算法（正向）

### 思路
维护一个变量maxReach，表示能够到达的最远位置。遍历数组时，不断更新maxReach，如果maxReach大于等于最后一个位置，说明可以到达。

```java
class Solution {
    public boolean canJump(int[] nums) {
        int maxReach = 0;  // 能到达的最远位置
        
        for (int i = 0; i <= maxReach && i < nums.length; i++) {
            // 更新最远可达位置
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // 如果可以到达最后位置
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        return false;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)，只需要遍历一次数组
- 空间复杂度：O(1)，只使用常数额外空间

## 解法二：贪心算法（反向）

### 思路
从后往前遍历，维护一个变量lastPos表示需要到达的位置。如果当前位置可以到达lastPos，则更新lastPos为当前位置。

```java
class Solution {
    public boolean canJump(int[] nums) {
        int lastPos = nums.length - 1;  // 需要到达的位置
        
        // 从后往前遍历
        for (int i = nums.length - 2; i >= 0; i--) {
            // 如果当前位置可以到达lastPos
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        
        // lastPos为0说明可以从起点到达终点
        return lastPos == 0;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(1)

## 题目重点
1. 贪心策略的选择
2. 最远可达位置的维护
3. 正向和反向遍历的区别
4. 边界条件的处理

## 解题技巧

### 1. 贪心策略
```java
// 每次都尽可能跳得更远
maxReach = Math.max(maxReach, i + nums[i]);
```

### 2. 提前返回
```java
// 如果已经可以到达最后位置，直接返回true
if (maxReach >= nums.length - 1) {
    return true;
}
```

### 3. 剪枝条件
```java
// 如果当前位置已经无法到达，后面的位置也无法到达
if (i > maxReach) {
    return false;
}
```

## 相关题目
- [45. 跳跃游戏 II](https://leetcode.com/problems/jump-game-ii/)
- [1306. 跳跃游戏 III](https://leetcode.com/problems/jump-game-iii/)
- [1345. 跳跃游戏 IV](https://leetcode.com/problems/jump-game-iv/)

## 延伸思考

### 1. 变种问题
- 如何求最少跳跃次数？
- 如果允许向左跳跃？
- 如果有障碍物？

### 2. 优化方向
- 提前终止条件
- 特殊情况处理
- 空间使用优化

### 3. 实际应用
- 网络路由跳转
- 游戏关卡设计
- 资源调度问题

## 面试技巧

### 1. 解题步骤
1. 分析能否使用贪心
2. 确定贪心策略
3. 处理边界情况
4. 考虑优化空间

### 2. 代码实现
- 变量命名清晰
- 注释说明完整
- 结构简洁明了
- 边界处理完善

### 3. 复杂度分析
- 时间复杂度说明
- 空间使用分析
- 优化可能性

## 常见错误

### 1. 边界条件
```java
// 错误：忘记处理数组长度为1的情况
if (nums.length == 1) {
    return true;
}
```

### 2. 遍历条件
```java
// 错误：遍历条件不完整
for (int i = 0; i < nums.length; i++)  // 应该加上 i <= maxReach
```

### 3. 更新逻辑
```java
// 错误：更新最远距离的逻辑错误
maxReach = i + nums[i];  // 应该用Math.max比较
```

## 总结

### 1. 贪心策略
- 维护最远可达位置
- 正向遍历更新状态
- 提前返回优化
- 边界情况处理

### 2. 代码优化
- 简化判断条件
- 优化遍历过程
- 提高代码可读性
- 完善错误处理

### 3. 解题思路
- 贪心策略证明
- 动态规划对比
- 双向遍历思考
- 特殊情况分析

### 4. 技能提升
- 贪心算法应用
- 问题分析能力
- 代码实现能力
- 优化思维培养
