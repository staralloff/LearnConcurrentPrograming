package cc.staralloff.bxcxjc.thread.Chapter04;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	public static class Candidate {
		int id;
		volatile int score;
	}
	public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater
		= AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");
	//检查Updater是否工作正确
	public static AtomicInteger allScore = new AtomicInteger(0);
	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		final Candidate stu = new Candidate();
		Thread[] ts = new Thread[10000];
		for(int i = 0; i < 10000; i++) {
			ts[i] = new Thread() {
				public void run() {
					//四舍五入
					if(Math.random()>0.4) {
						scoreUpdater.incrementAndGet(stu);
						allScore.incrementAndGet();
					}
				}
			};
			ts[i].start();
		}
		for(int i = 0; i < 10000; i++) {
			ts[i].join();
		}
		long end = System.currentTimeMillis();
		System.out.println("score="+stu.score);
		System.out.println("allScore="+allScore);
		System.out.println("runTime="+(end-start)/1000.00f+"second");
	}

}
