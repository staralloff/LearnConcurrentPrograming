package cc.staralloff.bxcxjc.thread.Chapter02;

public class Run {

    public static void main(String[] args){
        Thread t1 = new Thread(){
            @Override
            public void run(){
                System.out.println("this is thread one");
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run(){
                System.out.println("this is thread two");
            }
        };
        t1.start();
        t2.start();
    }
}
