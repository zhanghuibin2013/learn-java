package learn.common.innerClass;

/**
 * @author zhanghuibin
 */
public class A {
    protected B b;

    private int i;

    public A(B b, int i) {
        this.b = b;
        this.i = i;
    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public class B {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getI() {
            return A.this.i;
        }
    }
}
