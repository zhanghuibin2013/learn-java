package learn.common.basic;

/**
 * @author zhanghuibin
 * @version 1.0.0
 * @date 2018/6/6 09:49
 */
public class Foo {
    final int i;
    int j;

    public Foo(int i) {
        this.i = i;
    }

    public void doSomething() {
        System.out.println(++j + i);
    }
}
