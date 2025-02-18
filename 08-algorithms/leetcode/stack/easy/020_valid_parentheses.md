# 有效的括号 (简单)

## 题目描述
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

有效字符串需满足：
1. 左括号必须用相同类型的右括号闭合。
2. 左括号必须以正确的顺序闭合。

## 示例
输入: s = "()[]{}"
输出: true

## 解法
```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(') {
            stack.push(')');
        } else if (c == '{') {
            stack.push('}');
        } else if (c == '[') {
            stack.push(']');
        } else {
            if (stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}
```

## 复杂度分析
- 时间复杂度: O(n)
- 空间复杂度: O(n)

## 关键点
- 栈的使用
- 括号匹配规则
- 边界情况处理
