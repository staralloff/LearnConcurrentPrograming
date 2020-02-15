package cn.org.orion.concurrent.program.Chapter02;

/**
 * 线程优先级
 */
public class PriorityDemo {
    public static class HightPriority extends Thread {
        static int count=0;
        public void run() {
            while(true) {
                synchronized (PriorityDemo.class){
                    count++;
                    if(count>10000000){
                        System.out.println("HightPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        static int count=0;
        public void run() {
            while(true) {
                synchronized (PriorityDemo.class){
                    count++;
                    if(count>10000000){
                        System.out.println("LowPriority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread high=new HightPriority();
        Thread low=new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }
}
