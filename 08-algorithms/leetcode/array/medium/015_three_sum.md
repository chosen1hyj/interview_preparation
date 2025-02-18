# 15. 三数之和（3Sum）

[题目链接](https://leetcode.com/problems/3sum/)

## 题目描述
给你一个整数数组 nums ，判断是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。

注意：答案中不可以包含重复的三元组。

示例：
```
输入：nums = [-1,0,1,2,-1,-4]
输出：[[-1,-1,2],[-1,0,1]]
解释：
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0
```

## 解法：排序 + 双指针

### 思路
1. 首先将数组排序
2. 固定一个数，然后用双指针在剩余部分寻找两个数
3. 注意去重处理

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        // 数组长度小于3，无法构成三元组
        if (nums == null || nums.length < 3) {
            return result;
        }
        
        // 排序
        Arrays.sort(nums);
        
        // 固定第一个数
        for (int i = 0; i < nums.length - 2; i++) {
            // 去重：如果当前数字和前一个数字相同，跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // 双指针
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    // 找到一组解
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 去重：跳过重复的数字
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    // 和小于0，左指针右移
                    left++;
                } else {
                    // 和大于0，右指针左移
                    right--;
                }
            }
        }
        
        return result;
    }
}
```

### 复杂度分析
- 时间复杂度：O(n²)
  - 排序需要O(nlogn)
  - 双指针遍历需要O(n²)
- 空间复杂度：O(1)（不考虑返回值所占空间）

## 题目重点
1. 需要处理重复元素
2. 返回的是具体数值而不是下标
3. 可能存在多组解

## 解题技巧
1. **排序的作用**
   - 方便去重
   - 利用有序性使用双指针
   - 优化搜索过程

2. **去重处理**
   - 对第一个数去重
   - 对左指针去重
   - 对右指针去重

3. **优化方向**
   - 特判：数组长度小于3直接返回
   - 当nums[i] > 0时可以直接break
   - 利用有序性剪枝

## 常见变形
- 三数最近和
- 四数之和
- K数之和

## 相似题目
- [1. 两数之和](https://leetcode.com/problems/two-sum/)
- [16. 最接近的三数之和](https://leetcode.com/problems/3sum-closest/)
- [18. 四数之和](https://leetcode.com/problems/4sum/)

## 易错点
1. 漏掉去重的情况
2. 指针移动时的边界处理
3. 优化条件的判断

## 补充知识：双指针技巧

### 1. 什么情况适合使用双指针
- 有序数组中寻找和为定值的元素
- 原地修改数组
- 滑动窗口问题

### 2. 常见双指针类型
1. **对撞指针**
```java
int left = 0, right = array.length - 1;
while (left < right) {
    // 根据条件移动左右指针
}
```

2. **快慢指针**
```java
int slow = 0, fast = 0;
while (fast < array.length) {
    // 根据条件移动快慢指针
}
```

### 3. 双指针的优化
- 合理的初始化位置
- 正确的移动策略
- 适当的终止条件
- 去重的处理方法

### 4. 使用双指针的注意事项
- 指针的边界条件
- 指针的移动步长
- 结果的去重处理
- 特殊情况的处理
