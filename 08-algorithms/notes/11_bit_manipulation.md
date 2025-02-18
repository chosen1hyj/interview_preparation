# 位运算基础知识

## 基本概念

### 1. 位运算符
- & (AND) 按位与
- | (OR) 按位或
- ^ (XOR) 按位异或
- ~ (NOT) 按位取反
- << 左移
- >> 右移
- >>> 无符号右移

### 2. 常见位操作
```java
// 获取第i位
int getBit(int num, int i) {
    return (num & (1 << i)) != 0 ? 1 : 0;
}

// 设置第i位为1
int setBit(int num, int i) {
    return num | (1 << i);
}

// 清除第i位
int clearBit(int num, int i) {
    int mask = ~(1 << i);
    return num & mask;
}
```

## 常见技巧

### 1. 判断奇偶性
```java
// 判断是否为奇数
boolean isOdd(int num) {
    return (num & 1) == 1;
}
```

### 2. 交换两个数
```java
// 不使用临时变量交换
a ^= b;
b ^= a;
a ^= b;
```

### 3. 计算二进制中1的个数
```java
int countOnes(int num) {
    int count = 0;
    while (num != 0) {
        num &= (num - 1);
        count++;
    }
    return count;
}
```

## 高级应用

### 1. 算法优化
- 使用位运算代替乘除
- 状态压缩DP
- Bitset优化

### 2. 位掩码
```java
// 使用位掩码表示集合
int[] masks = new int[1 << n];
for (int i = 0; i < (1 << n); i++) {
    // i 的二进制表示子集
}

// 应用示例：统计子集数量
int countSubsets(int[] nums) {
    int n = nums.length;
    int total = 1 << n; // 2^n个子集
    return total;
}
```

### 3. 状态压缩
```java
// 使用位掩码表示集合
int[] masks = new int[1 << n];
for (int i = 0; i < (1 << n); i++) {
    // i 的二进制表示子集
}
```

### 2. 状态压缩
```java
// 使用整数表示状态
int state = 0;
state |= (1 << index); // 添加元素
state &= ~(1 << index); // 移除元素
```

### 3. 高效计算
```java
// 快速乘法
int multiplyByPowerOfTwo(int num, int power) {
    return num << power;
}

// 快速除法
int divideByPowerOfTwo(int num, int power) {
    return num >> power;
}
```

## 常见面试题型

### 1. 数字操作
- 计算二进制中1的个数
- 判断是否为2的幂
- 找出唯一出现的数字

### 2. 算法优化
- 不使用加减乘除实现计算器
- 位运算分治算法
- 状态压缩动态规划

## 注意事项

### 1. 边界条件
- Integer.MIN_VALUE 特殊处理
- 负数右移补位问题
- 溢出判断

### 2. 性能优化
- 替代乘除运算
- 减少循环次数
- 空间节省

### 3. 常见陷阱
- 运算优先级
- 符号扩展
- 位数限制
