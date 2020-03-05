package cn.org.orion.concurrent.program.Chapter05.parallelsort.shellsort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelShellSort {
	// 并行的希尔排序，时间复杂度O(n^(1.3-2)),空间复杂度O(1)
	// 缺点：不稳定
	static int[] arr = {10,9,8,7,6,5,4,3,2,1};
	static ExecutorService pool = Executors.newCachedThreadPool();
	public static class ShellSortTask implements Runnable {
		int i = 0;
		int h = 0;
		CountDownLatch l;
		
		public ShellSortTask(int i, int h, CountDownLatch latch) {
			this.i = i;
			this.h = h;
			this.l = latch;
		}

		@Override
		public void run() {
			if(arr[i] < arr[i - h]) {
				int tmp = arr[i];
				int j = i - h;
				while(j >= 0 && arr[j] > tmp) {
					arr[j + h] = arr[j];
					j -= h;
				}
				arr[j + h] = tmp;
			}
			l.countDown();
		}
	}
	
	public static void pShellSort(int[] arr) throws InterruptedException {
		// count max h value
		int h = 1;
		CountDownLatch latch = null;
		while(h <= arr.length / 3) {
			h = h * 3 + 1;
		}
		while(h > 0) {
			System.out.println("h=" + h);
			if(h >= 4)
				latch = new CountDownLatch(arr.length - h);
			for(int i = h; i < arr.length; i++) {
				// control threads value
				if(h >= 4) {
					pool.execute(new ShellSortTask(i, h, latch));
				} else {
					if(arr[i] < arr[i - h]) {
						int tmp = arr[i];
						int j = i - h;
						while(j >= 0 && arr[j] > tmp) {
							arr[j + h] = arr[j];
							j -= h;
						}
						arr[j + h] = tmp;
					}
					//System.out.println(Arrays.toString(arr));
				}
			}
			// wait thread sort complete,enter next sort.
			latch.await();
			// count next h value
			h = (h - 1) / 3;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		ParallelShellSort pss = new ParallelShellSort();
		pss.pShellSort(pss.arr);
		for(int a : pss.arr) {
			System.out.println(a);
		}
	}

}