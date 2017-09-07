package learn.common.queue;

import java.util.*;
import java.util.concurrent.*;

public class Tests {
    private static class C1 implements Delayed {
        private String name;
        private int population;
        private Date date = new Date();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public C1(String name, int population) {
            this.name = name;
            this.population = population;
        }

        public String toString() {
//            Tests.this.toString();
            return getName() + " - " + getPopulation();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed o) {
            return 0;
        }
    }

    public static void main(String[] args) {
//        EnumMap map=new EnumMap();
//        PriorityQueueTest();
//        DelayQueueTest();
        ThreadPoolExecutorTest();
    }

    private static void ThreadPoolExecutorTest() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(2), Executors.defaultThreadFactory());
//                (r, executor1) -> {
//                    r.run();
//                    System.out.println("executor1=" + executor1 + ",r=" + r);
//                });
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void PriorityQueueTest() {
        Comparator<C1> OrderIsdn = new Comparator<C1>() {
            public int compare(C1 o1, C1 o2) {
                // TODO Auto-generated method stub
                int numbera = o1.getPopulation();
                int numberb = o2.getPopulation();
                if (numberb > numbera) {
                    return 1;
                } else if (numberb < numbera) {
                    return -1;
                } else {
                    return 0;
                }

            }


        };
        Queue<C1> priorityQueue = new PriorityQueue<C1>(7, OrderIsdn);

        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            int v = random.nextInt(50);
            C1 t1 = new C1("t" + v, v);
            priorityQueue.add(t1);
        }

        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
    }

    private static void DelayQueueTest() {
        Comparator<C1> OrderIsdn = (o1, o2) -> {
            // TODO Auto-generated method stub
            int numbera = o1.getPopulation();
            int numberb = o2.getPopulation();
            if (numberb > numbera) {
                return 1;
            } else if (numberb < numbera) {
                return -1;
            } else {
                return 0;
            }

        };
        Queue<C1> priorityQueue = new PriorityQueue<>(7, OrderIsdn);

        Random random = new Random();
        for (int i = 0; i < 1; i++) {
            int v = random.nextInt(50);
            C1 t1 = new C1("t" + v, v);
            priorityQueue.add(t1);
        }

        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
        System.out.println(priorityQueue.poll().toString());
    }
}
