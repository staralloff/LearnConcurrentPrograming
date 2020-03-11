package cn.org.orion.concurrent.program.Chapter06.paralstreamandsort;

import java.util.Arrays;
import java.util.Random;

public class ParallelSort {
	
	public static void main(String[] args) {
		int[] arr = new int[10000000];
		Random r = new Random();
		Arrays.parallelSetAll(arr, (i)->r.nextInt());
		long sortstart = System.currentTimeMillis();
		Arrays.parallelSort(arr);
		long sortend = System.currentTimeMillis();
		System.out.println("ParallelSortTime: " + (sortend-sortstart) + "ms");
		long printstart = System.currentTimeMillis();
		for(int i : arr) {
			System.out.println(i);
		}
		long printend = System.currentTimeMillis();
		System.out.println("SerialPrintTime: " + (printend-printstart) / 1000 + "s");
	}
}
