package learn.common.basic;

import java.util.Random;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/7 10:30
 */
public class RandomTest2 {
    public static void main(String[] args) {
        System.out.println(findRandomSeed("sunxiaomei"));
    }

    private static int findRandomSeed(String str) {
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            String x = randomString(i);
            if (x.equals("sunxiaomei")) {
                System.out.println(x);
                return i;
            }
        }
        return 0;
    }

    private static String randomString(int i) {
        Random ran = new Random(i);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int k = ran.nextInt(27);
            if (k == 0) {
                break;
            }
            k += 96;
            sb.append((char) k);
        }
        return sb.toString();
    }
}


