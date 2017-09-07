package learn.common.CLH;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock implements Lock {
    private final AtomicReference tail;
    private final ThreadLocal<QNode> myPred;
    private final ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference(new QNode());
        myNode = new ThreadLocal() {
            protected QNode initialValue() {
                return new QNode();
            }
        };

        myPred = new ThreadLocal();
    }

    @Override
    public void lock() {
        QNode node = myNode.get();
        node.locked = true;
        QNode pred = (QNode) tail.getAndSet(node);
        myPred.set(pred);
        while (pred.locked) {
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();
        node.locked = false;
        myNode.set(myPred.get());
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static class QNode {
        volatile boolean locked;
    }
}