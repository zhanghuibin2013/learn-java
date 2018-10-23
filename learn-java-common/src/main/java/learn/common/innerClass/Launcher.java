package learn.common.innerClass;

public class Launcher {
//    public static void main(String[] args) {
//        A a1 = new A(null, 1);
//        A.B b1 = a1.new B();
//        A a2 = new A(b1, 2);
//        A.B b2 = a2.new B();
//
//        System.out.println(a1.getI());
//        System.out.println(b1.getI());
//        System.out.println(a2.getI());
//        System.out.println(b2.getI());
//
//
//    }
    public static void main(String[] args) {
        AA aa=new AA(null,1);
        A.B b = aa.new B();
        aa.setB(b);

        System.out.println(aa.getI());
        System.out.println(b.getI());


    }
}
