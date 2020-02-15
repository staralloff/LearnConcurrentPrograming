package cn.org.orion.concurrent.program.Chapter03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized的功能扩展：重入锁
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;

    @Override
    public void run() {
        for(int j=0;j<10000000;j++) {
            //重入锁可以完全替代synchronized关键字,jdk6.0开始synchronized做了优化,两者性能差距不大
            lock.lock();
            //lock.lock();
            //这种锁是可以反复进入的
            try{
                i++;
            }finally{
                //退出临界区时,必须记得释放锁
                lock.unlock();
                //lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock tl=new ReenterLock();
        Thread t1=new Thread(tl);
        Thread t2=new Thread(tl);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
