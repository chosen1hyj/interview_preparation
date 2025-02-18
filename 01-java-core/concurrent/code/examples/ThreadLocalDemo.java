public class ThreadLocalDemo {
    // 创建ThreadLocal实例
    private static ThreadLocal<String> userContext = new ThreadLocal<>();
    private static ThreadLocal<Integer> requestCounter = new ThreadLocal<>();

    public static void main(String[] args) {
        // 创建三个线程，模拟不同用户的请求
        Thread userThread1 = new Thread(() -> processUserRequest("用户A"), "Thread-UserA");
        Thread userThread2 = new Thread(() -> processUserRequest("用户B"), "Thread-UserB");
        Thread userThread3 = new Thread(() -> processUserRequest("用户C"), "Thread-UserC");

        userThread1.start();
        userThread2.start();
        userThread3.start();
    }

    // 模拟处理用户请求
    private static void processUserRequest(String user) {
        try {
            // 设置用户上下文
            userContext.set(user);
            requestCounter.set(1);

            // 第一层业务方法调用
            businessService1();
            
            // 增加请求计数
            requestCounter.set(requestCounter.get() + 1);
            
            // 第二层业务方法调用
            businessService2();

            // 最后一层业务方法调用
            businessService3();

        } finally {
            // 清理ThreadLocal，避免内存泄漏
            userContext.remove();
            requestCounter.remove();
        }
    }

    private static void businessService1() {
        String user = userContext.get();
        int count = requestCounter.get();
        System.out.printf("[%s] 业务方法1被调用 - 当前用户: %s, 请求计数: %d%n", 
            Thread.currentThread().getName(), user, count);
        try {
            Thread.sleep(100); // 模拟业务处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void businessService2() {
        String user = userContext.get();
        int count = requestCounter.get();
        System.out.printf("[%s] 业务方法2被调用 - 当前用户: %s, 请求计数: %d%n", 
            Thread.currentThread().getName(), user, count);
        try {
            Thread.sleep(100); // 模拟业务处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void businessService3() {
        String user = userContext.get();
        int count = requestCounter.get();
        System.out.printf("[%s] 业务方法3被调用 - 当前用户: %s, 请求计数: %d%n", 
            Thread.currentThread().getName(), user, count);
        try {
            Thread.sleep(100); // 模拟业务处理
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 更实用的ThreadLocal工具类示例
class UserContextHolder {
    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static void setUserContext(UserContext userContext) {
        userContextThreadLocal.set(userContext);
    }

    public static UserContext getUserContext() {
        return userContextThreadLocal.get();
    }

    public static void clear() {
        userContextThreadLocal.remove();
    }

    // 用户上下文对象
    static class UserContext {
        private String userId;
        private String username;
        private String role;

        public UserContext(String userId, String username, String role) {
            this.userId = userId;
            this.username = username;
            this.role = role;
        }

        // getter和setter方法
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}

// 使用示例：
class ThreadLocalUsageExample {
    public static void main(String[] args) {
        // 模拟用户登录和请求处理
        Thread userThread = new Thread(() -> {
            try {
                // 设置用户上下文
                UserContextHolder.setUserContext(
                    new UserContextHolder.UserContext("001", "张三", "admin")
                );

                // 处理业务逻辑
                processBusinessLogic();

            } finally {
                // 清理上下文
                UserContextHolder.clear();
            }
        });

        userThread.start();
    }

    private static void processBusinessLogic() {
        // 在任何地方都可以访问到用户上下文
        UserContextHolder.UserContext userContext = UserContextHolder.getUserContext();
        System.out.println("当前用户: " + userContext.getUsername());
        System.out.println("用户角色: " + userContext.getRole());
        
        // 可以在任何方法中获取用户信息，而不需要显式传递
        validateUserPermission();
    }

    private static void validateUserPermission() {
        UserContextHolder.UserContext userContext = UserContextHolder.getUserContext();
        if ("admin".equals(userContext.getRole())) {
            System.out.println("用户【" + userContext.getUsername() + "】具有管理员权限");
        }
    }
}
