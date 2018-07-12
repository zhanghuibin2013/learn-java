package learn.common.sync;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-1
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public class ProgramLock {
    final ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @Test
    public void main() throws InterruptedException {
        Runnable target = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("await:" + Thread.currentThread().getName());
                    condition.await();
                    Thread.sleep(50);
                    System.out.println("continue:" + Thread.currentThread().getName());

                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } finally {
                    lock.unlock();
                }
            }
        };
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(target);
            t.start();
        }
        Thread.sleep(2000);
        lock.lock();
        System.out.println("signalAll:" + Thread.currentThread().getName());
        condition.signalAll();
        Thread.sleep(2000);

        lock.unlock();
        System.out.println("after-unlock:" + Thread.currentThread().getName());
        Thread.sleep(2000);
    }
}
