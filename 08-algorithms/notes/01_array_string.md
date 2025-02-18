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

## 字符串处理

### 基本操作
- 子串查找：KMP算法、Boyer-Moore算法
- 字符串匹配：正则表达式
- 字符串转换：大小写转换、数字与字符串互转

### 典型例题

#### LeetCode 5 - 最长回文子串 (中等)
- 中心扩展法
- 动态规划
- Manacher算法

```java
// 中心扩展法实现
public String longestPalindrome(String s) {
    if (s == null || s.length() < 1) return "";
    int start = 0, end = 0;
    for (int i = 0; i < s.length(); i++) {
        int len1 = expandAroundCenter(s, i, i);
        int len2 = expandAroundCenter(s, i, i + 1);
        int len = Math.max(len1, len2);
        if (len > end - start) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
    return s.substring(start, end + 1);
}

private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

#### LeetCode 10 - 正则表达式匹配 (困难)
- 动态规划
- 回溯法

```java
// 动态规划实现
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m+1][n+1];
    dp[0][0] = true;
    
    // 初始化首行
    for (int j = 2; j <= n; j++) {
        dp[0][j] = p.charAt(j-1) == '*' && dp[0][j-2];
    }
    
    // 填充表格
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char sc = s.charAt(i-1), pc = p.charAt(j-1);
            if (pc == '.' || pc == sc) {
                dp[i][j] = dp[i-1][j-1];
            } else if (pc == '*') {
                char prev = p.charAt(j-2);
                boolean zero = dp[i][j-2]; // 匹配0个
                boolean oneOrMore = (prev == '.' || prev == sc) && dp[i-1][j];
                dp[i][j] = zero || oneOrMore;
            }
        }
    }
    return dp[m][n];
}
```

### 解题模板

#### KMP算法模板
```java
public int[] computeLPS(String pattern) {
    int n = pattern.length();
    int[] lps = new int[n];
    int len = 0;
    int i = 1;
    while (i < n) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len-1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps;
}

public int kmpSearch(String text, String pattern) {
    int[] lps = computeLPS(pattern);

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
### 典型例题

#### LeetCode 1 - 两数之和 (简单)
- 哈希表法
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    throw new IllegalArgumentException("No two sum solution");
}
```

#### LeetCode 15 - 三数之和 (中等)
- 排序+双指针
```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length-2; i++) {
        // 跳过重复值
        if (i > 0 && nums[i] == nums[i-1]) continue;
        int left = i+1, right = nums.length-1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left+1]) left++;
                while (left < right && nums[right] == nums[right-1]) right--;
                left++;
                right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    return result;
}
```

#### LeetCode 4 - 寻找两个正序数组的中位数 (困难)
- 二分查找
```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) {
        return findMedianSortedArrays(nums2, nums1);
    }
    int m = nums1.length;
    int n = nums2.length;
    int halfLen = (m + n + 1) / 2;
    int iMin = 0, iMax = m;
    while (iMin <= iMax) {
        int i = (iMin + iMax) / 2;
        int j = halfLen - i;
        if (i < iMax && nums2[j-1] > nums1[i]){
            iMin = i + 1;
        } else if (i > iMin && nums1[i-1] > nums2[j]) {
            iMax = i - 1;
        } else {
            int maxLeft = 0;
            if (i == 0) { maxLeft = nums2[j-1]; }
            else if (j == 0) { maxLeft = nums1[i-1]; }
            else { maxLeft = Math.max(nums1[i-1], nums2[j-1]); }
            if ( (m + n) % 2 == 1 ) { return maxLeft; }

            int minRight = 0;
            if (i == m) { minRight = nums2[j]; }
            else if (j == n) { minRight = nums1[i]; }
            else { minRight = Math.min(nums1[i], nums2[j]); }

            return (maxLeft + minRight) / 2.0;
        }
    }
    throw new IllegalArgumentException();
}
```

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
