package cn.org.orion.concurrent.program.Chapter03.syncdominate;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 * 与CountDownLatch非常类似, 它也可以实现线程间的计数等待
 * 本demo完成司令下达命令, 要求10个士兵一起去完成一项任务, 要求先集结10个士兵报到, 接着10个士兵完成任务
 * interface： CyclicBarrier(int parties, Runnable barrierAction)
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic, String soldier) {
            this.cyclic = cyclic;
            this.soldier = soldier;
        }

        @Override
        public void run() {
            try{
                //等待所有士兵到齐
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            try{
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + ":任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if(flag) {
                System.out.println("司令：[士兵" + N + "个, 任务完成！]");
            } else {
                System.out.println("司令：[士兵" + N + "个, 集合完毕！]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int N = 10;
        Thread[] allSoldier=new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        //设置屏障点, 主要是为了执行这个方法
        System.out.println("集合队伍！");
        for(int i=0;i<N;++i) {
            System.out.println("士兵 "+(i+1)+" 报道！ ");
            allSoldier[i]=new Thread(new Soldier(cyclic, "士兵" + (i+1)));
            allSoldier[i].start();
        }

    }
}
