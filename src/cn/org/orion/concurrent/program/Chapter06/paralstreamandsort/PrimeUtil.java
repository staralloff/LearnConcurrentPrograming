package cn.org.orion.concurrent.program.Chapter06.paralstreamandsort;

import java.util.stream.IntStream;

public class PrimeUtil {
	//Judge if it is Prime.
	public static boolean isPrime(int number) {
		int tmp = number;
		if (tmp < 2) {
			return false;
		}
		for (int i = 2; Math.sqrt(tmp) >= i; i++) {
			if (tmp % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		// filter.
		IntStream.range(1, 1000000).filter(PrimeUtil::isPrime).count();
		// parallel.
		IntStream.range(1, 1000000).parallel().filter(PrimeUtil::isPrime).count();
	}

}
