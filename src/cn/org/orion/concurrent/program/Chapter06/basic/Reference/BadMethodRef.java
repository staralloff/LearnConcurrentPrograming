package cn.org.orion.concurrent.program.Chapter06.basic.Reference;

import java.util.ArrayList;
import java.util.List;

public class BadMethodRef {
	public static void main(String[] args) {
		List<Double> numbers = new ArrayList<Double>();
		for(int i=1;i<10;i++) {
			numbers.add(Double.valueOf(i));
		}
		//numbers.stream().map(Double::toString).forEach(System.out::println);
	}

}
