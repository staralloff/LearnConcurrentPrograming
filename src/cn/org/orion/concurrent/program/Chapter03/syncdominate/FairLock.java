package cn.org.orion.concurrent.program.Chapter03.syncdominate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Fair Lock:
 * Ordered by Time,make sure that earlier get first,later get last.
 * It will not product hunger appearance,if we use synchronized key word control the lock,
 * Then the lock producted is unfair,But reentrantlock allow us set its fairness:
 * public ReentrantLock(boolean fair)
 * Fair lock is high implementation cost,and performances are low comparatively 
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
