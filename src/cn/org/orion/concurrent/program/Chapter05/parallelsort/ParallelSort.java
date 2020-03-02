package cn.org.orion.concurrent.program.Chapter05.parallelsort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelSort {
	static int[] arr = {10,9,8,7,6,5,4,3,2,1};
	static ExecutorService pool = Executors.newCachedThreadPool();
	static int exchFlag = 1;
	static synchronized void setExchFlag(int v) {
		exchFlag = v;
	}
	
	static synchronized int getExchFlag() {
		return exchFlag;
	}
	
	public static class OddEvenSortTask implements Runnable {
		int i;
		CountDownLatch latch;
		
		public OddEvenSortTask(int i,CountDownLatch latch) {
			this.i = i;
			this.latch = latch;
		}
		@Override
		public void run() {
			if(arr[i] > arr[i + 1]) {
				int temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
				setExchFlag(1);
			}
			latch.countDown();
		}
	}
	
	public static void pOddEvenSort(int[] arr) throws InterruptedException {
		int start = 0;
		while(getExchFlag() == 1 || start == 1) {
			setExchFlag(0);
			//Even array length,when start is one,just len/2-1 threads
			CountDownLatch latch = new CountDownLatch(arr.length/2-(arr.length%2==0?start:0));
			for(int i = start; i < arr.length - 1; i += 2) {
				pool.submit(new OddEvenSortTask(i,latch));
			}
			//wait all threads end
			latch.await();
			if(start == 0)
				start = 1;
			else
				start = 0;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ParallelSort ps = new ParallelSort();
		ps.pOddEvenSort(ps.arr);
		for(int a : ps.arr) {
			System.out.println(a);
		}
	}

}
