package cn.org.orion.concurrent.program.Chapter02.daemon;

/**
 * Daemon Thread
 * Thread.setDaemon(true)
 * otherwise,t thread will printing without stop while main thread stopped.
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
