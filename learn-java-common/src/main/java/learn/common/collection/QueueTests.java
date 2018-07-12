package learn.common.collection;

import org.junit.Test;

import java.util.PriorityQueue;

public class QueueTests {
    @Test
    public void priorityQueueTest() {
        PriorityQueue priorityQueue = new PriorityQueue<Integer>();
        for (int i = 0; i < 11; i++) {
            priorityQueue.add(i);
        }
        priorityQueue.poll();
        priorityQueue.poll();
        priorityQueue.add(0);
    }

    public static void print(Object[] a) {
        int c = 1;
        int i = 0;
        while (true) {
            StringBuilder sbTemp = new StringBuilder();
            for (int j = 0; j < c; j++) {
                sbTemp.append(a[i++] + "\t");
                if (i >= a.length) {
                    break;
                }
            }
            c <<= 1;
            System.out.println(sbTemp.toString());
            if (i >= a.length) {
                break;
            }

        }
    }
}
