# 颜色分类 (中等)

## 题目描述
给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

此题中，我们使用整数 0、1 和 2 分别表示红色、白色和蓝色。

注意：不能使用代码库中的排序函数来解决这道题。

## 示例
输入：nums = [2,0,2,1,1,0]
输出：[0,0,1,1,2,2]

## 解法一：双指针（一次遍历）
```java
public void sortColors(int[] nums) {
    // p0: 0的右边界，p2: 2的左边界
    int p0 = 0, curr = 0;
    int p2 = nums.length - 1;
    
    while (curr <= p2) {
        if (nums[curr] == 0) {
            swap(nums, curr, p0);
            p0++;
            curr++;
        } else if (nums[curr] == 2) {
            swap(nums, curr, p2);
            p2--;
        } else {
            curr++;
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

## 解法二：计数排序
```java
public void sortColors(int[] nums) {
    // 计数
    int[] count = new int[3];
    for (int num : nums) {
        count[num]++;
    }
    
    // 重写数组
    int index = 0;
    for (int i = 0; i < 3; i++) {
        while (count[i] > 0) {
            nums[index++] = i;
            count[i]--;
        }
    }
}
```

## 复杂度分析
- 解法一（双指针）：
  - 时间复杂度：O(n)
  - 空间复杂度：O(1)
- 解法二（计数排序）：
  - 时间复杂度：O(n)
  - 空间复杂度：O(1)，因为是固定大小的数组

## 关键点
1. 荷兰国旗问题的解决方案
2. 双指针的应用技巧
3. 一次遍历完成排序
4. 原地算法的实现

## 进阶思考
1. 如果有k种颜色怎么处理？
2. 如何保证排序算法的稳定性？
3. 能否用其他方法优化空间复杂度？
