package com.zhhb.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhanghuibin on 2017/7/14.
 */
public class RedisTest {
    @Test
    public void sentinel() {
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.11.146.16:26379");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(20);
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, config);
        Jedis jedis = pool.getResource();
        jedis.set("jedis", "jedis");

        jedis.close();
        pool.destroy();
    }

    @Test
    public void pool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "10.11.146.16", 6379, 2000);
        Jedis jedis = pool.getResource();
        String result = jedis.set("key", "zhanghuibin");
        String value = jedis.get("key");
        jedis.close();
        pool.returnResource(jedis);

    }
}
