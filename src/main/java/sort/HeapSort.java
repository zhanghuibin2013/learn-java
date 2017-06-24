package sort;

/**
 * Created by zhanghuibin on 2017/5/31.
 */
public class HeapSort {

    private static final int N = 100;

//    public static void main(String[] args) {
//        int arr[] = new int[]{8, 12, 10, 10, 9, 1, 8, 4, 0, 4, 6, 9, 0, 2, 9, 7, 15, 17, 10, 10};
////        Item arr[] = new Item[N];
////        for (int i = 0; i < N; i++) {
////            arr[i] = new Item(arri[i]);
////        }
//        HeapSort quickSort = new HeapSort();
//
//        quickSort.print(arr);
////        quickSort.quickSort1(arr, 0, N - 1);
////        quickSort.print(arr);
////        for (int i = 1; i < N; i++) {
////            System.out.println(i + "\t" + getDepth(i));
////        }
//    }

    private static void print(int[] arr) {
        int n = arr.length;
        for (int i = 0, depth = 0; i < n; ) {
            int count = (int) Math.pow(2, depth);
            for (int j = 0; i < n && j < count; j++) {
                System.out.print(arr[i] + "\t");
                i++;
            }
            System.out.println();
            depth++;
        }
    }

    private static int getDepth(int n) {
        return (int) (Math.log10(n) / Math.log10(2) + 1);
    }
}
