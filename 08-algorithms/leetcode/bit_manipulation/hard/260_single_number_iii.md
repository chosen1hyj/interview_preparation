# 只出现一次的数字 III (困难)

## 题目描述
给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。

要求：
你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？

## 示例
输入: [1,2,1,3,2,5]
输出: [3,5]

## 解法
```java
public int[] singleNumber(int[] nums) {
    // Step 1: 利用异或找出两个不同数的异或结果
    int diff = 0;
    for (int num : nums) {
        diff ^= num;
    }
    
    // Step 2: 找到区分两个数的第一个不同的位
    diff &= -diff; // 得到最右边的1
    
    // Step 3: 根据这个位将数字分为两组
    int[] result = new int[2];
    for (int num : nums) {
        if ((num & diff) == 0) {
            result[0] ^= num;
        } else {
            result[1] ^= num;
        }
    }
    
    return result;
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(1)

## 关键点
- 异或运算性质
- 分组策略
- 位操作技巧
