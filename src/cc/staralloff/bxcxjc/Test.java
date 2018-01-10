package cc.staralloff.bxcxjc;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Author Orion
 * @LeetCode 645
 */
public class Test {
    private static int[] findErrorNums(int[] nums) {
        Arrays.sort(nums);
        int[] res = new int[2];
        for(int i=0;i<nums.length;i++){
            if(i<nums.length-1){
                if(nums[i]==nums[i+1]){
                    res[0]=nums[i];
                }
            }
            if(Arrays.binarySearch(nums,i+1)<0){
                res[1]=i+1;
            }
        }
        return res;
    }

    private static boolean isPrime(int n){
        for(int i = 2; i < Math.sqrt(n); i++) {
            if(n%i==0){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        IntStream.range(50, 100).filter(Test::isPrime).forEach(System.out::println);
        System.out.println(Arrays.toString(findErrorNums(new int[]{1, 5, 3, 2, 2, 7, 6, 4, 8, 9})));
    }
}
