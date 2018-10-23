package learn.common.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghuibin
 */
public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private TreeNode root;

    public class TreeNode {
        private TreeNode parent;
        private int value;
        private TreeNode left;
        private TreeNode right;
        private boolean color;

        TreeNode(Integer value, TreeNode parent) {
            this.value = value;
            this.parent = parent;
            this.color = BLACK;
        }

        public TreeNode getParent() {
            return parent;
        }


        public TreeNode getLeft() {
            return left;
        }


        public TreeNode getRight() {
            return right;
        }

        public int getValue() {
            return value;
        }

        public char getColor() {
            return color == RED ? 'R' : 'B';
        }

        @Override
        public String toString() {
            return String.valueOf(value) + ":" + (color ? "RED" : "BLACK");
        }
    }

    private static <K, V> boolean colorOf(TreeNode p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K, V> TreeNode parentOf(TreeNode p) {
        return (p == null ? null : p.parent);
    }

    private static <K, V> void setColor(TreeNode p, boolean c) {
        if (p != null) {
            p.color = c;
        }
    }

    private static <K, V> TreeNode leftOf(TreeNode p) {
        return (p == null) ? null : p.left;
    }

    private static <K, V> TreeNode rightOf(TreeNode p) {
        return (p == null) ? null : p.right;
    }

    /**
     * From CLR
     */
    private void rotateLeft(TreeNode p) {
        if (p != null) {
            TreeNode r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }

            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }

            r.left = p;
            p.parent = r;
        }
    }

    /**
     * From CLR
     */
    private void rotateRight(TreeNode p) {
        if (p != null) {
            TreeNode l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    /**
     * From CLR
     */
    private void fixAfterInsertion(TreeNode x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                TreeNode y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                TreeNode y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    public void put(Integer value) {
        TreeNode t = root;
        if (t == null) {

            root = new TreeNode(value, null);
            return;
        }
        int cmp;
        TreeNode parent;
        do {
            parent = t;
            cmp = value.compareTo(t.value);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                return;
            }
        } while (t != null);
        TreeNode e = new TreeNode(value, parent);
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
        fixAfterInsertion(e);
    }

    public List<List<TreeNode>> listfiy() {
        List<List<TreeNode>> result = new ArrayList<>();
        if (root != null) {
            List<TreeNode> list = new ArrayList<>();
            list.add(root);
            result.add(list);
            boolean isAny = false;
            do {
                isAny = false;
                List<TreeNode> temp = new ArrayList<>();
                for (TreeNode node : list) {
                    if (node == null) {
                        temp.add(null);
                        temp.add(null);
                    } else {
                        temp.add(node.left);
                        temp.add(node.right);
                        if (node.left != null || node.right != null) {
                            isAny = true;
                        }
                    }
                }
                if (isAny) {
                    result.add(temp);
                    list = temp;
                }
            } while (isAny);
        }
        return result;
    }
}
