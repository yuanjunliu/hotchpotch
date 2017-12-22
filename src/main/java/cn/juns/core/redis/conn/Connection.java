package cn.juns.core.redis.conn;

import cn.juns.core.redis.config.ConfigBean;
import redis.clients.jedis.Commands;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by liuyuanjun on 2017/12/21.
 */


public class Connection {
    /**
     * 是个单例，同时只支持一个连接
     */
    private static Connection CONN = null;

    private Jedis jedis;

    private Connection() {
    }

    public static Connection getConn() {
        if (CONN == null) {
            synchronized (Connection.class) {
                if (CONN == null) {
                    CONN = new Connection();
                }
            }
        }
        return CONN;
    }

    public boolean connect(ConfigBean configBean) {
        jedis = new Jedis(configBean.host, configBean.port, configBean.soTimeout);
        try {
            jedis.ping();
        } catch (JedisConnectionException e) {
            jedis = null;
            return false;
        }
        return true;
    }

    public Object exec(String key, String value) {
        return jedis.set(key, value);
    }
}
