package concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class BlockQueue<T> {
    private final List<T> list = new ArrayList<T>();

    public T dequeue() throws InterruptedException {
        synchronized (list) {
            System.out.println("BlockQueue-dequeue:" + Thread.currentThread());
            if (list.size() == 0) {
                list.wait();
            }
            T t = list.get(0);
            list.remove(0);
            return t;
        }
    }

    public void enqueue(T t) throws InterruptedException {
        synchronized (list) {
            list.add(t);
            list.notify();
        }
    }
}
