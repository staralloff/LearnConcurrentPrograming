package cn.org.orion.concurrent.program.Chapter02.hiddenmistake;

public class Test {
    static int v1=1073741827;
    static int v2=1431655768;

    //int overflow
    public static void main(String[] args) {
        System.out.println("v1="+v1);
        System.out.println("v2="+v2);
        int ave=(v1+v2)/2;
        System.out.println("ave="+ave);
    }
}
