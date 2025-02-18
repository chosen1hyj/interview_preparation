# 字符串的排列 (中等)

## 题目描述
给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false。

换句话说，s1 的排列之一是 s2 的 子串。

## 示例
输入：s1 = "ab", s2 = "eidbaooo"
输出：true
解释：s2 包含 s1 的排列之一 ("ba").

## 解法
```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) {
        return false;
    }
    
    // 记录需要的字符数量
    int[] need = new int[26];
    // 记录窗口中的字符数量
    int[] window = new int[26];
    
    // 初始化need数组
    for (char c : s1.toCharArray()) {
        need[c - 'a']++;
    }
    
    int left = 0, right = 0;
    
    while (right < s2.length()) {
        char c = s2.charAt(right);
        right++;
        
        // 更新窗口数据
        window[c - 'a']++;
        
        // 当窗口长度大于s1长度时，需要缩小窗口
        while (right - left >= s1.length()) {
            // 当窗口长度等于s1长度时，判断是否找到了排列
            if (right - left == s1.length() && matches(window, need)) {
                return true;
            }
            
            char d = s2.charAt(left);
            left++;
            window[d - 'a']--;
        }
    }
    
    return false;
}

private boolean matches(int[] window, int[] need) {
    for (int i = 0; i < 26; i++) {
        if (window[i] != need[i]) {
            return false;
        }
    }
    return true;
}
```

## 优化解法（使用计数器）
```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) {
        return false;
    }
    
    int[] count = new int[26];
    int diff = 0;  // 记录need和window中不同字符的个数
    
    // 统计s1中字符
    for (char c : s1.toCharArray()) {
        count[c - 'a']++;
        if (count[c - 'a'] == 1) {
            diff++;
        }
    }
    
    // 初始窗口
    for (int i = 0; i < s1.length(); i++) {
        char c = s2.charAt(i);
        count[c - 'a']--;
        if (count[c - 'a'] == 0) {
            diff--;
        } else if (count[c - 'a'] == -1) {
            diff++;
        }
    }
    
    if (diff == 0) return true;
    
    // 滑动窗口
    for (int i = s1.length(); i < s2.length(); i++) {
        char c = s2.charAt(i);
        char d = s2.charAt(i - s1.length());
        
        // 加入新字符
        count[c - 'a']--;
        if (count[c - 'a'] == 0) {
            diff--;
        } else if (count[c - 'a'] == -1) {
            diff++;
        }
        
        // 移除旧字符
        count[d - 'a']++;
        if (count[d - 'a'] == 0) {
            diff--;
        } else if (count[d - 'a'] == 1) {
            diff++;
        }
        
        if (diff == 0) return true;
    }
    
    return false;
}
```

## 复杂度分析
- 时间复杂度: O(n)，其中n是字符串s2的长度
- 空间复杂度: O(1)，使用固定大小的数组

## 关键点
1. 使用固定大小的滑动窗口
2. 字符频率的比较方法
3. 优化比较过程
4. 使用diff变量减少比较次数
