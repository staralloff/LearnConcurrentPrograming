package cn.org.orion.concurrent.program.Chapter05.parallelsort;

public class BubbleSort {
	
	public static void bubbleSort(int[] arr) {
		for(int i = arr.length - 1; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				if(arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = {10,9,8,7,6,5,4,3,2,1};
		BubbleSort bs = new BubbleSort();
		bs.bubbleSort(arr);
		for(int a : arr) {
			System.out.println(a);
		}
	}

}
