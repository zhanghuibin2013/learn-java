package sync;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-1
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public class Program {
    final Object lock = new Object();

    @Test
    public void main() throws InterruptedException {
        Runnable target = new Runnable() {
            public void run() {
                try {
                    synchronized (lock) {
                        System.out.println("wait:" + Thread.currentThread().getName());
                        lock.wait();
                        Thread.sleep(100);
                        System.out.println("continue:" + Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(target);
            t.start();
        }
        Thread.sleep(10000);
        synchronized (lock) {
            System.out.println("notifyAll:" + Thread.currentThread().getName());
            lock.notifyAll();
            Thread.sleep(10000);
        }
        System.out.println("after-synchronized:" + Thread.currentThread().getName());
        Thread.sleep(10000);
    }
}
