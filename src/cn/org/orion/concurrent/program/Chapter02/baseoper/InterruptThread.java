package cn.org.orion.concurrent.program.Chapter02.baseoper;

public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run(){
                while(true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted!");
                        break;
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
