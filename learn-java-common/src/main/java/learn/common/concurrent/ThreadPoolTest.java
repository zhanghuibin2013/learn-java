package learn.common.concurrent;

import java.util.concurrent.*;

/**
 * @author zhanghuibin
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new SynchronousQueue<>(true));
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName());

                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 3; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
//        executorService.shutdown();
        while (!executorService.isTerminated()) {
            System.out.println(((ThreadPoolExecutor) executorService).getActiveCount());
            Thread.sleep(10);
        }
    }
}
