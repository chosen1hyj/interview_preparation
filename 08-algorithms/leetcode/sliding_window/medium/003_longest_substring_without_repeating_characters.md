# 无重复字符的最长子串 (中等)

## 题目描述
给定一个字符串 s，请你找出其中不含有重复字符的 最长子串 的长度。

## 示例
输入: s = "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

## 解法
```java
public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) {
        return 0;
    }
    
    Map<Character, Integer> window = new HashMap<>();
    int left = 0, right = 0;
    int maxLen = 0;
    
    while (right < s.length()) {
        char c = s.charAt(right);
        right++;
        
        // 更新窗口内数据
        window.put(c, window.getOrDefault(c, 0) + 1);
        
        // 如果窗口中出现重复字符，移动左侧窗口
        while (window.get(c) > 1) {
            char d = s.charAt(left);
            left++;
            window.put(d, window.get(d) - 1);
        }
        
        // 更新最大长度
        maxLen = Math.max(maxLen, right - left);
    }
    
    return maxLen;
}
```

## 优化解法（使用数组代替HashMap）
```java
public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) {
        return 0;
    }
    
    int[] window = new int[128];
    int left = 0, right = 0;
    int maxLen = 0;
    
    while (right < s.length()) {
        char c = s.charAt(right);
        right++;
        window[c]++;
        
        while (window[c] > 1) {
            char d = s.charAt(left);
            left++;
            window[d]--;
        }
        
        maxLen = Math.max(maxLen, right - left);
    }
    
    return maxLen;
}
```

## 复杂度分析
- 时间复杂度: O(n)，其中n是字符串的长度
- 空间复杂度: O(k)，k是字符集的大小（使用数组实现时为128）

## 关键点
1. 滑动窗口技巧应用
2. 字符出现次数的统计方法
3. 窗口收缩的时机
4. 最大长度的更新策略
