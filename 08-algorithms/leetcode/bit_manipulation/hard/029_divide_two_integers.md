# 两数相除 (困难)

## 题目描述
给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。

返回被除数 dividend 除以除数 divisor 得到的商。

整数除法的结果应当截去其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2

## 示例
```
输入: dividend = 10, divisor = 3
输出: 3
解释: 10/3 = truncate(3.33333..) = 3

输入: dividend = 7, divisor = -3
输出: -2
解释: 7/-3 = truncate(-2.33333..) = -2
```

## 解法
```java
public int divide(int dividend, int divisor) {
    if(dividend == Integer.MIN_VALUE && divisor == -1){
        return Integer.MAX_VALUE;
    }
    
    boolean negative = (dividend < 0) ^ (divisor < 0);
    long dvd = Math.abs((long)dividend);
    long dvs = Math.abs((long)divisor);
    int result = 0;
    
    while(dvd >= dvs){
        long temp = dvs, count = 1;
        while(dvd >= (temp << 1)){
            temp <<= 1;
            count <<= 1;
        }
        dvd -= temp;
        result += count;
    }
    
    return negative ? -result : result;
}
```

## 复杂度分析
- 时间复杂度: O(log^2(dividend))
- 空间复杂度: O(1)

## 关键点
1. 位运算代替乘除
2. 溢出处理
3. 边界条件判断

## 进阶思考
1. 如何处理更大范围的整数？
2. 能否进一步优化性能？
3. 考虑负数的特殊情况
