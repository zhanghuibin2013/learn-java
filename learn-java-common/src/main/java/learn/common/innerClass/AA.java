package learn.common.innerClass;

public class AA extends A {
    public AA(B b, int i) {
        super(b, i);
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
