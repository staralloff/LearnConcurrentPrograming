package cn.org.orion.concurrent.program.Chapter06.basic.LambdaExpress;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Lambda {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		numbers.forEach((Integer value) -> System.out.println(value));
		
		final int num = 2;
		Function<Integer, Integer> stringConverter = (from) -> from * num;
		System.out.println(stringConverter.apply(3));
		
		//int num = 2;
		//Function<Integer, Integer> stringConverter = (from) -> from * num;
		//	num++;
		//System.out.println(stringConverter.apply(3));
	}

}
