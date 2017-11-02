package learn.common.concurrent;

public class MultiThreadLong {
    static long t;

    private static class ChangeT implements Runnable {
        private long to;

        ChangeT(long to) {
            this.to = to;
        }

        @Override
        public void run() {
            while (true) {
                MultiThreadLong.t = to;
                Thread.yield();
            }
        }
    }

    private static class ReadT implements Runnable {
        @Override
        public void run() {
            while (true) {
                long v = MultiThreadLong.t;
                if (v != 111L && v != -999L && v != 333L && v != -444L) {
                    System.out.println(v);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
//        Thread.sleep(100000L);
    }
}
