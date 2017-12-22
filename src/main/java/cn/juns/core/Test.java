package cn.juns.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class Test {
    public static void main(String[] args) {
        try {
            Jedis jedis = new Jedis("127.0.0.1", 6373, 200);
            System.out.println(jedis.ping());
        } catch (JedisConnectionException e) {
            System.out.println("xxx");
        }
    }
}
