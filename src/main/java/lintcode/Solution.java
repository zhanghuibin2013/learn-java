package lintcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhanghuibin on 2017/6/1.
 */
public class Solution {

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
}
