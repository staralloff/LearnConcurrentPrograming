package cn.org.orion.concurrent.program.Chapter02;

import java.util.Vector;

public class ArrayListMultiThread {
    //static ArrayList<Integer> a1 = new ArrayList<Integer>(10);
    //ArrayList线程不安全,Vector线程安全
    static Vector<Integer> v = new Vector<>(10);
    public static class AddThread implements Runnable {

        @Override
        public void run() {
            for(int i = 0; i < 100000; i++) {
                v.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();t2.join();
        System.out.println(v.size());
    }
}
