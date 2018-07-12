package learn.common.basic;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/7 10:30
 */
public class EvalTest2 {
    private static int[] arr = new int[]{1};

    private static int test() {
        System.out.println("test");
        return 0;
    }

    public static void main(String[] args) {
        arr[test()] += 1;
    }
}


