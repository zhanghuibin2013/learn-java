package com.zhhb.concurrent;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: yuguodong
 * Date: 17-5-19
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class TestThread2 implements Runnable {
    private String name;

    public void run() {
        String uuid = String.valueOf(UUID.randomUUID());
        System.out.println(uuid);
        System.out.println("name=" + this.getName());
        this.setName(uuid);
        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public synchronized String getName() {
        System.out.println(Thread.currentThread().getId() + "getName");
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public static void main(String[] args) throws InterruptedException {
        TestThread2 testThread1 = new TestThread2();

        Thread thread1 = new Thread(testThread1);
        Thread thread2 = new Thread(testThread1);

        thread1.start();
        Thread.sleep(1000);
        thread2.start();


    }

}
