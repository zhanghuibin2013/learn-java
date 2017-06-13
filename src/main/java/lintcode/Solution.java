package lintcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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

    private void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println(head);
    }


    /**
     * @param head: The first node of linked list.
     * @param n:    An integer.
     * @return: The head of linked list.
     */
    ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
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

}
