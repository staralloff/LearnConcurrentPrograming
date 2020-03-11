package cn.org.orion.concurrent.program.Chapter06.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CombineCompletableFuture {
	public static Integer calc(Integer para) {
		return para / 2;
	}
	
	/**
	 * this function like Assembly following:
	 * Who can write it?
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException,ExecutionException {
		// intFuture will use CompletableFuture.supplyAsync() method to call calc(50);
		// 范型<T> T是Type的缩写
		CompletableFuture<Integer> intFuture = CompletableFuture.supplyAsync(() -> calc(50));
		CompletableFuture<Integer> intFuture2 = CompletableFuture.supplyAsync(() -> calc(25));
		
		// 函数式编程模拟一个计算过程这个过程将定义一个函数：将intFuture和intFuture2相加并赋值给intFuture2.
		// (i, j)可以理解为f(i, j).
		// simulate a calculate process, define a function(i, j): let i and j combine together
		// then give it to intFuture2 then print.
		CompletableFuture<Void> fu = intFuture.thenCombine(intFuture2, (i, j) -> (i + j))
				.thenApply((str) -> "\"" + str + "\"")
				.thenAccept(System.out::println);
		// get the Future.
		fu.get();
	}

}
