package cn.org.orion.concurrent.program.Chapter06.basic.Enterance;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class ByStep {
	static int[] arr = {1,3,4,5,6,7,8,9,10};
	
	public static void main(String[] args) {
		Arrays.stream(arr).forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				System.out.println(value);
			}
		});
	}

}
