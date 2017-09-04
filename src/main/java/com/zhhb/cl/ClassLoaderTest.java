package com.zhhb.cl;

/**
 * Created by zhanghuibin on 2017/7/6.
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
       System.out.print(ClassLoaderTest.class.getClassLoader().getParent());
    }
}
