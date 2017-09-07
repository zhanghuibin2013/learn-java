package learn.common.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-9-7
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPoolTests implements Runnable {
    @Test
    public void test1() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executor.execute(this);
        }
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }

    private ThreadLocal<Integer> count = new ThreadLocal<>();

    @Override
    public void run() {
        Integer integer = count.get();
        if (integer == null) {
            Random random = new Random();
            count.set(random.nextInt());
        }

        System.out.println(Thread.currentThread().getName() + "," + Thread.currentThread() + "," + integer + "," + count.get());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
