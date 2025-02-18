# 滑动窗口基础知识

## 基本概念

### 1. 标准滑动窗口模板
```java
public String slidingWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>();
    Map<Character, Integer> window = new HashMap<>();
    
    // 初始化need哈希表
    for (char c : t.toCharArray()) {
        need.put(c, need.getOrDefault(c, 0) + 1);
    }
    
    int left = 0, right = 0;
    int valid = 0;
    
    while (right < s.length()) {
        // 扩大窗口
        char c = s.charAt(right);
        right++;
        // 进行窗口内数据的更新
        
        // 判断左侧窗口是否要收缩
        while (window needs shrink) {
            char d = s.charAt(left);
            left++;
            // 进行窗口内数据的更新
        }
    }
}
```

### 2. 应用场景
- 字符串匹配
- 子数组问题
- 子串问题

### 3. 优化技巧
```java
// 使用数组代替HashMap（当字符集较小时）
int[] need = new int[128];
int[] window = new int[128];

// 使用计数器优化valid判断
int count = 0;
```

## 常见变体

### 1. 固定窗口大小
```java
public int fixedWindow(int[] nums, int k) {
    int left = 0, right = 0;
    int sum = 0;
    int maxSum = Integer.MIN_VALUE;
    
    while (right < nums.length) {
        sum += nums[right];
        right++;
        
        if (right - left == k) {
            maxSum = Math.max(maxSum, sum);
            sum -= nums[left];
            left++;
        }
    }
    
    return maxSum;
}
```

### 2. 可变窗口大小
```java
public int variableWindow(int[] nums, int target) {
    int left = 0, right = 0;
    int sum = 0;
    int minLen = Integer.MAX_VALUE;
    
    while (right < nums.length) {
        sum += nums[right];
        right++;
        
        while (sum >= target) {
            minLen = Math.min(minLen, right - left);
            sum -= nums[left];
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

## 注意事项

### 1. 窗口更新
- 扩大窗口时机
- 缩小窗口时机
- 更新结果的位置

### 2. 边界条件
- 空串处理
- 窗口大小限制
- 结果不存在的情况

### 3. 常见问题
- 何时更新答案
- 如何维护窗口内的数据结构
- 什么时候需要扩大/缩小窗口
