package cc.staralloff.bxcxjc.leetcode;

public class SumOfSquareNum {

    private static boolean judgeSquareSum(int c){
        for(long i=0;i*i<=c;i++){
            for(long j=0;j*j<=c;j++){
                if((i*i+j*j)==c){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args){
        System.out.println(judgeSquareSum(999999999));
    }
}
