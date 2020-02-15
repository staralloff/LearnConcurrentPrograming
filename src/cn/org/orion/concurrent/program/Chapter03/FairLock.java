package cn.org.orion.concurrent.program.Chapter03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁:
 * 按照时间的先后顺序,保证先到者先得,后到者后得
 * 它不会产生饥饿现象,如果我们使用synchronized关键字进行锁控制,那么产生的锁就是非公平的
 * 而重入锁允许我们对其公平性进行设置：
 * public ReentrantLock(boolean fair)
 * 公平锁的实现成本较高,性能相对也非常低下
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while(true) {
            try{
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+" 获得锁");
            }finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FairLock r1 = new FairLock();
        Thread t1 = new Thread(r1,"Thread_t1");
        Thread t2 = new Thread(r1,"Thread_t2");
        t1.start();
        t2.start();
    }
}
