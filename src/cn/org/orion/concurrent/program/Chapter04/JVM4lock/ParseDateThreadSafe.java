package cn.org.orion.concurrent.program.Chapter04.JVM4lock;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * ThreadLocal的简单使用
 */
public class ParseDateThreadSafe implements Runnable {
	
	static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>();
    
    int i=0;
    public ParseDateThreadSafe(int i){
        this.i=i;
    }

    public void run() {
        try {
            if(t1.get()==null) {
            	t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            Date t = t1.get().parse("2015-03-29 19:29:"+i%60);
            System.out.println(i+":"+t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService es= Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;i++){
            es.execute(new ParseDateThreadSafe(i));
        }
    }
}
