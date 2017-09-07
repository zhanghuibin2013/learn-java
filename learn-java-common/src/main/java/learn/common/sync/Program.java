package learn.common.sync;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-1
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public class Program {
    private static int N = 10;
    final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        ThreadGroup tg = new ThreadGroup("tg");

        new Thread(tg, new Runnable() {
            public void run() {
                lock.lock();
                for (int i = 0; i < N; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                lock.unlock();
            }
        }, "t1").start();
//        t1.isInterrupted();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                for (int i = 0; i < N; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                lock.unlock();
            }
        }, "t2");
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                for (int i = 0; i < N; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                lock.unlock();
            }
        }, "t3");
        t3.start();

        new Thread(tg, new Runnable() {
            public void run() {
                lock.lock();
                for (int i = 0; i < N; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                lock.unlock();
            }
        }, "t1").join();
        t2.join();
        t3.join();
        TimeUnit.SECONDS.sleep(1);
        lock.lock();
        System.out.println(Long.MAX_VALUE);
        return;
//        Runnable target = new Runnable() {
//            public void run() {
//                try {
//                    synchronized (lock) {
//                        System.out.println("wait:" + Thread.currentThread().getName());
//                        lock.wait();
//                        Thread.sleep(100);
//                        System.out.println("continue:" + Thread.currentThread().getName());
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        };
//        for (int i = 0; i < 20; i++) {
//            Thread t = new Thread(target);
//            t.start();
//        }
//        Thread.sleep(10000);
//        synchronized (lock) {
//            System.out.println("notifyAll:" + Thread.currentThread().getName());
//            lock.notifyAll();
//            Thread.sleep(10000);
//        }
//        System.out.println("after-synchronized:" + Thread.currentThread().getName());
//        Thread.sleep(10000);
    }
}
