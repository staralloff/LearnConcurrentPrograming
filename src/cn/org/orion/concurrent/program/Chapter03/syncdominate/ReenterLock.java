package cn.org.orion.concurrent.program.Chapter03.syncdominate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Sync's functional expansion:ReentrantLock.
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;

    @Override
    public void run() {
        for(int j=0;j<10000000;j++) {
            //Reentrantlock can replace synchronized key word Absolutely,start from jdk6.0,synchronized key word optimized,The two of them have little difference.
            lock.lock();
            //lock.lock();
            //this lock can enter repeatedly.
            try{
                i++;
            }finally{
                //While exiting critical area,must remember to release the lock.
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
