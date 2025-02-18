# Java并发编程基础概念

## 进程与线程

### 进程（Process）
- 定义：程序的一次执行过程，是系统进行资源分配和调度的基本单位
- 特点：
  - 独立的内存空间
  - 拥有自己的资源
  - 相互隔离
  - 切换开销大

### 线程（Thread）
- 定义：进程的执行单元，是CPU调度的基本单位
- 特点：
  - 共享进程的内存空间
  - 创建和切换成本较低
  - 通信更简单
- 创建方式：
  ```java
  // 1. 继承Thread类
  public class MyThread extends Thread {
      @Override
      public void run() {
          // 线程执行代码
      }
  }

  // 2. 实现Runnable接口
  public class MyRunnable implements Runnable {
      @Override
      public void run() {
          // 线程执行代码
      }
  }

  // 3. 实现Callable接口（有返回值）
  public class MyCallable implements Callable<String> {
      @Override
      public String call() throws Exception {
          return "线程执行结果";
      }
  }
  ```

## 并发与并行

### 并发（Concurrency）
- 定义：多个任务交替执行
- 特点：
  - 单核CPU也可以实现
  - 通过时间片轮转实现
  - 存在上下文切换开销

### 并行（Parallelism）
- 定义：多个任务同时执行
- 特点：
  - 需要多核CPU支持
  - 真正的同时执行
  - 适合计算密集型任务

## 线程的生命周期

### 线程状态
1. **NEW**：新创建，尚未启动
2. **RUNNABLE**：可运行状态
   - Ready：等待CPU时间片
   - Running：正在执行
3. **BLOCKED**：阻塞状态
   - 等待获取锁
4. **WAITING**：等待状态
   - 无限期等待其他线程的特定操作
5. **TIMED_WAITING**：超时等待
   - 有限期等待其他线程的特定操作
6. **TERMINATED**：终止状态

### 状态转换
```java
Thread thread = new Thread(() -> {
    System.out.println("线程开始执行 - NEW -> RUNNABLE");
    try {
        Thread.sleep(1000); // RUNNABLE -> TIMED_WAITING
        System.out.println("线程从 TIMED_WAITING 恢复到 RUNNABLE");
    } catch (InterruptedException e) {
        System.out.println("线程被中断");
        e.printStackTrace();
    }
    synchronized (lock) {
        System.out.println("线程尝试获取锁 - 可能 RUNNABLE -> BLOCKED");
    }
    System.out.println("线程执行结束 - 进入 TERMINATED");
});

System.out.println("线程初始状态: " + thread.getState()); // NEW
thread.start(); // 启动线程
System.out.println("线程启动后状态: " + thread.getState()); // RUNNABLE
```

## 上下文切换

### 概念
- 定义：CPU从一个线程或进程切换到另一个线程或进程
- 过程：
  1. 保存当前线程上下文（程序计数器、寄存器等）
  2. 加载要切换的线程上下文
  3. 切换到新线程继续执行

### 影响因素
1. 时间片用完
2. 系统中断
3. I/O阻塞
4. 线程阻塞（等待锁、sleep等）

### 优化策略
1. 减少锁的持有时间
2. 使用合适的锁粒度
3. 采用无锁数据结构
4. 适当的线程数量

## 面试高频问题

1. **进程和线程的区别是什么？**
   - 进程是资源分配的最小单位，线程是CPU调度的最小单位
   - 进程间相互独立，线程共享进程的资源
   - 进程切换开销大，线程切换开销小
   - 进程间通信复杂，线程间通信相对简单

2. **并发和并行的区别是什么？**
   - 并发是多个任务交替执行，并行是多个任务同时执行
   - 并发可以在单核CPU上实现，并行需要多核CPU
   - 并发关注的是任务处理，并行关注的是任务分配

3. **线程有哪些状态？各个状态之间如何转换？**
   - 六个状态：NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED
   - 重点关注状态转换的触发条件和对应的方法调用

4. **如何理解上下文切换？**
   - 本质是CPU从一个线程切换到另一个线程
   - 涉及到线程状态的保存和恢复
   - 会带来性能开销
   - 需要通过合适的策略来优化

## 示例代码

```java
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程
        Thread thread = new Thread(() -> {
            System.out.println("线程开始执行");
            try {
                Thread.sleep(2000); // 线程进入TIMED_WAITING状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程执行结束");
        });

        // NEW状态
        System.out.println("线程状态：" + thread.getState());

        // 启动线程，进入RUNNABLE状态
        thread.start();
        System.out.println("线程状态：" + thread.getState());

        // 主线程睜眠1秒，让子线程有足够时间进入TIMED_WAITING状态
        Thread.sleep(1000);
        System.out.println("线程状态：" + thread.getState());

        // 等待线程结束
        thread.join();
        System.out.println("线程状态：" + thread.getState());
    }
}
```

## 最佳实践建议

1. **线程创建**
   - 优先使用线程池而不是直接创建线程
   - 根据任务类型选择合适的线程创建方式

2. **状态管理**
   - 避免使用Thread.stop()等过时方法
   - 正确处理中断信号
   - 合理使用线程同步机制

3. **性能优化**
   - 控制线程数量
   - 减少上下文切换
   - 使用合适的并发度

4. **调试与监控**
   - 使用jstack等工具查看线程状态
   - 监控线程池运行状况
   - 及时发现死锁等问题
