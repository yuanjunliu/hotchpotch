package cn.juns.core.redis;

import cn.juns.core.ivoker.Context;
import cn.juns.core.redis.config.ConfigBean;
import cn.juns.core.redis.conn.Connection;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class RedisAction {

    private Connection conn = Connection.getConn();

    public boolean connect() {
        Context context = Context.get();
        ConfigBean configBean = context.form(ConfigBean.class);
        return conn.connect(configBean);
    }

    public boolean execute() {
        return true;
    }
}
