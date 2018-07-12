package learn.common.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PiEstimateTask extends RecursiveTask<Double> {

    private final long begin;
    private final long end;
    /**
     * 分割任务的临界值
     */
    private final long threshold;

    public PiEstimateTask(long begin, long end, long threshold) {
        this.begin = begin;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Double compute() {  // 实现 compute 方法
        // 临界值之下，不再分割，直接计算
        if (end - begin <= threshold) {

            // 符号，取 1 或者 -1
            int sign = 1;
            double result = 0.0;
            for (long i = begin; i < end; i++) {
                result += sign / (i * 2.0 + 1);
                sign = -sign;
            }

            return result * 4;
        }

        // 分割任务
        long middle = (begin + end) / 2;
        PiEstimateTask leftTask = new PiEstimateTask(begin, middle, threshold);
        PiEstimateTask rightTask = new PiEstimateTask(middle, end, threshold);

        leftTask.fork();  // 异步执行 leftTask
        rightTask.fork(); // 异步执行 rightTask

        // 阻塞，直到 leftTask 执行完毕返回结果
        double leftResult = leftTask.join();
        // 阻塞，直到 rightTask 执行完毕返回结果
        double rightResult = rightTask.join();

        // 合并结果
        return leftResult + rightResult;
    }
}

