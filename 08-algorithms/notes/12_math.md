# 数学问题基础知识

## 基本概念

### 1. 质数相关
```java
// 判断是否为质数
boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}

// 埃拉托色尼筛法
boolean[] sieveOfEratosthenes(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    
    for (int i = 2; i * i <= n; i++) {
        if (isPrime[i]) {
            for (int j = i * i; j <= n; j += i) {
                isPrime[j] = false;
            }
        }
    }
    return isPrime;
}
```

### 2. 最大公约数与最小公倍数
```java
// 欧几里得算法求最大公约数
int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
}

// 最小公倍数
int lcm(int a, int b) {
    return a / gcd(a, b) * b;
}
```

## 常见技巧

### 1. 快速幂
```java
// 快速幂计算
long quickPow(long base, long exp, long mod) {
    long res = 1;
    while (exp > 0) {
        if ((exp & 1) == 1) {
            res = res * base % mod;
        }
        base = base * base % mod;
        exp >>= 1;
    }
    return res;
}
```

### 2. 数位处理
```java
// 分离数字的各个位
List<Integer> getDigits(int num) {
    List<Integer> digits = new ArrayList<>();
    while (num > 0) {
        digits.add(num % 10);
        num /= 10;
    }
    Collections.reverse(digits);
    return digits;
}
```

### 3. 进制转换
```java
// 十进制转任意进制
String toBase(int num, int base) {
    StringBuilder sb = new StringBuilder();
    while (num > 0) {
        int digit = num % base;
        sb.append(digit < 10 ? (char)(digit + '0') : (char)(digit - 10 + 'A'));
        num /= base;
    }
    return sb.reverse().toString();
}
```

## 实战应用

### 1. 数论基础
- 同余定理
- 模运算性质
- 费马小定理

### 2. 组合计数
```java
// 计算组合数C(n,k)
long comb(int n, int k) {
    long res = 1;
    for (int i = 1; i <= k; i++) {
        res = res * (n - i + 1) / i;
    }
    return res;
}
```

### 3. 数字特性
```java
// 判断回文数
boolean isPalindrome(int x) {
    if (x < 0) return false;
    int origin = x, reverse = 0;
    while (x != 0) {
        reverse = reverse * 10 + x % 10;
        x /= 10;
    }
    return origin == reverse;
}
```

## 注意事项

### 1. 边界处理
- 零和负数特殊情况
- 整数溢出判断
- 精度误差控制

### 2. 性能优化
- 减少循环次数
- 缓存中间结果
- 使用位运算替代乘除

### 3. 常见陷阱
- 数据类型范围
- 取模运算顺序
- 浮点数精度
