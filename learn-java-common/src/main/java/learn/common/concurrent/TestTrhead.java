package learn.common.concurrent;

/**
 * Created with IntelliJ IDEA.
 * User: yuguodong
 * Date: 17-5-18
 * Time: 下午10:12
 * To change this template use File | Settings | File Templates.
 */
public class TestTrhead implements Runnable {
    private String name ;

    public TestTrhead(){}
    public TestTrhead(String name){
        this.name = name ;
    }
    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
        sy(this);
    }

    public void sy(Object obj){
        System.out.println("进来了，没有线程"+this.getName());
        synchronized (obj){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            for(int i = 0 ; i<10;i++){
                System.out.print("--"+i);
            }
        }
        System.out.println("线程后面的方法"+this.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public static void main(String[] args){
//        Runnable thread1 = new TestTrhead("thread1");
//        Runnable thread2 = new TestTrhead("thread2");
//        Thread th1 = new Thread(thread1);
//        Thread th2 = new Thread(thread2);
//        th1.start();
//        th2.start();
//
//    }
}
