package cn.org.orion.bxcxjc.thread.Chapter02;

/**
 * 守护线程
 * Thread.setDaemon(true)
 * 不然t线程在主线程结束后仍会不停地打印
 */
public class DaemonDemo {
    public static class DaemonT extends Thread {
        public void run() {
            while(true) {
                System.out.println("I am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t=new DaemonT();
        t.setDaemon(true);
        t.start();

        Thread.sleep(2000);
    }
}
