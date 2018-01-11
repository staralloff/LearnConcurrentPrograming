## 允许多个线程同时访问： 信号量（Semaphore）

### 构造方法

- Semaphore(int permits)
- Semaphore(int permits, boolean fair) 第二个参数可以指定是否公平

### 主要逻辑方法

- acquire(): 尝试获得一个准入的许可, 若无法获得, 则线程会等待, 直到有线程释放一个许可或者当前线程被中断
- acquireUninterruptibly(): 和 acquire()方法类似, 但是不响应中断
- tryAcquire(): 尝试获得一个许可, 如果成功返回true, 失败则返回false, 它不会进行等待, 立即返回
- tryAcquire(long timeout, TimeUnit unit)
- release(): 用于在线程访问结束后, 释放一个许可, 以使其他等待许可的线程可以进行资源访问