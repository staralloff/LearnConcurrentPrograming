package cn.org.orion.concurrent.program.Chapter03.syncdominate;


import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock.tryLock() method without parameters.
 * Current thread will try to get lock,if lock not occupied by other thread,
 * success and return true at the same time.
 * If lock occupied by other thread,then current thread will not wait and return false
 * at the same time.
 * Benefit:thread will not wait,but try always until get the lock.
 */
public class TryLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if(lock == 1) {
            while(true) {
                if(lock1.tryLock()) {
                    try{
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                        }
                        if(lock2.tryLock()) {
                            try{
                                System.out.println(Thread.currentThread()
                                        .getId() + ":My Job done");
                                return;
                            }finally {
                                lock2.unlock();
                            }
                        }
                    }finally {
                        lock1.unlock();
                    }
                }
            }
        }else{
            while(true) {
                if(lock2.tryLock()) {
                    try{
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                        }
                        if(lock1.tryLock()) {
                            try{
                                System.out.println(Thread.currentThread()
                                        .getId() + ":My Job done");
                                return;
                            }finally {
                                lock1.unlock();
                            }
                        }
                    }finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TryLock r1 = new TryLock(1);
        TryLock r2 = new TryLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }
}
