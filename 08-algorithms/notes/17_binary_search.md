# 二分查找基础知识

## 基本概念

### 1. 标准二分查找
```java
// 基本模板
public int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

### 2. 查找左边界
```java
public int searchLeftBound(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            // 不要立即返回，继续向左寻找
            if (mid == 0 || nums[mid - 1] != target) {
                return mid;
            }
            right = mid - 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

### 3. 查找右边界
```java
public int searchRightBound(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            // 不要立即返回，继续向右寻找
            if (mid == nums.length - 1 || nums[mid + 1] != target) {
                return mid;
            }
            left = mid + 1;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return -1;
}
```

## 常见变体

### 1. 寻找旋转点
```java
public int findRotateIndex(int[] nums) {
    int left = 0, right = nums.length - 1;
    
    // 如果数组没有旋转
    if (nums[left] < nums[right]) return 0;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (nums[mid] > nums[mid + 1]) return mid + 1;
        if (nums[mid - 1] > nums[mid]) return mid;
        
        if (nums[mid] > nums[0]) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return 0;
}
```

### 2. 二分答案
```java
// 在某个范围内二分查找答案
public int binarySearchAnswer(int[] nums, int target) {
    int left = getMinPossibleAnswer(nums);
    int right = getMaxPossibleAnswer(nums);
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (check(mid)) {
            // 找到一个可能的解
            right = mid - 1; // 或 left = mid + 1，取决于要找最大值还是最小值
        } else {
            left = mid + 1; // 或 right = mid - 1
        }
    }
    
    return left; // 或 right，取决于具体问题
}
```

## 注意事项

### 1. 边界条件
- 循环条件：left <= right vs left < right
- 中点计算：防止整数溢出
- 区间更新：+1 和 -1 的选择

### 2. 代码模板选择
- 标准二分查找：查找精确值
- 寻找边界：处理重复元素
- 二分答案：解决优化问题

### 3. 常见问题
- 数组为空或只有一个元素
- 目标值不存在
- 有重复元素
