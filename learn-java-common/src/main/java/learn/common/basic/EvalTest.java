package learn.common.basic;

import java.util.Random;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/7 10:30
 */
public class EvalTest {
    private static String[] arr = new String[]{""};

    private static int test() {
        System.out.println("test");
        return 0;
    }

    public static void main(String[] args) {
        arr[test()] += "aa";
    }
}


