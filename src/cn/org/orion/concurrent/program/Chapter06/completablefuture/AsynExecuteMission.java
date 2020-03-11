package cn.org.orion.concurrent.program.Chapter06.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsynExecuteMission {
	public static Integer calc(Integer para) {
		try {
			// simulate a long time execution.
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		return para * para;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50));
		System.out.println(future.get());
	}

}
