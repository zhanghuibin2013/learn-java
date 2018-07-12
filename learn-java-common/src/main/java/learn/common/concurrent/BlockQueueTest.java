package learn.common.concurrent;

import org.junit.Test;

/**
 * Created by Administrator on 2017/6/14.
 */
public class BlockQueueTest {
    private static final int N = 10;

    @Test
    public void Test1() throws InterruptedException {
        final BlockQueue<Integer> blockQueue = new BlockQueue<Integer>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("enqueue...");
                    for (int i = 0; i < N; i++) {
                        System.out.println("enqueue:" + i);
                        blockQueue.enqueue(i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");
        t1.start();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread() + "dequeue...");
                        System.out.println(Thread.currentThread() + "dequeue:" + blockQueue.dequeue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t2" + i);
            threads[i].start();
        }

        t1.join();
        for (int i = 0; i < N; i++) {
            threads[i].join();
        }
    }
}
