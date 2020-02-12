package cn.org.orion.thread.Chapter03;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 * 它可以让某一个线程等待直到倒计时结束, 再开始执行
 * 这里主线程main在CountDownLatch上等待, 当所有检查任务全部完成后, 主线程方能继续执行
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo=new CountDownLatchDemo();

    @Override
    public void run() {
        try{
            //模拟检查任务
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
            end.countDown();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            exec.submit(demo);
        }
        //等待检查
        end.await();
        //发射火箭
        System.out.println("Fire!");
        exec.shutdown();
    }
}
