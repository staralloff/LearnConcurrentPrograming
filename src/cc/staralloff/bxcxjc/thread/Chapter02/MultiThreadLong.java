package cc.staralloff.bxcxjc.thread.Chapter02;

public class MultiThreadLong {
    public volatile static long t=0;
    public static class ChangeT implements Runnable {
        private long to;

        @Override
        public void run() {

        }
    }
}
