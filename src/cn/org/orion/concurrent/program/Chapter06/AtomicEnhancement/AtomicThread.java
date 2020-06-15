package cn.org.orion.concurrent.program.Chapter06.AtomicEnhancement;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AtomicThread implements Runnable {
	private static final int MAX_THREADS = 3;			// threads value
	private static final int TASK_COUNT = 3;			// missions count
	private static final int TARGET_COUNT = 10000000;	// target count
	
	private AtomicLong account = new AtomicLong(0L);
	private LongAdder laccount = new LongAdder();
	private long count = 0;
	
	static CountDownLatch cdlsync = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdlatomic = new CountDownLatch(TASK_COUNT);
	static CountDownLatch cdladdr = new CountDownLatch(TASK_COUNT);
	
	protected String name;
	protected long starttime;
	public AtomicThread(long starttime) {
		this.starttime=starttime;
	}
	@Override
	public void run() {
		long v=account.get();
		while(v<TARGET_COUNT) {
			v=account.incrementAndGet();
		}
		long endtime=System.currentTimeMillis();
		System.out.println("AtomicThread spend:"+(endtime-starttime)+"ms"+" v="+v);
	}

}
