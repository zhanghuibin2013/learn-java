package learn.lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of TreeNode:
 *
 * @author zhanghuibin
 */
public class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode(int val) {
        this.val = val;
        this.left = this.right = null;
    }

    public static TreeNode build(int[] vals) {
        if (vals == null || vals.length <= 0) {
            return null;
        }
        List<TreeNode> list = new ArrayList<>();
        TreeNode root = null;
        for (int i = 0; i < vals.length; ) {
            if (root == null) {
                list.add(root = new TreeNode(vals[i++]));
            } else {
                TreeNode treeNode = list.remove(0);
                int val = vals[i++];
                if (val != -1) {
                    list.add(treeNode.left = new TreeNode(val));
                }
                if (i < vals.length) {
                    int val1 = vals[i++];
                    if (val1 != -1) {
                        list.add(treeNode.right = new TreeNode(val1));
                    }
                }
            }
        }
        return root;
    }
}
