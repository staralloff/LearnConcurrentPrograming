package cn.org.orion.concurrent.program.Chapter05.consumerandprovider;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
	private BlockingQueue<PCData> queue;                       //buffer
	private static final int SLEEPTIME = 1000;
	
	public Consumer(BlockingQueue<PCData> queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println("start Consumer id=" + Thread.currentThread().getId());
		Random r = new Random();                               //random wait time
		
		try {
			while(true) {
				PCData data = queue.take();                    //take mission
				if(null != data) {
					int re = data.getData() * data.getData();  //calculate square
					System.out.println(MessageFormat.format("{0}*{1}={2}", 
							data.getData(), data.getData(), re));
					Thread.sleep(r.nextInt(SLEEPTIME));
				}
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
