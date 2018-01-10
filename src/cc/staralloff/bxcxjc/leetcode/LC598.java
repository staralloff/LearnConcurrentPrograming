package cc.staralloff.bxcxjc.leetcode;

public class LC598 {
    private static int maxCount(int m, int n, int[][] ops) {
        for(int[] op : ops) {
            m = Math.min(m,op[0]);
            n = Math.min(n,op[1]);
        }
        return m * n;
    }

    public static void main(String[] args){
        int[][] ops = {};
        System.out.println(maxCount(40000,40000,ops));
    }
}
