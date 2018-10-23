package learn.common.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTests {
    @Test
    public void initTest() {
        ConcurrentHashMap map = new ConcurrentHashMap(7);
        map.put("a", "b");
    }
}
