package learn.common.collection;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class MapTests {
    @Test
    public void treeMapTest() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
        map.put(-20, -20);
    }

    @Test
    public void treeMapTest2() {
        RedBlackTree tree = new RedBlackTree();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            tree.put(random.nextInt(100));
            System.out.println("------------------------------------------------------------------------------------------");
            printTree(tree.listfiy());
        }
//        tree.put(13);
//        tree.put(8);
//        tree.put(17);
//        tree.put(1);
//        tree.put(11);
//        tree.put(15);
//        tree.put(25);
//        tree.put(6);
//        tree.put(22);
//        tree.put(27);
//        tree.put(14);
//        tree.put(21);
    }

    void printTree(List<List<RedBlackTree.TreeNode>> lists) {
        if (lists == null || lists.size() == 0) {
            System.out.println("null");
            return;
        }
        int maxWidth = (1 << (lists.size() - 1)) * 7;
        for (int i = 0; i < lists.size(); i++) {
            List<RedBlackTree.TreeNode> list = lists.get(i);
            int eachWidth = maxWidth / list.size();
            int headSpace = (eachWidth - 6) / 2;
            int tailSpace = (eachWidth - 6) - headSpace;
            for (int j = 0; j < list.size(); j++) {
                for (int k = 0; k < headSpace; k++) {
                    System.out.print(" ");
                }
                RedBlackTree.TreeNode x = list.get(j);
                if (x == null) {
                    System.out.print("      ");
                } else {
//                    String command = "echo -e \"\\033[31m\\033[47m";
//                    command += "(" + x.getValue() + ")";
//                    command += "\"";
//                    Process process = null;
//                    try {
//                        process = Runtime.getRuntime().exec(command);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        process.waitFor();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.print("(" + x.getValue() + " " + x.getColor() + ")");
                }
                if (j < list.size() - 1) {
                    for (int k = 0; k < tailSpace; k++) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();

            if (i < lists.size() - 1) {
                for (int j = 0; j < list.size(); j++) {
                    for (int k = 0; k < headSpace - 1; k++) {
                        System.out.print(" ");
                    }

                    RedBlackTree.TreeNode x = list.get(j);
                    if (x == null) {
                        System.out.print("        ");

                    } else {
                        if (x.getLeft() == null) {
                            System.out.print(" ");
                        } else {
                            System.out.print("/");
                        }
                        System.out.print("      ");
                        if (x.getRight() == null) {
                            System.out.print(" ");
                        } else {
                            System.out.print("\\");
                        }
                    }
                    if (j < list.size() - 1) {
                        for (int k = 0; k < tailSpace - 1; k++) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }
        }
    }
}
