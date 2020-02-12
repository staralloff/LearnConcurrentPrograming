package cn.org.orion.bxcxjc.thread.Chapter02;

public class BadLockOnInteger implements Runnable {
    public static Integer i=0;
    static BadLockOnInteger instance = new BadLockOnInteger();
    @Override
    public void run() {
        synchronized (instance){
            for(int j=0;j<10000000;j++){
                /*synchronized (i){
                    i++;
                }*///注释的代码是反面教材
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
