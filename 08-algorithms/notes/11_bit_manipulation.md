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

## 实战应用

### 1. 位掩码
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

## 注意事项

### 1. 边界处理
- 负数处理
- 溢出判断
- 符号位影响

### 2. 性能优化
- 替代乘除运算
- 减少循环次数
- 空间节省

### 3. 常见陷阱
- 运算优先级
- 符号扩展
- 位数限制
