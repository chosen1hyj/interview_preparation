# 1. 两数之和（Two Sum）

[题目链接](https://leetcode.com/problems/two-sum/)

## 题目描述
给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

你可以按任意顺序返回答案。

示例：
```
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
```

## 解法一：暴力法

### 思路
遍历每个元素 x，寻找数组中是否存在另一个元素 y = target - x。

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 遍历每个数字
        for (int i = 0; i < nums.length; i++) {
            // 寻找另一个数字
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[0];
    }
}
```

### 复杂度分析
- 时间复杂度：O(n²)
- 空间复杂度：O(1)

## 解法二：哈希表

### 思路
使用哈希表存储已经遍历过的数字及其下标。对于每个数字，检查target - nums[i]是否在哈希表中。

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 创建哈希表，存储数字和下标
        Map<Integer, Integer> map = new HashMap<>();
        
        // 遍历数组
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // 检查互补数字是否在哈希表中
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            // 将当前数字加入哈希表
            map.put(nums[i], i);
        }
        return new int[0];
    }
}
```

### 复杂度分析
- 时间复杂度：O(n)
- 空间复杂度：O(n)

## 题目重点
1. 需要返回的是下标而不是具体的数字
2. 同一个元素不能使用两次
3. 题目保证有且仅有一个解

## 解题技巧
1. 考虑使用哈希表将查找时间从O(n)降低到O(1)
2. 在遍历的同时查找，可以节省一次遍历
3. 注意处理重复元素的情况

## 相似题目
- [15. 三数之和](https://leetcode.com/problems/3sum/)
- [167. 两数之和 II - 输入有序数组](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
- [170. 两数之和 III - 数据结构设计](https://leetcode.com/problems/two-sum-iii-data-structure-design/)

## 易错点
1. 只使用一个循环是不够的，需要两个循环或使用哈希表
2. 返回结果应该是下标数组而不是具体数字
3. 注意数组边界的处理
