# 逆波兰表达式求值 (中等)

## 题目描述
根据 逆波兰表示法，求表达式的值。

有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。

注意：两个整数除法只保留整数部分。给定逆波兰表达式总是有效的。

## 示例
输入: tokens = ["2","1","+","3","*"]
输出: 9
解释: 该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9

## 解法
```java
public int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();
    
    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack.pop();
            int a = stack.pop();
            stack.push(calculate(a, b, token));
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    
    return stack.pop();
}

private boolean isOperator(String token) {
    return token.equals("+") || token.equals("-") || 
           token.equals("*") || token.equals("/");
}

private int calculate(int a, int b, String operator) {
    switch(operator) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/": return a / b;
        default: throw new IllegalArgumentException();
    }
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(n)

## 关键点
- 栈的应用
- 运算符处理
- 边界情况判断
