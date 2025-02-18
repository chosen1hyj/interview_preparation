# 最小覆盖子串 (困难)

## 题目描述
给你一个字符串 s 和一个字符串 t ，请你找出 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""。

注意：
- 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
- 如果 s 中存在这样的子串，我们保证它是唯一的答案。

## 示例
输入：s = "ADOBECODEBANC", t = "ABC"
输出："BANC"
解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。

## 解法
```java
public String minWindow(String s, String t) {
    if (s == null || t == null || s.length() < t.length()) {
        return "";
    }
    
    // 记录所需字符及其数量
    Map<Character, Integer> need = new HashMap<>();
    Map<Character, Integer> window = new HashMap<>();
    for (char c : t.toCharArray()) {
        need.put(c, need.getOrDefault(c, 0) + 1);
    }
    
    int left = 0, right = 0;
    int valid = 0;  // 已满足条件的字符数
    int start = 0, len = Integer.MAX_VALUE;
    
    while (right < s.length()) {
        char c = s.charAt(right);
        right++;
        
        // 更新窗口内数据
        if (need.containsKey(c)) {
            window.put(c, window.getOrDefault(c, 0) + 1);
            if (window.get(c).equals(need.get(c))) {
                valid++;
            }
        }
        
        // 判断左侧窗口是否需要收缩
        while (valid == need.size()) {
            // 更新最小覆盖子串
            if (right - left < len) {
                start = left;
                len = right - left;
            }
            
            char d = s.charAt(left);
            left++;
            
            // 更新窗口内数据
            if (need.containsKey(d)) {
                if (window.get(d).equals(need.get(d))) {
                    valid--;
                }
                window.put(d, window.get(d) - 1);
            }
        }
    }
    
    return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
}
```

## 复杂度分析
- 时间复杂度: O(n)，其中n是字符串s的长度
- 空间复杂度: O(k)，k是字符集大小，在本题中不超过52（大小写字母）

## 关键点
1. 使用HashMap记录需要的字符及其数量
2. 使用滑动窗口维护当前窗口中的字符
3. 使用valid变量跟踪已满足条件的字符个数
4. 注意处理重复字符的情况
