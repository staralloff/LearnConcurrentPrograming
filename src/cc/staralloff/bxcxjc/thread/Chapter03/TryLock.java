package cc.staralloff.bxcxjc.thread.Chapter03;


import java.util.concurrent.locks.ReentrantLock;

/**
 * 不带参数的ReentrantLock.tryLock()方法
 * 当前线程会尝试获得锁,如果锁并未被其他线程占用,申请锁成功并立即返回true
 * 如果锁被其他线程占用,则当前线程不会等待而是立即返回false
 * 好处：线程不会傻傻地等待,而是不停地尝试
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
