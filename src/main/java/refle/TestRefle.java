package refle;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2017/7/4.
 */
public class TestRefle {

    @Test
    public void re() throws IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {
        // TODO Auto-generated method stub
        // 获取Class类对象
        Class<Person> cls = Person.class;
        // 调用newInstance方法创建Person类对象
        Person p = cls.newInstance();
        System.out.println(p);
        // 使用构造器类创建Person类对象
        Constructor<Person> con = cls.getConstructor(String.class, int.class);
        Person person = con.newInstance("lili", 12);
        System.out.println(person);
    }
}
