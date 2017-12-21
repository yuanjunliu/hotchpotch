package cn.juns.core.redis.conn;

import cn.juns.core.redis.config.ConfigBean;
import redis.clients.jedis.Jedis;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class Connection {

    public Jedis getConn(ConfigBean configBean) {
        return new Jedis(configBean.host, configBean.port, configBean.soTimeout);
    }
}
