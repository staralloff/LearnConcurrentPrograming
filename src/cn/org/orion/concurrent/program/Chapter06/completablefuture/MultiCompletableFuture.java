package cn.org.orion.concurrent.program.Chapter06.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MultiCompletableFuture {
	public static Integer calc(Integer para) {
		return para / 2;
	}
	
	// In this case,divide 2 execute 2 times.
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletableFuture<Void> fu =
				CompletableFuture.supplyAsync(() -> calc(50))
				.thenCompose((i)->CompletableFuture.supplyAsync(() -> calc(i)))
				.thenApply((str)->"\"" + str + "\"").thenAccept(System.out::println);
		fu.get();
	}

}
