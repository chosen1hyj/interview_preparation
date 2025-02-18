# 字符串相乘 (困难)

## 题目描述
给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也必须用字符串形式表示。

注意：不能使用任何内置的大数库或直接将输入转换为整数。

## 示例
```
输入: num1 = "2", num2 = "3"
输出: "6"

输入: num1 = "123", num2 = "456"
输出: "56088"
```

## 解法
```java
public String multiply(String num1, String num2) {
    if (num1.equals("0") || num2.equals("0")) return "0";
    
    int m = num1.length(), n = num2.length();
    int[] result = new int[m + n];
    
    for(int i = m - 1; i >= 0; i--){
        for(int j = n - 1; j >= 0; j--){
            int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            int p1 = i + j, p2 = i + j + 1;
            int sum = mul + result[p2];
            
            result[p1] += sum / 10;
            result[p2] = sum % 10;
        }
    }
    
    StringBuilder sb = new StringBuilder();
    for(int num : result){
        if(!(sb.length() == 0 && num == 0)){
            sb.append(num);
        }
    }
    
    return sb.toString();
}
```

## 复杂度分析
- 时间复杂度: O(mn)
- 空间复杂度: O(m+n)

## 关键点
1. 数字逐位相乘
2. 结果位置计算
3. 进位处理

## 进阶思考
1. 如何优化大数相乘？
2. 考虑 Karatsuba算法
3. 边界条件处理
