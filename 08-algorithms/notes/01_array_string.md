# 数组与字符串基础知识

## 数组基础

### 1. 数组的特点
- 连续的内存空间
- 固定的大小
- O(1)时间复杂度的随机访问
- 插入和删除需要移动元素

### 2. 数组操作的时间复杂度
- 访问：O(1)
- 搜索：O(n)
- 插入：O(n)
- 删除：O(n)

### 3. 常见数组技巧

#### 双指针技巧
```java
// 1. 快慢指针（同向移动）
public void fastSlowPointer(int[] nums) {
    int slow = 0;
    for (int fast = 0; fast < nums.length; fast++) {
        // 根据条件移动慢指针
    }
}

// 2. 左右指针（相向移动）
public void twoPointers(int[] nums) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        // 根据条件移动左右指针
    }
}
```

#### 滑动窗口
```java
public void slidingWindow(int[] nums) {
    int left = 0, right = 0;
    while (right < nums.length) {
        // 扩大窗口
        right++;
        
        while (/* 窗口需要收缩 */) {
            // 缩小窗口
            left++;
        }
    }
}
```

#### 前缀和
```java
// 构建前缀和数组
public int[] buildPrefixSum(int[] nums) {
    int[] prefixSum = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
        prefixSum[i + 1] = prefixSum[i] + nums[i];
    }
    return prefixSum;
}
```

### 4. 常见数组问题类型

1. **查找问题**
   - 二分查找
   - 查找特定元素
   - 查找重复元素

2. **排序问题**
   - 基本排序算法
   - 特殊排序要求
   - 部分排序

3. **子数组问题**
   - 最大子数组和
   - 连续子数组
   - 子数组统计

4. **区间问题**
   - 区间合并
   - 区间交集
   - 区间统计

## 字符串基础

### 1. Java中的字符串
- 不可变性
- String vs StringBuilder vs StringBuffer
- 字符串池

### 2. 常见字符串操作
```java
// 1. 反转字符串
public void reverse(char[] s) {
    int left = 0, right = s.length - 1;
    while (left < right) {
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        left++;
        right--;
    }
}

// 2. 判断回文
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```

### 3. 常见字符串算法

#### KMP算法
```java
// 构建部分匹配表
public int[] buildNext(String pattern) {
    int[] next = new int[pattern.length()];
    int j = 0;
    for (int i = 1; i < pattern.length(); i++) {
        while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
            j = next[j - 1];
        }
        if (pattern.charAt(i) == pattern.charAt(j)) {
            j++;
        }
        next[i] = j;
    }
    return next;
}
```

### 4. 常见字符串问题类型

1. **字符统计类**
   - 字符频率统计
   - 异位词问题
   - 字符串排列

2. **子串问题**
   - 最长公共子串
   - 最长回文子串
   - 子串查找

3. **编辑距离类**
   - 一次编辑距离
   - 最小编辑距离
   - 字符串转换

4. **模式匹配**
   - 通配符匹配
   - 正则表达式
   - 字符串搜索

## 面试要点

### 1. 常见优化方向
- 使用额外空间换取时间效率
- 原地修改数组
- 使用排序简化问题
- 双指针/滑动窗口优化

### 2. 解题技巧
- 画图帮助理解
- 考虑边界情况
- 寻找问题的对称性
- 利用数学性质

### 3. 注意事项
- 数组越界检查
- 空字符串处理
- 大小写敏感性
- 特殊字符处理

### 4. 常见陷阱
- 忽略边界条件
- 未考虑空值情况
- 字符编码问题
- 整数溢出问题

## 实战准备建议

1. **基础练习顺序**
   - 基本数组操作
   - 双指针技巧
   - 滑动窗口
   - 子数组问题
   - 字符串基本操作
   - 子串问题

2. **进阶练习方向**
   - 区间问题
   - KMP算法
   - 字符串高级算法
   - 复杂的子串问题

3. **刷题策略**
   - 从简单题开始
   - 同类型题目集中练习
   - 注重复习和总结
   - 关注题目的多种解法
