package cn.org.orion.concurrent.program.Chapter03.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程创建：
 * 线程池的主要作用是为了线程复用, 也就是避免了线程的频繁创建. 但是, 最开始
 * 的那些线程从何而来呢? 答案就是ThreadFactory
 */
public class ThreadFactory {
    public static class MyTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new java.util.concurrent.ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        System.out.println("create "+t);
                        return t;
                    }
                });
        for (int i = 0; i < 5; i++) {
            es.submit(task);
        }
        Thread.sleep(2000);
    }
}
