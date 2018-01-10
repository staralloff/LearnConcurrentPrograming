package cc.staralloff.bxcxjc.leetcode;

import java.util.Arrays;

public class LC593 {
    private static double length(int[] a, int[] b) {
        return Math.sqrt((b[0]-a[0])*(b[0]-a[0])+(b[1]-a[1])*(b[1]-a[1]));
    }

    public static boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        double l1 = length(p1,p2);
        double l2 = length(p1,p3);
        double l3 = length(p1,p4);
        double l4 = length(p2,p3);
        double l5 = length(p2,p4);
        double l6 = length(p3,p4);
        double[] a = {l1,l2,l3,l4,l5,l6};
        Arrays.sort(a);
        if(a[0]==a[1]&&a[0]==a[2]&&a[0]==a[3]&&a[4]==a[5]){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        int[] p1 = {0,0};
        int[] p2 = {0,1};
        int[] p3 = {1,1};
        int[] p4 = {1,0};
        System.out.println(validSquare(p1,p2,p3,p4));
    }
}
