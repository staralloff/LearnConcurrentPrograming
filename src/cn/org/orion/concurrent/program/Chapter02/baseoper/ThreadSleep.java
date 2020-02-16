package cn.org.orion.concurrent.program.Chapter02.baseoper;

public class ThreadSleep {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while(true) {
                    if(Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        System.out.println("Interrupted When Sleep");
                        //set interrupted status
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
