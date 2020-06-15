package cn.org.orion.concurrent.program.Chapter06.AtomicEnhancement;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
	private static final int MAX_THREADS = 3;			// threads value
	private static final int TASK_COUNT = 3;			// missions count
	private static final int TARGET_COUNT = 10000000;	// target count
	
	private AtomicLong account = new AtomicLong(0L);
	private LongAdder laccount = new LongAdder();
	private long count = 0;
	
	static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);
	
	protected synchronized long inc() {
		return ++count;
	}
	
	protected synchronized long getCount() {
		return count;
	}
	
	public class SyncThread implements Runnable {
		protected String name;
		protected long starttime;
		LongAdderDemo out;
		public SyncThread(LongAdderDemo o, long starttime) {
			out = o;
			this.starttime = starttime;
		}
		@Override
		public void run() {
			long v=out.getCount();
			while(v<TARGET_COUNT) {
				v=out.inc();
			}
			long endtime=System.currentTimeMillis();
			System.out.println("SyncThread spend:"+(endtime-starttime)+"ms"+" v="+v);
			cdlsync.countDown();
		}
	}
	
	public void testSync() throws InterruptedException {
		ExecutorService exe=Executors.newFixedThreadPool(MAX_THREADS);
		long starttime=System.currentTimeMillis();
		SyncThread sync=new SyncThread(this,starttime);
		for(int i=0;i<TASK_COUNT;i++) {
			exe.submit(sync);
		}
		cdlatomic.await();
		exe.shutdown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		LongAdderDemo lad = new LongAdderDemo();
		lad.testSync();
	}
}
