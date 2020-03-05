package cn.org.orion.concurrent.program.Chapter05.parallelsort.shellsort;

public class ShellSort {
	public static void shellSort(int[] arr) {
		// count max h value
		int h = 1;
		while(h <= arr.length / 3) {
			h = h * 3 + 1;
		}
		while(h > 0) {
			for(int i = h; i < arr.length; i++) {
				if(arr[i] < arr[i - h]) {
					int tmp = arr[i];
					int j = i - h;
					while(j >= 0 && arr[j] > tmp) {
						arr[j + h] = arr[j];
						j -= h;
					}
					arr[j + h] = tmp;
				}
			}
			// count next h value
			h = (h - 1) / 3;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		ShellSort ss = new ShellSort();
		int[] arr = {10,9,8,7,6,5,4,3,2,1};
		ss.shellSort(arr);
		for(int a : arr) {
			System.out.println(a);
		}
	}

}
