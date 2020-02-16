package cn.org.orion.concurrent.program.Chapter02.baseoper;

/**
 * Yield
 */
public class YieldMain {
    public volatile static int i=0;
    public static class AddThread extends Thread {
        @Override
        public void run() {
            for(i=0;i<10000000;i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread at = new AddThread();
        at.start();
        //at.yield();
        //yield() must used with Thread.yield()
        Thread.yield();
        System.out.println(i);
    }
}
