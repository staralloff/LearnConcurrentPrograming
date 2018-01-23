package cc.staralloff.bxcxjc.thread.Chapter03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 3.2.7合理的选择：优化线程池线程数量
 * 一个估算线程池大小的经验公式：
 *                    Ncpu=CPU的数量
 *          Ucpu= 目标CPU的使用率, 0<=Ucpu<=1
 *             W/C= 等待时间与计算时间的比率
 * 为保持处理器达到期望的使用率, 最优的池的大小等于：
 *             Nthreads=Ncpu*Ucpu*(1+W/C)
 * 在java中, 可以通过：
 * Runtime.getRuntime().availableProcessors()取得可用的CPU数量
 *
 *
 * 3.2.8堆栈去哪里了：在线程池中寻找堆栈
 * 线程池吃掉程序抛出的异常，这种简单的错误可能找都找不到
 * 我的一个领导曾经说过：最鄙视那些出错不打印异常堆栈的行为！我相信，任何一个得益于异常堆栈而
 * 快速定位问题的程序员来说，一定对这句话深有体会。
 */
public class DivTask implements Runnable {
    //Runnable接口，用来计算两个数的商
    int a,b;
    public DivTask(int a,int b){
        this.a=a;
        this.b=b;
    }
    @Override
    public void run() {
        double re=a/b;
        System.out.println(re);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor pools=new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());

        for(int i=0;i<5;i++){
            pools.submit(new DivTask(100,i));
            //pools.execute(new DivTask(100,i));
            //Future re=pools.submit(new DivTask(100,i));
            //re.get();
        }
    }
}
