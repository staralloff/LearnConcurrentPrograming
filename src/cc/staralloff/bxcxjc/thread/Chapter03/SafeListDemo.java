package cc.staralloff.bxcxjc.thread.Chapter03;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 有关List的线程安全
 */
public class SafeListDemo implements Runnable {
    public static List<String> list=Collections.synchronizedList(new LinkedList<String>());
    //public static List<String> list=new LinkedList<String>();
    public String name;

    public SafeListDemo(String name){
        this.name=name;
    }

    @Override
    public void run() {
        list.add(name);
    }

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            SafeListDemo task = new SafeListDemo(String.valueOf(i));
            Thread t=new Thread(task);
            t.start();
        }
        for (int i=0;i<5;i++){
            System.out.println(list.get(i));
        }
    }
}
