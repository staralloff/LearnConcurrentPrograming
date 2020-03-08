package cn.org.orion.concurrent.program.Chapter06.basic.Enterance;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class AndThen {
	static int[] arr = {1,3,4,5,6,7,8,9,10};
	
	public static void main(String[] args) {
		IntConsumer outprintln = System.out::println;
		IntConsumer errprintln = System.err::println;
		Arrays.stream(arr).forEach(outprintln.andThen(errprintln));
	}

}
