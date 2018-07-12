package learn.common.concurrent;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTest {

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);

        // 计算 10 亿项，分割任务的临界值为 1 千万
        int threshold = 10_000_000;
        PiEstimateTask task = new PiEstimateTask(0, 2_000_000_000, threshold);

        // 阻塞，直到任务执行完毕返回结果
        double pi = forkJoinPool.invoke(task);

        System.out.println("π 的值：" + pi);

        forkJoinPool.shutdown(); // 向线程池发送关闭的指令
    }
}
