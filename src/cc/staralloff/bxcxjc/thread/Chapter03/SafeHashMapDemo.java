package cc.staralloff.bxcxjc.thread.Chapter03;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程安全的HashMap
 */
public class SafeHashMapDemo implements Runnable {
    public static Map m=Collections.synchronizedMap(new HashMap());
    //public static Map m=new HashMap();
    public int i;

    public SafeHashMapDemo(int i) {
        this.i=i;
    }

    @Override
    public void run() {
        m.put(i,i);
    }

    public static void main(String[] args) {

        for(int i=0;i<5;i++){
            SafeHashMapDemo task = new SafeHashMapDemo(i);
            Thread t = new Thread(task);
            t.start();
        }
        for(int i=0;i<5;i++){
            System.out.println(m.get(i));
        }
    }

}
