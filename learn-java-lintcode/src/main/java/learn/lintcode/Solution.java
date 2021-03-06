package learn.lintcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhanghuibin on 2017/6/1.
 */
public class Solution {

    public static final int MOD = 10000;

    @Test()
    public void threeSum() {
        List<List<Integer>> lists = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        if (lists != null && lists.size() > 0) {
            for (List<Integer> list : lists) {
                System.out.printf("(%d,%d,%d)%n", list.get(0), list.get(1), list.get(2));
            }
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> lists = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] > 0) {
                    break;
                }
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                for (int k = j + 1; k < nums.length; k++) {
                    if (k > j + 1 && nums[k] == nums[k - 1]) {
                        continue;
                    }
                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum == 0) {
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(list);
                        lists.add(list);
                    } else if (sum > 0) {
                        break;
                    }
                }
            }
        }
        return lists;
    }

    @Test
    public void isValid() {
        System.out.println(isValid("({})[[{[]}]]}"));
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{': {
                    stack.push(c);
                    break;
                }
                case ')': {
                    if (stack.size() <= 0) {
                        return false;
                    }
                    Character character = stack.pop();
                    if (character != '(') {
                        return false;
                    }
                    break;
                }
                case ']': {
                    if (stack.size() <= 0) {
                        return false;
                    }
                    Character character = stack.pop();
                    if (character != '[') {
                        return false;
                    }
                    break;
                }
                case '}': {
                    if (stack.size() <= 0) {
                        return false;
                    }
                    Character character = stack.pop();
                    if (character != '{') {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Test
    public void log() {
        System.out.println((int) Math.ceil(Math.log10(1268742)));
    }

    @Test
    public void fourSum() {
        int[] nums = {1, -2, -5, -4, -3, 3, 3, 5};
        int target = -11;
        List<List<Integer>> lists = fourSum(nums, target);
        System.out.println(lists);
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        Set<String> set = new HashSet<String>();

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length - 3; i++) {
//            if (nums[i + 1] > target - nums[i]) {
//                break;
//            }
            for (int j = i + 1; j < nums.length - 2; j++) {
//                if (nums[j + 1] > target - nums[i] - nums[j]) {
//                    break;
//                }
                for (int k = j + 1; k < nums.length - 1; k++) {
                    int left = target - nums[i] - nums[j] - nums[k];
                    if (nums[k + 1] > target - nums[i] - nums[j] - nums[k]) {
                        break;
                    }
                    if (map.containsKey(left) && map.get(left) > k) {
                        String key = nums[i] + "," + nums[j] + "," + nums[k] + "," + left;
                        if (!set.contains(key)) {
                            result.add(Arrays.asList(nums[i], nums[j], nums[k], left));
                            set.add(key);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Test
    public void updateBits() {
        System.out.println(updateBits(-123, 45, 21, 26));
    }

    /**
     * @param n, m: Two integer
     * @param i, j: Two bit positions
     *           return: An integer
     */
    public int updateBits(int n, int m, int i, int j) {
        if (j - i == 31) {
            return m;
        }
        return (n << (32 - i) >> (32 - i)) + (m << (32 - j) >> (32 - j) << i) + (n >> j << j);
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        public ListNode(int[] ints) {
            this(ints, 0);
        }

        public ListNode(int[] ints, int index) {
            this.val = ints[index];
            if (ints.length > index + 1) {
                this.next = new ListNode(ints, index + 1);
            }
        }

        @Override
        public String toString() {
            return val + "->" + next;
        }
    }

    @Test
    public void removeNthFromEnd() {
        ListNode head = null;
        for (int i = 5; i > 0; i--) {
            head = new ListNode(i, head);
        }
        print(head);
        head = removeNthFromEnd(head, 2);
        print(head);
    }


    /**
     * @param head: The first node of linked list.
     * @param n:    An integer.
     * @return: The head of linked list.
     */
    ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;
            if (fast == null) {
                return head.next;
            }
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        if (slow.next != null) {
            slow.next = slow.next.next;
        }
        return head;
    }

    @Test
    public void reverseInteger() {
        System.out.println(reverseInteger(22));
    }

    public int reverseInteger(int n) {
        // Write your code here
        long result = 0;
        while (n != 0) {
            result = result * 10 + n % 10;
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return 0;
            }
            n = n / 10;
        }
        return (int) result;
    }

    @Test
    public void basicTest() {
        System.out.println(-2 >>> 1);
        System.out.println(-2 >> 1);
    }

    @Test
    public void findDuplicate() {
        int[] nums = {4, 5, 1, 2, 2, 3};
        System.out.println(findDuplicate(nums));
    }

    public int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;
        while (true) {
            int slow2 = nums[slow];
            int fast2 = nums[nums[fast]];
            if (slow2 == fast2) {
                return fast;
            } else {
                slow = slow2;
                fast = fast2;
            }
        }
    }

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        if (nums.length * nums[0].length != r * c) {
            return nums;
        }
        int[][] result = new int[r][c];
        int r1 = 0, c1 = 0;
        for (int[] row : nums) {
            for (int aRow : row) {
                result[r1][c1] = aRow;
                c1++;
                if (c1 == c) {
                    c1 = 0;
                    r1++;
                }
            }
        }
        return result;
    }

    public String reverseWords(String s) {
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                sb.append(word.charAt(word.length() - j - 1));
            }
            if (i < words.length - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    @Test
    public void findWords() {
        String[] strings = {"Hello", "Alaska", "Dad", "Peace"};
        String[] words = findWords(strings);
        System.out.println(Arrays.toString(words));
    }

    public String[] findWords(String[] words) {
        List<String> res = new ArrayList<String>();
        for (String word : words) {
            int r = -1;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) ((int) c + (int) 'a' - (int) 'A');
                }
                int _r = -1;
                if ("qwertyuiop".indexOf(c) >= 0) {
                    _r = 0;
                } else if ("asdfghjkl".indexOf(c) >= 0) {
                    _r = 1;
                } else if ("zxcvbnm".indexOf(c) >= 0) {
                    _r = 2;
                }
                if (r == -1) {
                    r = _r;
                } else {
                    if (r != _r) {
                        r = -2;
                        break;
                    }
                }
            }
            if (r >= 0) {
                res.add(word);
            }
        }
        String[] result = new String[res.size()];
        return res.toArray(result);
    }

    @Test
    public void isValidSudoku() {
        String[] strings = {".87654321"
                , "2........"
                , "3........"
                , "4........"
                , "5........"
                , "6........"
                , "7........"
                , "8........"
                , "9........"};
        char[][] board = new char[9][];
        for (int i = 0; i < 9; i++) {
            board[i] = strings[i].toCharArray();
        }
        System.out.println(isValidSudoku(board));
    }

    /**
     * @param board: the board
     * @return: wether the Sudoku is valid
     */
    public boolean isValidSudoku(char[][] board) {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < 9; i++) {
            set.clear();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    if (set.contains(board[i][j])) {
                        return false;
                    }
                    set.add(board[i][j]);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            set.clear();
            for (int j = 0; j < 9; j++) {
                if (board[j][i] != '.') {
                    if (set.contains(board[j][i])) {
                        return false;
                    }
                    set.add(board[j][i]);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            int c = i % 3;
            int r = i / 3;
            set.clear();
            for (int j = 0; j < 9; j++) {
                int c1 = j % 3;
                int r1 = j / 3;
                if (board[c * 3 + c1][r * 3 + r1] != '.') {
                    if (set.contains(board[c * 3 + c1][r * 3 + r1])) {
                        return false;
                    }
                    set.add(board[c * 3 + c1][r * 3 + r1]);
                }
            }
        }
        return true;
    }

    @Test
    public void searchMatrix() {
        int[][] matrix = {{1, 2, 8, 10, 16, 21, 23, 30, 31, 37, 39, 43, 44, 46, 53, 59, 66, 68, 71}, {90, 113, 125, 138, 157, 182, 207, 229, 242, 267, 289, 308, 331, 346, 370, 392, 415, 429, 440}, {460, 478, 494, 506, 527, 549, 561, 586, 609, 629, 649, 665, 683, 704, 729, 747, 763, 786, 796}, {808, 830, 844, 864, 889, 906, 928, 947, 962, 976, 998, 1016, 1037, 1058, 1080, 1093, 1111, 1136, 1157}, {1180, 1204, 1220, 1235, 1251, 1272, 1286, 1298, 1320, 1338, 1362, 1378, 1402, 1416, 1441, 1456, 1475, 1488, 1513}, {1533, 1548, 1563, 1586, 1609, 1634, 1656, 1667, 1681, 1706, 1731, 1746, 1760, 1778, 1794, 1815, 1830, 1846, 1861}};
        System.out.println(searchMatrix(matrix, 1861));
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int r = 0;
        for (; r < matrix.length; r++) {
            if (matrix[r][0] > target) {
                if (r == 0) {
                    return false;
                }
                r--;
                break;
            }
        }
        r--;
        int s = 0, e = matrix[r].length - 1;
        while (s != e) {
            int m = (s + e) / 2;
            if (matrix[r][m] == target) {
                return true;
            }
//            Arrays.as
            if (matrix[r][m] > target) {
                e = m;
            } else {
                if (s == m) {
                    s = m + 1;
                } else {
                    s = m;
                }
            }
        }
        return matrix[r][s] == target;
    }

    @Test
    public void lengthOfLongestSubstring() {
        System.out.println(lengthOfLongestSubstring("aaaa"));
    }

    /**
     * @param s: a string
     * @return: an integer
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        int res = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c) && map.get(c) >= start) {
                int l = i - start;
                if (res < l) {
                    res = l;
                }
                start = map.get(c) + 1;
            }
            map.put(c, i);
        }
        int l2 = s.length() - start;
        if (res < l2) {
            res = l2;
        }
        return res;
    }


    //Definition of SegmentTreeNode:
    private static class SegmentTreeNode {
        public int start, end;
        public SegmentTreeNode left, right;

        public SegmentTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
            this.left = this.right = null;
        }
    }

    @Test
    public void build() {
        SegmentTreeNode root = build(1, 4);
        System.out.println(root);
    }

    /**
     * @param start, end: Denote an segment / interval
     * @return: The root of Segment Tree
     */
    public SegmentTreeNode build(int start, int end) {
        SegmentTreeNode root = new SegmentTreeNode(start, end);
        if (start == end) {
            return root;
        }
        int mid = (start + end) >> 1;
        root.left = build(start, mid);
        root.right = build(mid + 1, end);
        return root;
    }


    private class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int label) {
            this.label = label;
            this.next = null;
        }

        public RandomListNode(int val, RandomListNode next) {
            this.label = val;
            this.next = next;
        }

        public RandomListNode(int[] ints) {
            this(ints, 0);
        }

        public RandomListNode(int[] ints, int index) {
            this.label = ints[index];
            if (ints.length > index + 1) {
                this.next = new RandomListNode(ints, index + 1);
            }
        }

        @Override
        public String toString() {
            return label + "->" + next + ",[" + (random == null ? "null" : random.label) + "]";
        }
    }

    public class LRUCache {
        private class ListNode {
            int key;
            int val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
                this.next = null;
            }
        }

        private int maxCapacity;
        private int capacity;
        private ListNode cache;

        // @param capacity, an integer
        public LRUCache(int capacity) {
            // write your code here
            this.maxCapacity = capacity > 0 ? capacity : 16;
        }

        // @return an integer
        public int get(int key) {
            // write your code here
            ListNode p = getNode(key);
            if (p == null) {
                return -1;
            }
            return p.val;
        }

        // @return an integer
        private ListNode getNode(int key) {
            // write your code here
            ListNode p = cache;
            while (p != null) {
                if (p.key == key) {
                    return p;
                }
                p = p.next;
            }
            return null;
        }

        // @param key, an integer
        // @param value, an integer
        // @return nothing
        public void set(int key, int value) {
            ListNode node = getNode(key);
            if (node != null) {
                node.val = value;
                return;
            }
            node = new ListNode(key, value);
            node.next = cache;
            cache = node;
            this.capacity++;
            if (capacity > maxCapacity) {
                ListNode p = cache;
                while (p.next.next != null) {
                    p = p.next;
                }
                p.next = null;
                this.capacity--;
            }
            // write your code here
        }
    }

    @Test
    public void sortList() {
        ListNode head = new ListNode(4);
        head = new ListNode(31, head);
        head = new ListNode(25, head);
        head = new ListNode(25, head);
        head = new ListNode(21, head);
        print(head);
        head = sortList(head);
        print(head);
    }

    private void print(ListNode head) {
        System.out.println(head);
    }

    /**
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list,
     * using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        // write your code here
        if (head == null || head.next == null) {
            return head;
        }
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode end) {
        if (head.next == end) {
            if (end == null) {
                return head;
            }
            if (head.val > end.val) {
                head.next = end.next;
                end.next = head.next;
                return end;
            }
            return head;
        }
        ListNode p = null;
        ListNode t = head;
        while (t.next != null) {
            if (p == null) {
                if (head.val > t.next.val) {
                    ListNode next = t.next;
                    t.next = next.next;
                    next.next = head;
                    p = head = next;
                } else {
                    t = t.next;
                }
            } else {
                if (p.next.val > t.next.val) {
                    ListNode next = t.next;
                    t.next = next.next;
                    next.next = p.next;
                    p.next = next;
                    p = p.next;
                } else {
                    t = t.next;
                }
            }
        }

        if (p == null) {
            head.next = sortList(head.next, end);
            return head;
        }
        if (p.next != null && p.next.next != end) {
            p.next.next = sortList(p.next.next, end);
        }
        if (p != head) {
            return sortList(head, p.next);
        }
        return head;
    }

    @Test
    public void mergeKLists() {
        List<ListNode> lists = new ArrayList<ListNode>();
        lists.add(null);
        lists.add(new ListNode(new int[]{-1, 5, 11}));
        lists.add(null);
        lists.add(new ListNode(new int[]{6, 10}));
        System.out.println(mergeKLists(lists));
    }

    /**
     * @param lists: a list of sort.ListNode
     * @return: The head of one sorted list.
     */
    public ListNode mergeKLists(List<ListNode> lists) {
        // write your code here
        if (lists.size() == 0) {
            return null;
        }
        if (lists.size() == 1) {
            return lists.get(0);
        }
        while (lists.size() > 1) {
            ListNode l0 = lists.get(0);
            ListNode l1 = lists.get(1);
            ListNode l = null, p = null;

            while (l0 != null && l1 != null) {
                ListNode t;
                if (l0.val <= l1.val) {
                    t = l0;
                    l0 = l0.next;
                } else {
                    t = l1;
                    l1 = l1.next;
                }
                if (p == null) {
                    p = l = t;
                } else {
                    p.next = t;
                    p = t;
                }
            }
            if (l0 != null) {
                if (p == null) {
                    l = p = l0;
                } else {
                    p.next = l0;
                }
            }
            if (l1 != null) {
                if (p == null) {
                    l = l1;
                } else {
                    p.next = l1;
                }
            }
            lists.remove(0);
            lists.remove(0);
            lists.add(l);
        }
        return lists.get(0);
    }

    @Test
    public void reverseBetween() {
        System.out.println(reverseBetween(new ListNode(new int[]{3760, 2881, 7595, 3904, 5069, 4421, 8560, 8879, 8488, 5040, 5792, 56, 1007, 2270, 3403, 6062}), 2, 7));
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        // write your code
        ListNode pre = null;
        ListNode m0 = head;
        for (int i = 1; i < m; i++) {
            if (pre == null) {
                pre = head;
            } else {
                pre = pre.next;
            }
            m0 = m0.next;
        }
        if (pre != null) {
            pre.next = null;
        }
        ListNode n0 = m0, sub = m0.next;
        for (int i = m; i < n; i++) {
            ListNode m1 = sub;
            ListNode m2 = sub.next;
            sub.next = n0;
            n0 = m1;
            sub = m2;
        }
        m0.next = sub;
        if (pre == null) {
            return n0;
        } else {
            pre.next = n0;
            return head;
        }
    }

    @Test
    public void reorderList() {
        ListNode head = new ListNode(new int[]{0, 1, 2, 3, 4});
        reorderList(head);
        System.out.println(head);
    }

    public void reorderList(ListNode head) {
        // write your code here
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode p1 = head;
        ListNode p2 = head;
        while (true) {
            p1 = p1.next;
            p2 = p2.next;
            if (p2 != null) {
                p2 = p2.next;
            }
            if (p2 == null || p2.next == null) {
                break;
            }
        }
        ListNode l2 = p1.next;
        p1.next = null;
        p2 = l2;
        ListNode l3 = l2.next;
        while (l3 != null) {
            ListNode l4 = l3.next;
            if (p2.next == l3) {
                p2.next = null;
            }
            l3.next = p2;
            p2 = l3;
            l3 = l4;
        }
        ListNode l5 = head;
        p1 = head;
        while (true) {
            ListNode n1 = p1.next;
            l5.next = p2;
            l5 = p2;
            p1 = n1;

            ListNode n2 = p2.next;
            l5.next = p1;
            l5 = p1;
            p2 = n2;
            if (p2 == null || p1 == null) {
                break;
            }
        }
    }

    @Test
    public void copyRandomList() {
        RandomListNode head = new RandomListNode(-1);
        System.out.println(copyRandomList(head));
    }

    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        RandomListNode result = new RandomListNode(head.label);
        RandomListNode ph = head, pr = result;
        while (ph.next != null) {
            pr.next = new RandomListNode(ph.next.label);
            pr = pr.next;
            ph = ph.next;
        }
        ph = head;
        pr = result;
        while (ph != null) {
            try {
                RandomListNode random = ph.random;
                if (random == null) {
                    continue;
                }

                RandomListNode ph2 = head, pr2 = result;
                while (ph2 != random) {
                    ph2 = ph2.next;
                    pr2 = pr2.next;
                }
                pr.random = pr2;
            } finally {
                ph = ph.next;
                pr = pr.next;
            }

        }
        return result;
        // write your code here
    }

    @Test
    public void bitSwapRequired() {
        System.out.println(bitSwapRequired(1, -1));
    }

    public static int bitSwapRequired(int a, int b) {
        // write your code here
        int c = a ^ b;
        int d = 0;
        for (int i = 0; i < 32; i++) {
            if ((c & (1 << i)) != 0) {
                d++;
            }
        }
        return d;
    }

    public boolean anagram(String s, String t) {
        // write your code here
        if ((s == null) != (t == null)) {
            return false;
        }
        if (s == null) {
            return true;
        }
        if (s.length() != t.length()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = t.indexOf(c);
            if (index < 0) {
                return false;
            }
            t = t.replaceFirst(String.valueOf(c), "");
        }
        return true;
    }

    @Test
    public void rotateString() {
        char[] str = "timelimiterror".toCharArray();
        rotateString(str, 1000000000);
        System.out.println(new String(str));
    }

    public void rotateString(char[] str, int offset) {
        int length = str.length;
        if (length == 0) {
            return;
        }
        // write your code here
        offset = offset % length;
        if (offset == 0) {
            return;
        }
        int time;
        int l = length, o = offset;
        while (true) {
            if (l > o) {
                l = l % o;
                if (l == 0) {
                    time = o;
                    break;
                }
            } else if (l < o) {
                o = o % l;
                if (o == 0) {
                    time = l;
                    break;
                }
            } else {
                time = 1;
                break;
            }
        }
        for (int i = 0; i < time; i++) {
            int pt = i;
            char c0 = str[pt];
            while (true) {
                int pf = pt - offset;
                while (pf < 0) {
                    pf += length;
                }
                if (pf == i) {
                    str[pt] = c0;
                    break;
                } else {
                    str[pt] = str[pf];
                    pt = pf;
                }
            }
        }
    }

    @Test
    public void removeDuplicates() {
        System.out.println(removeDuplicates(new int[]{-14, -14, -14, -14, -14, -14, -14, -13, -13, -13, -13, -12, -11, -11, -11, -9, -9, -9, -7, -7, -7, -6, -6, -5, -5, -5, -4, -4, -4, -3, -3, -3, -2, -2, -2, -1, -1, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 14, 14, 15, 16, 17, 18, 18, 18, 20, 20, 21, 21, 21, 21, 21, 22, 22, 22, 22, 23, 24, 24, 25}));
    }

    public int removeDuplicates(int[] nums) {
        // write your code here
        if (nums.length == 0) {
            return 0;
        }
        // write your code here
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i != result && (nums[i] != nums[result] || (result == 0 || (result >= 1 && nums[i] != nums[result - 1])))) {
                nums[++result] = nums[i];
            }
        }
        return result + 1;
    }

    @Test
    public void partition() {
        ListNode head = new ListNode(new int[]{-21, -59, 4, -99, 84, -65, -32, -80, 12, -45, 17, -52, 72, -51, -64, -37, -34, -28, 85, -3, -22, 1, 4, -2, 12, -70, -83, 48, -99, 31, -87, -19, 24, 18, -66, 8, 5, 2, -20, -83, 10, 49, -35, -66, 99, -46, -3, -83, -22, -65, 14, 8, -12, 71, -94, -100, -99, 75, 48, -98, -42, 62, -65, 82, -68, -78, -58, 37, -24, -26, 55, 86, -76, 72, -79, 75, -74, -30, 92, -43, 5, 7, 65, 93, -70, 24, 93, -69, -1, 42, 85, 58, -44, 73, -8, -12, 95, -13, 77, -29, 61, -16, -90, 37, -91});
        System.out.println(partition(head, 53));
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        // write your code here
        ListNode p = head;
        ListNode small = null;
        ListNode psmall = null;
        if (head.val < x) {
            small = psmall = head;
        }
        while (p.next != null) {
            if (p.next.val < x) {
                ListNode next = p.next;
                ListNode next2 = next.next;
                if (psmall == null) {
                    psmall = small = next;
                    psmall.next = head;
                    p.next = next2;
                } else if (psmall == p) {
                    psmall = p = next;
                } else {
                    next.next = psmall.next;
                    psmall.next = next;
                    psmall = psmall.next;
                    p.next = next2;
                }
            } else {
                p = p.next;
            }
        }
        if (small == null) {
            small = head;
        }
        return small;
    }

    @Test
    public void findMedianSortedArrays() {
        System.out.println(findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6}, new int[]{2, 3, 4, 5}));
    }

    public double findMedianSortedArrays(int[] A, int[] B) {
        int la = 0, lb = 0, ra = A.length - 1, rb = B.length - 1;

        int l = 0, r = 0;
        while (la + lb < ra + rb - 1) {
            if (A[la] < B[lb]) {
                la++;
                l = A[la];
            } else {
                lb++;
                l = B[lb];
            }
            if (A[ra] > B[rb]) {
                ra--;
                r = A[ra];
            } else {
                rb--;
                r = B[rb];
            }
        }
        if (l == r) {
            return l;
        }
        return (l + r) / 2.0;
    }

    @Test
    public void LRUCacheTest() {
        System.out.println(findMedianSortedArrays(new int[]{1, 2, 3, 4, 5, 6}, new int[]{2, 3, 4, 5}));
    }

    @Test
    public void multiply() {
        System.out.println(multiply("111111", "999999999999999999999999999999999999999999999999999999999999999999999999999999"));
    }

    /**
     * @param num1 a non-negative integers
     * @param num2 a non-negative integers
     * @return return product of num1 and num2
     */
    public String multiply(String num1, String num2) {
        // Write your code here

        int[] result = new int[num1.length() + num2.length()];
        int maxIndex = 0;
        for (int i = 0; i < num1.length(); i++) {
            int forward = 0;
            int ci = (int) num1.charAt(num1.length() - 1 - i) - '0';

            for (int j = 0; j < num2.length(); j++) {
                int cj = (int) num2.charAt(num2.length() - 1 - j) - '0';
                int multi = ci * cj + forward + result[i + j];
                if (multi > 10) {
                    forward = multi / 10;
                    multi = multi % 10;
                } else {
                    forward = 0;
                }
                result[i + j] = multi;

                if (multi > 0 || forward > 0) {
                    if (maxIndex < i + j) {
                        maxIndex = i + j;
                    }
                }
            }
            if (forward > 0) {
                maxIndex = i + num2.length();
                result[i + num2.length()] = forward;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = maxIndex; i >= 0; i--) {
            sb.append(String.valueOf(result[i]));
        }
        return sb.toString();
    }


    @Test
    public void binaryRepresentation() {
        System.out.println(binaryRepresentation("17817287.6418609619140625"));

    }

    /**
     * @param n: Given a decimal number that is passed in as a string
     * @return: A string
     */
    public String binaryRepresentation(String n) {
        // write your code here
        int point = n.indexOf('.');
        float m = Float.valueOf(n);
        String result = "";
        if (point > 0) {
            result += binaryRepresentation(Integer.valueOf(n.substring(0, point)));
            if (result.length() <= 0) {
                result = "0";
            }
            String s = binaryRepresentation(Double.valueOf(n.substring(point)));
            if (s.length() > 0) {
                if (s.length() > 32) {
                    return "ERROR";
                }
                result += ".";
                result += s;
            }
        } else {
            if (n.compareTo("2147483647") <= 0) {
                result += binaryRepresentation(Integer.valueOf(n));
            } else {
                return "ERROR";
            }
        }
        return result;
    }

    private String binaryRepresentation(int n) {
        String result = "";
        while (n > 0) {
            result = (n & 1) + result;
            n = n >> 1;
        }
        return result;
    }

    private String binaryRepresentation(double n) {
        String result = "";
        while (n != 0) {
            n = n * 2;
            int n1 = (int) n;
            result = result + (n1 & 1);
            n -= n1;
        }
        return result;
    }

    @Test
    public void guessNumber() {
        re = 4;
        System.out.print(guessNumber(10));
    }

    private int re = 4;

    private int guess(int num) {
        return compare(num, re);
    }

    private int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * @param n an integer
     * @return the number you guess
     */
    public int guessNumber(int n) {
        // Write your code here
        int l = 1, r = n;
        while (l < r) {
            int m = (l + r) / 2;
            int re = guess(m);
            if (re == 0) {
                return m;
            }
            if (re == -1) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return l;
    }

    @Test
    public void lots() {
        List<Integer> result = lots(1, 32, 7);
        System.out.println(result.size());
//        for (int i : result) {
//            System.out.println(Integer.toBinaryString(i));
//        }
    }

    public List<Integer> lots(int start, int end, int count) {
        if (count == 1) {
            List<Integer> result = new ArrayList<Integer>();
            for (int i = start; i <= end; i++) {
                result.add(1 << (end - i));
            }
            return result;
        }
        List<Integer> result = new ArrayList<Integer>();
        for (int i = start; i <= end - count + 1; i++) {
            int head = 1 << (end - i);
            List<Integer> result2 = lots(i + 1, end, count - 1);
            for (int j : result2) {
                result.add(j + head);
            }
        }
        return result;
    }

    @Test
    public void testHashCode() {
        System.out.println(hashCode("ubuntu".toCharArray(), 1007));
    }

    /**
     * @param key:       A String you should hash
     * @param HASH_SIZE: An integer
     * @return an integer
     */
    public int hashCode(char[] key, int HASH_SIZE) {
        int result = 0;
        for (int i = 0; i < key.length; i++) {
            result = result * 33 + (int) key[i];
            //result+=(((int)key[i])*(int)Math.pow(33,j))%HASH_SIZE;
        }
        return result % HASH_SIZE;
        // write your code here
    }

    @Test
    public void uniquePaths() {
        System.out.println(uniquePaths(8, 68));
    }

    public int uniquePaths(int m, int n) {
        if (m > n) {
            return uniquePaths(n, m);
        }
        int s = m + n - 2;
        long cc = 1;
        int j = 2;
        for (int i = n; i <= s; i++) {
            cc *= i;
            for (; j < m; ) {
                if (cc % j != 0) {
                    break;
                }
                cc /= j;
                j++;
            }
        }
        for (; j < m; ) {
            cc /= j;
            j++;
        }
        return (int) cc;
    }

    @Test
    public void uniquePaths2() {
        System.out.println(uniquePaths2(8, 68));
    }

    public int uniquePaths2(int m, int n) {
        char[] route = new char[m + n - 2];
        int count = 0;
        for (int sm = 0, sn = 0; ; ) {
            while (sm + sn < route.length) {
                if (m > sm + 1) {
                    sm++;
                    route[sm + sn - 1] = 'R';
                } else {
                    sn++;
                    route[sm + sn - 1] = 'D';
                }
            }
            count++;
            boolean backSuccess = false;
            for (int i = route.length - 1; i >= 0; i--) {
                if (route[i] == 'R') {
                    sm--;
                    if (sn < n - 1) {
                        route[i] = 'D';
                        sn++;
                        backSuccess = true;
                        break;
                    } else {
                        route[i] = '\0';
                    }
                } else {
                    route[i] = '\0';
                    sn--;
                }
            }
            if (!backSuccess) {
                break;
            }
        }
        return count;
    }

    @Test
    public void uniquePaths3() {
        System.out.println(uniquePaths3(1, 3));
    }

    public int uniquePaths3(int m, int n) {
        int[][] grid = new int[m][n];
        grid[0][0] = 1;
        for (int i = 0; i < m; i++) {
            grid[i][0] = 1;
            for (int j = 1; j < n; j++) {
                if (i == 0) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = grid[i - 1][j] + grid[i][j - 1];
                }
            }
        }
        return grid[m - 1][n - 1];
    }

    @Test
    public void uniquePathsWithObstacles() {
        int[][] obstacleGrid = new int[][]{{0, 0}, {0, 0}, {0, 0}, {1, 0}, {0, 0}};
        System.out.println(uniquePathsWithObstacles(obstacleGrid));
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    if (j == 0) {
                        grid[i][j] = 1;
                        continue;
                    }
                    if (obstacleGrid[i][j] == 1) {
                        grid[i][j] = 0;
                    } else {
                        grid[i][j] = grid[i][j - 1];
                    }
                } else {
                    if (j == 0) {
                        if (obstacleGrid[i][j] == 1) {
                            grid[i][j] = 0;
                        } else {
                            grid[i][j] = grid[i - 1][j];
                        }
                        continue;
                    }
                    if (obstacleGrid[i][j] == 1) {
                        grid[i][j] = 0;
                    } else {
                        grid[i][j] = grid[i][j - 1] + grid[i - 1][j];
                    }
                }
            }
        }
        return grid[m - 1][n - 1];
    }

    //Definition of Interval:
    class Interval {
        int start, end;

        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    @Test
    public void insertInterval() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 5));
        System.out.println(insertInterval(intervals, new Interval(5, 7)));
    }

    @Test
    public void insertInterval2() {
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 5));
        intervals.add(new Interval(6, 8));
        System.out.println(insertInterval(intervals, new Interval(0, 9)));
    }

    public List<Interval> insertInterval(List<Interval> intervals, Interval newInterval) {
        // write your code here
        if (intervals.size() <= 0) {
            intervals.add(newInterval);
            return intervals;
        }
        for (int i = 0; i < intervals.size(); ) {
            Interval interval = intervals.get(i);
            if (interval.start < newInterval.start) {
                if (interval.end < newInterval.start) {
                    i++;
                } else if (interval.end <= newInterval.end) {
                    intervals.remove(interval);
                    newInterval.start = interval.start;
                } else {
                    return intervals;
                }
            } else if (interval.start <= newInterval.end) {
                if (interval.end <= newInterval.end) {
                    intervals.remove(interval);
                } else {
                    intervals.remove(interval);
                    newInterval.end = interval.end;
                }
            } else {
                intervals.add(intervals.indexOf(interval), newInterval);
                newInterval = null;
                break;
            }
        }
        if (newInterval != null) {
            intervals.add(newInterval);
        }
        return intervals;
    }

    @Test
    public void minSubArray() {
        System.out.println(minSubArray(Arrays.asList(-1, -2, -3, -100, -1, -50)));
        System.out.println(minSubArray(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, -19, 1, 1, 1, 1, 1, 1, 1, -2, 1, 1, 1, 1, 1, 1, 1, 1, -2, 1, -15, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)));
    }

    public int minSubArray(List<Integer> nums) {
        // write your code here
        if (nums.size() <= 0) {
            return 0;
        }
        int sum = nums.get(0);
        int result = sum;
        for (int i = 1; i < nums.size(); i++) {
            int num = nums.get(i);
            if (sum >= 0) {
                if (num < sum) {
                    sum = num;
                    if (sum < result) {
                        result = sum;
                    }
                }
            } else {
                if (sum + num < 0) {
                    sum += num;
                    if (sum < result) {
                        result = sum;
                    }
                } else {
                    sum = 0;
                }
            }
        }
        return result;
    }

    @Test
    public void productExcludeItself() {
        System.out.println(productExcludeItself(new ArrayList<>()));
        System.out.println(productExcludeItself(Collections.singletonList(1)));
        System.out.println(productExcludeItself(Arrays.asList(1, 2)));
        System.out.println(productExcludeItself(Arrays.asList(1, 2, 3)));
        System.out.println(productExcludeItself(Arrays.asList(1, 2, 3, 4)));
        System.out.println(productExcludeItself(Arrays.asList(1, 2, 3, 4, 5)));
    }

    public List<Long> productExcludeItself(List<Integer> nums) {
        // write your code here

        List<Long> result = new ArrayList<>(nums.size());
        if (nums.size() == 0) {
            return result;
        }
        if (nums.size() == 1) {
            result.add(1L);
            return result;
        }
        for (int i = 0; i < nums.size(); i++) {
            result.add(1L);
        }
        long temp = 1;
        result.set(0, temp);
        for (int i = 0; i < nums.size(); i++) {
            result.set(i, temp);
            temp *= nums.get(i);
        }
        temp = 1;
        for (int i = nums.size() - 1; i >= 0; i--) {
            result.set(i, temp * result.get(i));
            temp *= nums.get(i);
        }
        return result;
    }

    @Test
    public void recoverRotatedSortedArray() {
//        System.out.println(recoverRotatedSortedArray(Arrays.asList(4, 5, 1, 2, 3)));
//        System.out.println(recoverRotatedSortedArray(Arrays.asList(5, 6, 1, 2, 3, 4)));
        System.out.println(recoverRotatedSortedArray(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)));
    }

    public List<Integer> recoverRotatedSortedArray(List<Integer> nums) {
        // write your code here
        int size = nums.size();
        for (int i = 0; i < size - 1; i++) {
            if (nums.get(i) > nums.get(i + 1)) {
                int d = size - 1 - i;
                int di = div(size, d);
                for (int j = 0; j < di; j++) {
                    int p = i;
                    int t = nums.get(p);
                    for (; ; ) {
                        p = p + d;
                        if (p >= size) {
                            p -= size;
                        }
                        int t2 = nums.get(p);
                        nums.set(p, t);
                        t = t2;
                        if (p == i) {
                            break;
                        }
                    }
                    i++;
                }
                break;
            }
        }
        return nums;
    }

    private int div(int a, int b) {
        if (a < b) {
            return div(b, a);
        }
        int t = a % b;
        if (t != 0) {
            return div(b, t);
        }
        return b;
    }

    @Test
    public void minimumTotal() {
//        System.out.println(minimumTotal(new int[][]{{-10}}));
        System.out.println(minimumTotal(new int[][]{{1}, {2, 3}}));

    }

    /**
     * @param triangle: a list of lists of integers
     * @return: An integer, minimum path sum
     */
    public int minimumTotal(int[][] triangle) {
        // write your code here
        int[][] temp = new int[triangle.length][];
        for (int i = 0; i < triangle.length; i++) {
            int[] triangleI = triangle[i];
            temp[i] = new int[triangleI.length];
            for (int j = 0; j < triangleI.length; j++) {
                if (i == 0) {
                    temp[i][j] = triangle[i][j];
                } else if (j == 0) {
                    temp[i][j] = temp[i - 1][j] + triangle[i][j];
                } else if (j == triangleI.length - 1) {
                    temp[i][j] = temp[i - 1][j - 1] + triangle[i][j];
                } else {
                    temp[i][j] = Math.min(temp[i - 1][j - 1], temp[i - 1][j]) + triangle[i][j];
                }
            }
        }
        int minSum = Integer.MAX_VALUE;
        for (int sum : temp[temp.length - 1]) {
            if (sum < minSum) {
                minSum = sum;
            }
        }
        return minSum;
    }

    @Test
    public void lastFourDigitsOfFn() {
        for (int i = 0; i < 110; i++) {
            System.out.println(i + "=" + lastFourDigitsOfFn(i));
        }

    }

    public String lastFourDigitsOfFn(int n) {
        // write your code here
        long[][] result = new long[][]{{1, 0}, {0, 1}};
        long[][] temp = new long[][]{{1, 1}, {1, 0}};
        for (int i = n; i > 0; i = i >> 1) {
            if ((i & 1) == 1) {
                result = matrixMultiply(result, temp);
            }
            temp = matrixMultiply(temp, temp);
        }
        return String.valueOf(result[1][0]);
//        return String.valueOf(result[1][0] % 10000);
    }

    long[][] matrixMultiply(long[][] x, long[][] y) {
        long[][] result = new long[x.length][y[0].length];
        for (int i = 0; i < x.length; i++) {
            long[] row = x[i];
            for (int j = 0; j < y[0].length; j++) {
                for (int k = 0; k < row.length; k++) {
                    result[i][j] += row[k] * y[j][k];
                }
                result[i][j] = result[i][j] % MOD;
            }
        }
        return result;
    }


    @Test
    public void countRotateWords() {
        System.out.println(countRotateWords(Arrays.asList("abba", "bbaa", "baab", "aabb", "abba")));
    }

    public int countRotateWords(List<String> words) {
        // Write your code here
        List<String> words2 = new ArrayList<>(words.size());
        words2.addAll(words);
        int result = words2.size();
        while (words2.size() > 0) {
            String word = words2.remove(0);
            while (words2.contains(word)) {
                words2.remove(word);
                result--;
            }
            String rotate = rotateWord(word);
            while (!word.equals(rotate)) {
                while (words2.contains(rotate)) {
                    words2.remove(rotate);
                    result--;
                }
                rotate = rotateWord(rotate);
            }
        }
        return result;
    }

    String rotateWord(String word) {
        if (word.length() == 1) {
            return word;
        }
        return word.substring(1) + word.charAt(0);
    }

    @Test
    public void checkSumOfSquareNumbers() {
        System.out.println(checkSumOfSquareNumbers(2147483647));
    }

    public boolean checkSumOfSquareNumbers(int num) {
        // write your code here
        //if(num<=1){
        //    return false;
        //}
        Set<Integer> squareNumbers = new HashSet<>();
        for (int i = 0; ; i++) {
            int s = i * i;
            if (s > num) {
                break;
            }
            squareNumbers.add(s);
            if (squareNumbers.contains(num - s)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void isToeplitzMatrix() {
        System.out.println(isToeplitzMatrix(new int[][]{{1, 2, 3, 4}, {5, 1, 2, 3}, {9, 5, 1, 2}}));
    }

    public boolean isToeplitzMatrix(int[][] matrix) {
        // Write your code here
        int M = matrix.length;
        int N = matrix[0].length;
        for (int i = 0; i < M + N - 1; i++) {
            int x = 0, y = 0;
            if (i < M) {
                x = 0;
                y = M - i - 1;
            } else {
                x = i - M;
                y = 0;
            }
            int e = matrix[y][x];
            while (x < N - 1 && y < M - 1) {
                x++;
                y++;
                if (e != matrix[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void floodFill() {
        System.out.println(Arrays.deepToString(floodFill(new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}, 1, 1, 2)));
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // Write your code here
        int oldColor = image[sr][sc];
//        int[] steps=new int[]
        floodFill(image, sr, sc, oldColor, newColor, -1, -1);
        return image;
    }

    public void floodFill(int[][] image, int sr, int sc, int oldColor, int newColor, int exclude, int exclude2) {
        // Write your code here
        System.out.println(sr + "," + sc);
        image[sr][sc] = newColor;
        if (exclude != 0 && exclude2 != 0 && sr > 0 && image[sr - 1][sc] == oldColor) {
            floodFill(image, sr - 1, sc, oldColor, newColor, 2, exclude);
        }
        if (exclude != 1 && exclude2 != 1 && sc < image[0].length - 1 && image[sr][sc + 1] == oldColor) {
            floodFill(image, sr, sc + 1, oldColor, newColor, 3, exclude);
        }
        if (exclude != 2 && exclude2 != 2 && sr < image.length - 1 && image[sr + 1][sc] == oldColor) {
            floodFill(image, sr + 1, sc, oldColor, newColor, 0, exclude);
        }
        if (exclude != 3 && exclude2 != 3 && sc > 0 && image[sr][sc - 1] == oldColor) {
            floodFill(image, sr, sc - 1, oldColor, newColor, 1, exclude);
        }
    }

    public List<String> subdomainVisits(String[] cpdomains) {
        // Write your code here
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < cpdomains.length; i++) {
            String[] cpdomainss = cpdomains[i].split(" ");
            String cpdomain = cpdomainss[1];
            int count = Integer.valueOf(cpdomainss[0]);
            while (true) {
                Integer ex = map.get(cpdomain);
                if (ex != null) {
                    map.put(cpdomain, ex + count);
                } else {
                    map.put(cpdomain, count);
                }
                int dotIndex = cpdomain.indexOf('.');
                if (dotIndex > 0) {
                    cpdomain = cpdomain.substring(dotIndex + 1);
                } else {
                    break;
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(entry.getValue() + " " + entry.getKey());
        }
        return result;
    }

    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        // Write your code here
        int result = (int) Math.ceil(Math.log(buckets) / Math.log(minutesToTest / minutesToDie));
        return result;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // Write your code here
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            Integer lastIndex = map.get(num);
            if (lastIndex != null && lastIndex >= i - k && lastIndex <= i + k) {
                return true;
            }
            map.put(num, i);
        }
        return false;
    }

    @Test
    public void diameterOfBinaryTree() {
        System.out.println(diameterOfBinaryTree(TreeNode.build(new int[]{1, 2, 3, 4, 5})));
        System.out.println(diameterOfBinaryTree(TreeNode.build(new int[]{2, 3, -1, 1})));
    }

    public int diameterOfBinaryTree(TreeNode root) {
        // write your code here
        if (root == null) {
            return 0;
        }
        if (root.left == null) {
            return depthOfBinaryTree(root.right);
        }

        if (root.right == null) {
            return depthOfBinaryTree(root.left);
        }
        return depthOfBinaryTree(root.left) + depthOfBinaryTree(root.right);
    }

    public int depthOfBinaryTree(TreeNode root) {
        // write your code here
        if (root == null) {
            return 0;
        }
        if (root.left == null) {
            return depthOfBinaryTree(root.right) + 1;
        }

        if (root.right == null) {
            return depthOfBinaryTree(root.left) + 1;
        }
        return (int) Math.max(depthOfBinaryTree(root.left), depthOfBinaryTree(root.right)) + 1;
    }

    @Test
    public void countPrimeSetBits() {
        System.out.println(countPrimeSetBits(842, 888));
    }

    /**
     * @param L: an integer
     * @param R: an integer
     * @return: the count of numbers in the range [L, R] having a prime number of set bits in their binary representation
     */
    public int countPrimeSetBits(int L, int R) {
        // Write your code here
        int result = 0;
        Map<Integer, Boolean> primeMap = new HashMap<>();
        for (int i = L; i <= R; i++) {
            int bitsCount = countBits(i);
            Boolean prime = primeMap.get(bitsCount);
            if (prime == null) {
                prime = isPrime(bitsCount);
                primeMap.put(bitsCount, prime);
            }
            if (prime) {
                System.out.println(i + "," + bitsCount);
                result++;
            }
        }
        return result;
    }

    int countBits(int i) {
        int result = 0;
        while (i > 0) {
            result += (i & 1);
            i >>= 1;
        }
        return result;
    }

    boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void rotatedDigits() {
        System.out.println(rotatedDigits(6610));
    }

    /**
     * @param N: a positive number
     * @return: how many numbers X from 1 to N are good
     */
    public int rotatedDigits(int N) {
        // write your code here
        int result = 0;
        for (int i = 1; i <= N; i++) {
            int n = i;
            boolean isYes = false;
            boolean isNo = false;
            while (n > 0) {
                int b = n % 10;
                switch (b) {
                    case 2:
                    case 5:
                    case 6:
                    case 9: {
                        isYes = true;
                        break;
                    }
                    case 3:
                    case 4:
                    case 7: {
                        isNo = true;
                        break;
                    }
                }
                n = n / 10;
            }
            if (isYes && !isNo) {
                System.out.println(i);
                result++;
            }
        }
        return result;
    }

    @Test
    public void countBinarySubstrings() {
        System.out.println(countBinarySubstrings("00110011"));
    }

    public int countBinarySubstrings(String s) {
        // Write your code here
        int result = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            char ci = s.charAt(i);
            int j = i + 1;
            while (j < s.length() && s.charAt(j) == ci) {
                j++;
            }
            if (j >= s.length()) {
                continue;
            }
            char cj = s.charAt(j);
            if (j - i + j - 1 >= s.length()) {
                continue;
            }
            boolean match = true;
            for (int k = j + 1; k < j + j - i; k++) {
                if (s.charAt(k) != cj) {
                    match = false;
                    break;
                }
            }
            if (match) {
                result++;
            }
        }
        return result;
    }

    @Test
    public void longestWord() {
//        System.out.println(longestWord(new String[]{"w", "wo", "wor", "worl", "world"}));
        System.out.println(longestWord(new String[]{"yo", "ew", "fc", "zrc", "yodn", "fcm", "qm", "qmo", "fcmz", "z", "ewq", "yod", "ewqz", "y"}));
    }

    /**
     * @param words: a list of strings
     * @return: the longest word in words that can be built one character at a time by other words in words
     */
    public String longestWord(String[] words) {
        // Write your code here
        Set<String> wordSet = new HashSet<>();
        Set<String> builtWordSet = new HashSet<>();
        Collections.addAll(wordSet, words);
        String result = "";
        int length = 0;
        for (String word : words) {
            if (word.length() >= length) {
                if (canBuiltBy(wordSet, builtWordSet, word)) {
                    if (result.equals("") || length < word.length() || result.compareTo(word) > 0) {
                        result = word;
                        length = word.length();
                    }
                    builtWordSet.add(word);
                }
            }
        }
        return result;
    }

    boolean canBuiltBy(Set<String> wordSet, Set<String> builtWordSet, String word) {
        for (int i = word.length() - 1; i > 0; i--) {
            String subString = word.substring(0, i);
            if (builtWordSet.contains(subString)) {
                return true;
            }
            if (!wordSet.contains(subString)) {
                return false;
            }
        }
        return true;
    }

    public List<Integer> getRow(int rowIndex) {
        // write your code here
        Integer[] row = new Integer[rowIndex + 1];
        row[0] = 1;
        for (int i = 1; i <= rowIndex; i++) {
            row[i] = 1;
            for (int j = i - 1; j > 0; j--) {
                row[j] += row[j - 1];
            }
        }
        return Arrays.asList(row);

    }

    @Test
    public void intersection() {
        System.out.println(System.currentTimeMillis());
        System.out.println(Arrays.toString(intersection(new int[]{61, 24, 20, 58, 95, 53, 17, 32, 45, 85, 70, 20, 83, 62, 35, 89, 5, 95, 12, 86, 58, 77, 30, 64, 46, 13, 5, 92, 67, 40, 20, 38, 31, 18, 89, 85, 7, 30, 67, 34, 62, 35, 47, 98, 3, 41, 53, 26, 66, 40, 54, 44, 57, 46, 70, 60, 4, 63, 82, 42, 65, 59, 17, 98, 29, 72, 1, 96, 82, 66, 98, 6, 92, 31, 43, 81, 88, 60, 10, 55, 66, 82, 0, 79, 11, 81},
                new int[]{5, 25, 4, 39, 57, 49, 93, 79, 7, 8, 49, 89, 2, 7, 73, 88, 45, 15, 34, 92, 84, 38, 85, 34, 16, 6, 99, 0, 2, 36, 68, 52, 73, 50, 77, 44, 61, 48})));
        System.out.println(System.currentTimeMillis());
    }

    public int[] intersection3(int[] nums1, int[] nums2) {
        // write your code here
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            Integer li = map.get(nums1[i]);
            if (li == null) {
                li = 0;
            }
            boolean find = false;
            for (int j = li; j < nums2.length; j++) {
                if (nums2[j] == nums1[i]) {
                    list.add(nums1[i]);
                    map.put(nums1[i], j + 1);
                    find = true;
                    break;
                }
            }
            if (!find) {
                map.put(nums1[i], nums2.length);
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        // write your code here
        if (nums1.length > nums2.length) {
            return intersection(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<>(nums1.length);
        List<Integer> list = new ArrayList<>();
        for (int aNums1 : nums1) {
            map.merge(aNums1, 1, (a, b) -> a + b);
        }
        for (int aNums2 : nums2) {
            Integer cc = map.get(aNums2);
            if (cc != null && cc > 0) {
                list.add(aNums2);
                map.put(aNums2, cc - 1);
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Test
    public void printZMatrix() {
        System.out.println(Arrays.toString(printZMatrix(new int[][]{{1}})));
        System.out.println(Arrays.toString(printZMatrix(new int[][]{{1, 2}})));
        System.out.println(Arrays.toString(printZMatrix(new int[][]{{1, 2}, {1, 2}})));
        System.out.println(Arrays.toString(printZMatrix(new int[][]{{1}, {2}, {3}, {4}, {5}, {6}, {9}, {8}, {7}})));
        System.out.println(Arrays.toString(printZMatrix(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}})));
    }

    public int[] printZMatrix(int[][] matrix) {
        // write your code here
        int m = matrix.length;
        int n = matrix[0].length;
        int[] result = new int[m * n];
        int d = -1;
        int c = 0;
        for (int i = 0; i < m; i++) {
            if (d == -1) {
                for (int k = 0; k <= Math.min(i, n - 1); k++) {
                    result[c++] = matrix[i - k][k];
                }
            } else {
                for (int k = Math.min(i, n - 1); k >= 0; k--) {
                    result[c++] = matrix[i - k][k];
                }
            }
            d = -d;
        }

        for (int j = 1; j < n; j++) {
            if (d == -1) {
                for (int k = 0; k < Math.min(n - j, m); k++) {
                    result[c++] = matrix[m - 1 - k][j + k];
                }
            } else {
                for (int k = Math.min(n - j, m) - 1; k >= 0; k--) {
                    result[c++] = matrix[m - 1 - k][j + k];
                }
            }

            d = -d;
        }
        return result;
    }

    @Test
    public void findAnagrams() {
//        System.out.println(findAnagrams("abab", "ab"));
        System.out.println(findAnagrams("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        ));
    }


    /**
     * @param s: a string
     * @param p: a string
     * @return: a list of index
     */
    public List<Integer> findAnagrams(String s, String p) {
        // write your code here
        Map<Character, Integer> pMap = new HashMap<>();
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            Integer cc = pMap.get(c);
            if (cc == null) {
                pMap.put(c, 1);
            } else {
                pMap.put(c, cc + 1);
            }
        }
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> sMap = new HashMap<>();
        for (int j = 0; j < s.length() - p.length() + 1; j++) {
            boolean isMatch = true;
            sMap.clear();
            for (int k = 0; k < p.length(); k++) {
                char c = s.charAt(j + k);
                Integer pc = pMap.get(c);
                if (pc == null) {
                    sMap.clear();
                    isMatch = false;
                    j += k;
                    break;
                }
                Integer sc = sMap.get(c);
                if (sc == null) {
                    sMap.put(c, 1);
                } else if (sc.equals(pc)) {
                    sMap.clear();
                    isMatch = false;
                    break;
                } else {
                    sMap.put(c, sc + 1);
                }
            }
            if (isMatch) {
                result.add(j);
            }
        }
        return result;
    }

    public char nextGreatestLetter(char[] letters, char target) {
        // Write your code here
        char result = '\255';
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] > target && letters[i] < result) {
                result = letters[i];
            }
        }
        return result;
    }

    @Test
    public void coinProblem() {
        System.out.println(coinProblem(100, 29));
    }

    public int coinProblem(int n, int m) {
        // Write your code here
        int[] coins = new int[]{100, 50, 20, 10, 5, 2, 1};
        int result = 0;
        int d = n - m;
        int i = 0;
        while (d != 0) {
            if (d >= coins[i]) {
                result += d / coins[i];
                d = d % coins[i];
            }
            i++;
        }
        return result;
    }

    @Test
    public void splitString() {
        System.out.println(splitString("123"));
    }

    public List<List<String>> splitString(String s) {
        // write your code here
        if (s == null) {
            return null;
        }
        if (s.length() <= 0) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        return splitString(s, s.length());
    }

    List<List<String>> splitString(String s, int length) {
        if (s.length() == 1) {
            List<List<String>> result = new ArrayList<>();
            List<String> list = new ArrayList<>();
            list.add(s);
            result.add(list);
            return result;
        }
        if (length == 1) {
            List<List<String>> result = new ArrayList<>();
            List<String> list = new ArrayList<>();
            list.add(s.substring(0, 1));
            result.add(list);
            return result;
        }
        List<List<String>> temp = splitString(s, length - 1);
        int size = temp.size();
        String nextChar = s.substring(length - 1, length);
        for (int i = 0; i < size; i++) {
            List<String> list = temp.get(i);
            String lastWord = list.get(list.size() - 1);
            if (lastWord.length() == 1) {
                List<String> newList = new ArrayList<>(list.size());
                newList.addAll(list);
                newList.set(list.size() - 1, lastWord + nextChar);
                temp.add(newList);
            }
            list.add(nextChar);
        }
        return temp;
    }

    public int[] constructRectangle(int area) {
        // Write your code here
        for (int i = (int) Math.sqrt(area); i >= 1; i--) {
            if (area % i == 0) {
                int j = area / i;
                return new int[]{i, j};
            }
        }
        return null;
    }

    @Test
    public void findSubstringInWraproundString() {
        System.out.println(findSubstringInWraproundString("cac"));
    }

    public int findSubstringInWraproundString(String p) {
        // Write your code here
        int result = 0;
        for (int i = 0, j = 0; i < p.length() && j < p.length(); ) {
            if (j == p.length() - 1) {
                result += cal(j - i + 1);
                break;
            }
            char cj = p.charAt(j);
            char cjn = p.charAt(j + 1);
            if (cjn == cj + 1 || (cj == 'z' && cjn == 'a')) {
                j++;
            } else {
                result += cal(j - i + 1);
                i = j = j + 1;
            }
        }
        return result;
    }

    private int cal(int i) {
        return (i + 1) * i / 2;
    }

    @Test
    public void houseRobber() {
        System.out.println(houseRobber(new int[]{828, 125, 740, 724, 983, 321, 773, 678, 841, 842, 875, 377, 674, 144, 340, 467, 625, 916, 463, 922, 255, 662, 692, 123, 778, 766, 254, 559, 480, 483, 904, 60, 305, 966, 872, 935, 626, 691, 832, 998, 508, 657, 215, 162, 858, 179, 869, 674, 452, 158, 520, 138, 847, 452, 764, 995, 600, 568, 92, 496, 533, 404, 186, 345, 304, 420, 181, 73, 547, 281, 374, 376, 454, 438, 553, 929, 140, 298, 451, 674, 91, 531, 685, 862, 446, 262, 477, 573, 627, 624, 814, 103, 294, 388}));
    }

    public long houseRobber(int[] A) {
        // write your code here
        int l = A.length;
        if (l == 0) {
            return 0;
        }
        if (l == 1) {
            return A[0];
        }
        long[] dp = new long[l];
        for (int i = 0; i < A.length; i++) {
            switch (i) {
                case 0:
                case 1: {
                    dp[i] = A[i];
                    break;
                }
                case 2: {
                    dp[i] = A[i] + dp[i - 2];
                    break;
                }
                default: {
                    dp[i] = Math.max(A[i] + dp[i - 2], A[i] + dp[i - 3]);
                    break;
                }
            }
        }
        return dp[l - 1] > dp[l - 2] ? dp[l - 1] : dp[l - 2];
    }

}
