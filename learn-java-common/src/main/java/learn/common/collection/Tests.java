package learn.common.collection;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-9-1
 * Time: 上午9:35
 * To change this template use File | Settings | File Templates.
 */
public class Tests {
    static class CA implements Comparable<CA> {
        private Integer value;

        public CA(int value) {
            this.value = value;
        }

        public String toString() {
            return value.toString();
        }

        @Override
        public int compareTo(CA o) {
            if (o == null) {
                return 1;
            }
            int result = Integer.compare(value, o.value);
            if (result == 0) {
                return -1;
            }
            return result;
        }
    }

    public static void main(String[] args) {
        TreeMap<CA, String> map = new TreeMap<>();
        map.put(new CA(5), "zhang5");
        map.put(new CA(5), "zhang6");
        map.put(new CA(2), "zhang2");
        map.put(new CA(3), "zhang3");
        map.put(new CA(8), "zhang8");
        map.remove(new CA(3));
        map.put(new CA(3), "zhang33");
        for (Map.Entry<CA, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
