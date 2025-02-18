# Count Primes (Easy)

## Problem Description
Count the number of prime numbers less than a non-negative number, n.

## Example
Input: 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.

## Solution
```java
// 埃拉托色尼筛法
public int countPrimes(int n) {
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    
    int count = 0;
    for (int i = 2; i < n; i++) {
        if (isPrime[i]) {
            count++;
            for (int j = 2; i * j < n; j++) {
                isPrime[i * j] = false;
            }
        }
    }
    
    return count;
}
```

## Complexity Analysis
- Time complexity: O(nloglogn)
- Space complexity: O(n)

## Key Points
- Prime number definition
- Sieve of Eratosthenes implementation
- Optimization techniques
