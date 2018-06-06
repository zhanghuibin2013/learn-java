package learn.common.basic;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/6 09:47
 */
public class B {
    public static B t1 = new B();
    public static B t2 = new B();

    {
        System.out.println("构造块");
    }

    static {
        System.out.println("静态块");
    }

    public static void main(String[] args) {
        B t = new B();
    }
}
