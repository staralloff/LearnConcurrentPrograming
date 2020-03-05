package cn.org.orion.concurrent.program.Chapter05.parallelsort.shellsort;

public class InsertSort {
	public static void insertSort(int[] arr) {
		//扑克牌排序
		int length = arr.length;
		int j, i, key;
		for(i = 1; i < length; i++) {
			//key is element to insert
			key = arr[i];
			j = i - 1;
			while(j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j--;
			}
			//find suitable location insert key
			arr[j + 1] = key;
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		int[] arr = {7,9,10,6,8,3,4,5,1,2};
		InsertSort is = new InsertSort();
		is.insertSort(arr);
		for(int a : arr) {
			System.out.println(a);
		}
	}

}
