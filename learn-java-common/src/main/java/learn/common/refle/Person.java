package learn.common.refle;

/**
 * Created by Administrator on 2017/7/4.
 */
class Person {
    private String name;
    private int age;

    public Person() {
        super();
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name:" + name + "---age:" + age;
    }
}
