package cn.org.orion.concurrent.program.Chapter05.parallelsort;

public class OddEvenSort {
	public static void oddEvenSort(int[] arr) {
		int exchFlag = 1, start = 0;
		while(exchFlag == 1 || start == 1) {
			exchFlag = 0;
			for(int i = start; i < arr.length - 1; i += 2) {
				if(arr[i] > arr[i + 1]) {
					int temp = arr[i];
					arr[i] = arr[i + 1];
					arr[i + 1] = temp;
					exchFlag = 1;
				}
			}
			if(start == 0)
				start = 1;
			else
				start = 0;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		OddEvenSort oes = new OddEvenSort();
		int[] arr = {10,9,8,7,6,5,4,3,2,1};
		oes.oddEvenSort(arr);
		for(int a: arr) {
			System.out.println(a);
		}
	}

}
