package learn.common.sort;


public class QuickSort2 {
    private static final int N = 20;

    private <T extends Comparable<T>> void print(T[] arr) {
        for (int i = 0; i < N; i++) {
            System.out.print(String.format("%d\t", i));
        }
        System.out.println();
        for (T i : arr) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int arri[] = new int[]{8, 12, 10, 10, 9, 1, 8, 4, 0, 4, 6, 9, 0, 2, 9, 7, 15, 17, 10, 10};
        Item arr[] = new Item[N];
        for (int i = 0; i < N; i++) {
            arr[i] = new Item(arri[i]);
        }
        QuickSort2 quickSort = new QuickSort2();

        quickSort.print(arr);
        quickSort.quickSort1(arr, 0, N - 1);
        quickSort.print(arr);
    }

    private static class Item implements Comparable {

        private int key;

        public Item(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }


        @Override
        public int compareTo(Object o) {
            if (o == null) {
                return 1;
            }
            if (o instanceof Item) {
                Item item = (Item) o;
                return Integer.compare(key, item.key);
            }
            throw new IllegalArgumentException("o is not instanceof Item");
        }

        @Override
        public String toString() {
            return String.valueOf(key);
        }
    }

    private <T extends Comparable<T>> void quickSort1(T[] targetArr, int start, int end) {
        int l = start;
        int h = end;
        T povit = targetArr[start];

        while (l < h) {
            while (l < h && targetArr[h].compareTo(povit) >= 0) {
                h--;
            }
            if (l < h) {
                T temp = targetArr[h];
                targetArr[h] = targetArr[l];
                targetArr[l] = temp;
                System.out.print("mr:l=" + (l) + ",h=" + (h) + ",povit=" + povit + ",temp=" + temp + "\n");
                print(targetArr);
                l++;
            }

            while (l < h && targetArr[l].compareTo(povit) <= 0) {
                l++;
            }

            if (l < h) {
                T temp = targetArr[h];
                targetArr[h] = targetArr[l];
                targetArr[l] = temp;
                System.out.print("ml:l=" + (l) + ",h=" + (h) + ",povit=" + povit + ",temp=" + temp + "\n");
                print(targetArr);
                h--;
            }
        }
        print(targetArr);
        System.out.print("l=" + (l) + ",h=" + (h) + ",povit=" + povit + "\n");
        if (l > start) {
            quickSort1(targetArr, start, l - 1);
        }
        if (h < end) {
            quickSort1(targetArr, l + 1, end);
        }
    }

    private <T extends Comparable<T>> void quickSort2(T[] targetArr, int start, int end) {
        int i = start + 1, j = end;
        T key = targetArr[start];
        if (start >= end) {
            return;
        }
        /*从i++和j--两个方向搜索不满足条件的值并交换
         *
         *条件为：i++方向小于key，j--方向大于key
         */
        while (true) {
            while (targetArr[j].compareTo(key) > 0) {
                j--;
            }
            while (targetArr[i].compareTo(key) < 0 && i < j) {
                i++;
            }
            if (i >= j) {
                break;
            }
            T t = targetArr[i];
            targetArr[i] = targetArr[j];
            targetArr[j] = t;
            if (targetArr[i] == key) {
                j--;
            } else {
                i++;
            }
        }

        /*关键数据放到‘中间’*/
        if (start < j) {
            T t = targetArr[start];
            targetArr[start] = targetArr[j];
            targetArr[j] = t;
        }

        if (start < i - 1) {
            this.quickSort2(targetArr, start, i - 1);
        }
        if (j + 1 < end) {
            this.quickSort2(targetArr, j + 1, end);
        }
    }

    private <T extends Comparable<T>> void quickSort3(T[] targetArr, int start, int end) {
        int i = start, j = end;
        T key = targetArr[start];

        while (i < j) {
            /*按j--方向遍历目标数组，直到比key小的值为止*/
            while (j > i && targetArr[j].compareTo(key) >= 0) {
                j--;
            }
            if (i < j) {
                /*targetArr[i]已经保存在key中，可将后面的数填入*/
                targetArr[i] = targetArr[j];
                i++;
            }
            /*按i++方向遍历目标数组，直到比key大的值为止*/
            while (i < j && targetArr[i].compareTo(key) <= 0)
                /*此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次。*/ {
                i++;
            }
            if (i < j) {
                /*targetArr[j]已保存在targetArr[i]中，可将前面的值填入*/
                targetArr[j] = targetArr[i];
                j--;
            }
        }
        /*此时i==j*/
        targetArr[i] = key;

        /*递归调用，把key前面的完成排序*/
        if (start < i - 1) {
            this.quickSort3(targetArr, start, i - 1);
        }
        /*递归调用，把key后面的完成排序*/
        if (j + 1 < end) {
            this.quickSort3(targetArr, j + 1, end);
        }
    }

}