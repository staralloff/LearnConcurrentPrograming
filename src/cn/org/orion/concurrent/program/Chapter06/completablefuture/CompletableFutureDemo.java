package cn.org.orion.concurrent.program.Chapter06.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
	
	public static class AskThread implements Runnable {
		CompletableFuture<Integer> re = null;
		
		public AskThread(CompletableFuture<Integer> re) {
			this.re = re;
		}
		
		@Override
		public void run() {
			int myRe = 0;
			try {
				myRe = re.get() * re.get();
			} catch (Exception e) {
			}
			System.out.println(myRe);
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		final CompletableFuture<Integer> future = new CompletableFuture<>();
		new Thread(new AskThread(future)).start();
		// simulate process of long time calculate.
		Thread.sleep(1000);
		// return result.
		future.complete(60);
	}

}
