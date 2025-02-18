# 整数反转 (中等)

## 题目描述
给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。

如果反转后整数超过 32 位的有符号整数的范围 [−2^31,  2^31 − 1] ，就返回 0。

假设环境不允许存储 64 位整数（有符号或无符号）。

## 示例
```
输入：x = 123
输出：321

输入：x = -123
输出：-321

输入：x = 120
输出：21
```

## 解法
```java
public int reverse(int x) {
    int result = 0;
    while (x != 0) {
        int digit = x % 10;
        x /= 10;
        
        // 检查溢出
        if (result > Integer.MAX_VALUE / 10 || 
            (result == Integer.MAX_VALUE / 10 && digit > 7)) {
            return 0;
        }
        if (result < Integer.MIN_VALUE / 10 || 
            (result == Integer.MIN_VALUE / 10 && digit < -8)) {
            return 0;
        }
        
        result = result * 10 + digit;
    }
    return result;
}
```

## 复杂度分析
- 时间复杂度: O(log|x|)
- 空间复杂度: O(1)

## 关键点
1. 整数溢出处理
2. 数字逐位处理
3. 边界条件判断

## 进阶思考
1. 如何处理更大的整数？
2. 能否使用字符串来解决？
3. 考虑负数的特殊情况
