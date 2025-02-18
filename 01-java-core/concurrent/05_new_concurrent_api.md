# 新版并发API（java.util.concurrent）

## 1. CompletableFuture

### 核心特性
- 异步非阻塞编程
- 链式调用
- 异常处理机制
- 组合多个异步操作

### 常用方法
```java
// 创建CompletableFuture
CompletableFuture.supplyAsync(() -> {
    // 异步任务
    return result;
});

// 链式调用
future.thenApply(result -> transform(result))
      .thenAccept(result -> process(result))
      .exceptionally(ex -> handleException(ex));

// 组合多个任务
CompletableFuture.allOf(future1, future2, future3);
CompletableFuture.anyOf(future1, future2, future3);
```

## 2. ForkJoinPool

### 工作窃取算法
- 每个线程维护双端队列
- 空闲线程从其他线程队列尾部窃取任务
- 提高CPU利用率

### 使用示例
```java
ForkJoinPool pool = new ForkJoinPool();
pool.submit(() -> {
    // 分治任务
    if (task.isSmallEnough()) {
        process(task);
    } else {
        List<SubTask> subTasks = task.split();
        subTasks.forEach(subTask -> forkJoinPool.submit(subTask));
    }
});
```

## 3. Phaser

### 灵活的同步屏障
- 动态注册参与方
- 支持多阶段同步
- 可重用

### 使用场景
```java
Phaser phaser = new Phaser(3);

Runnable task = () -> {
    try {
        System.out.println("Phase 1");
        phaser.arriveAndAwaitAdvance();
        
        System.out.println("Phase 2");
        phaser.arriveAndAwaitAdvance();
        
        System.out.println("Phase 3");
        phaser.arriveAndDeregister();
    } catch (Exception e) {
        Thread.currentThread().interrupt();
    }
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();
```

## 4. 实战建议
- CompletableFuture适合IO密集型任务
- ForkJoinPool适合CPU密集型任务
- Phaser适用于动态变化的同步需求
