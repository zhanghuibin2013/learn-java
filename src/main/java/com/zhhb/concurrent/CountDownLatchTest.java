package com.zhhb.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zhanghuibin on 2017/6/27.
 */
public class CountDownLatchTest {

    public void testCountDownLatch() throws InterruptedException, BrokenBarrierException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        countDownLatch.await();
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
//        cyclicBarrier.await()
    }
}
