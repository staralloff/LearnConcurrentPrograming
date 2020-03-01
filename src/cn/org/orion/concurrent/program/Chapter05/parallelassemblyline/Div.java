package cn.org.orion.concurrent.program.Chapter05.parallelassemblyline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Div implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<Msg>();

	@Override
	public void run() {
		while(true) {
			try {
				Msg msg = bq.take();
				msg.i = msg.i/msg.j;
				System.out.println(msg.orgStr + "=" + msg.i);
			}catch(InterruptedException e) {
			}
		}
	}
}
