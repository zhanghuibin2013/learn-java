package learn.common.basic;

import java.util.Random;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/7 10:30
 */
public class RandomTest {
    public static void main(String[] args) {
        System.out.println(randomString(-229985452) + " " + randomString(-147909649));
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


