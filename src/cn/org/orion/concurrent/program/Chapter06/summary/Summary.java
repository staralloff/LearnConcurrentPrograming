package cn.org.orion.concurrent.program.Chapter06.summary;

import java.util.Arrays;

public class Summary {
	static int[] arr = {1,3,4,5,6,7,8,9,10};
	
	public static void imperative() {
		int[] iArr = {1,3,4,5,6,9,8,7,4,2};
		for(int i=0;i<iArr.length;i++) {
			System.out.println(iArr[i]);
		}
	}

	public static void declarative() {
		int[] iArr = {1,3,4,5,6,9,8,7,4,2};
		Arrays.stream(iArr).forEach(System.out::println);
	}
	
	public static void functional() {
		Arrays.stream(arr).map((x)->x=x+1).forEach(System.out::println);
		System.out.println();
		Arrays.stream(arr).forEach(System.out::println);
	}
	
	public static void traditional() {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]%2!=0) {
				arr[i]++;
			}
			System.out.println(arr[i]);
		}
	}
	
	public static void function() {
		Arrays.stream(arr).map(x->(x%2==0?x:x+1)).forEach(System.out::println);
	}
}
