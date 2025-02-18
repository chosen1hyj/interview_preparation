# 50. Pow(x, n)

[题目链接](https://leetcode.com/problems/powx-n/)

## 题目描述
实现 pow(x, n)，即计算 x 的 n 次幂。

示例：
```
输入：x = 2.00000, n = 10
输出：1024.00000

输入：x = 2.10000, n = 3
输出：9.26100

输入：x = 2.00000, n = -2
输出：0.25000
解释：2^-2 = 1/2^2 = 1/4 = 0.25
```

## 解法：快速幂（分治思想）

### 思路
1. 使用分治思想将问题规模减半
2. 处理正负指数
3. 递归计算结果

```java
class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        return fastPow(x, n);
    }
    
    private double fastPow(double x, int n) {
        if (n == 0) return 1.0;
        
        double half = fastPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }
}
```

### 复杂度分析
- 时间复杂度：O(logn)
- 空间复杂度：O(logn)

## 解题技巧

### 1. 快速幂核心
```java
// 递归分解
double half = fastPow(x, n / 2);
if (n % 2 == 0) {
    return half * half;
} else {
    return half * half * x;
}
```

### 2. 负指数处理
```java
// 处理负指数
if (n < 0) {
    x = 1 / x;
    n = -n;
}
```

## 相关题目
- [69. x 的平方根](https://leetcode.com/problems/sqrtx/)
- [372. 超级次方](https://leetcode.com/problems/super-pow/)
- [343. 整数拆分](https://leetcode.com/problems/integer-break/)

## 延伸思考

### 1. 变种问题
- 大数取模？
- 浮点数精度？
- 边界情况？

### 2. 优化方向
- 迭代实现
- 位运算优化
- 缓存中间结果

### 3. 实际应用
- 科学计算
- 图形处理
- 金融计算
- 密码学

## 面试技巧

### 1. 解题步骤
1. 确定递归定义
2. 设计基准情况
3. 实现分解逻辑
4. 完成合并操作

### 2. 代码实现
- 清晰的递归结构
- 正确的边界处理
- 高效的计算过程
- 完善的特殊情况处理

### 3. 复杂度分析
- 时间复杂度证明
- 空间使用说明
- 优化可能性

## 常见错误

### 1. 边界条件
```java
// 错误：忘记处理负指数
public double myPow(double x, int n) {
    // 缺少负指数处理
}
```

### 2. 溢出处理
```java
// 错误：整数溢出
int n = -n; // 当n为Integer.MIN_VALUE时会溢出
// 应该使用long类型处理
```

### 3. 精度问题
```java
// 错误：浮点数比较
if (result == expected) return true;
// 应该使用误差范围判断
if (Math.abs(result - expected) < 1e-6) return true;
```

## 总结

### 1. 分治要点
- 明确递归定义
- 正确划分问题
- 高效合并结果
- 处理边界情况

### 2. 优化方向
- 迭代实现
- 小数据优化
- 并行计算
- 空间优化

### 3. 技能提升
- 递归思维
- 分治意识
- 优化能力
- 代码实现
