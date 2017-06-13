package sort;

import com.sun.istack.internal.NotNull;

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


        public int compareTo(@NotNull Object o) {
            if (o == null) {
                return 1;
            }
            if (o instanceof Item) {
                Item item = (Item) o;
                return Integer.compare(key, item.key);
            }
            throw new IllegalArgumentException("o is not instanceof Item");
        }

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
        if (start >= end) return;
/*��i++��j--������������������������ֵ������
*
*����Ϊ��i++����С��key��j--�������key
*/
        while (true) {
            while (targetArr[j].compareTo(key) > 0) j--;
            while (targetArr[i].compareTo(key) < 0 && i < j) i++;
            if (i >= j) break;
            T t = targetArr[i];
            targetArr[i] = targetArr[j];
            targetArr[j] = t;
            if (targetArr[i] == key) {
                j--;
            } else {
                i++;
            }
        }

/*�ؼ����ݷŵ����м䡯*/
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
/*��j--�������Ŀ�����飬ֱ����keyС��ֵΪֹ*/
            while (j > i && targetArr[j].compareTo(key) >= 0) {
                j--;
            }
            if (i < j) {
/*targetArr[i]�Ѿ�������key�У��ɽ������������*/
                targetArr[i] = targetArr[j];
                i++;
            }
/*��i++�������Ŀ�����飬ֱ����key���ֵΪֹ*/
            while (i < j && targetArr[i].compareTo(key) <= 0)
/*�˴�һ��ҪС�ڵ����㣬��������֮����һ�ڸ�1��0������ֵĻ�����key��ֵ��ǡ����1�Ļ�����ô���С�ڵ��ڵ����þͻ�ʹ�����if�����ִ��һ�ڴΡ�*/ {
                i++;
            }
            if (i < j) {
/*targetArr[j]�ѱ�����targetArr[i]�У��ɽ�ǰ���ֵ����*/
                targetArr[j] = targetArr[i];
                j--;
            }
        }
/*��ʱi==j*/
        targetArr[i] = key;

/*�ݹ���ã���keyǰ����������*/
        if (start < i - 1) {
            this.quickSort3(targetArr, start, i - 1);
        }
/*�ݹ���ã���key������������*/
        if (j + 1 < end) {
            this.quickSort3(targetArr, j + 1, end);
        }
    }

}