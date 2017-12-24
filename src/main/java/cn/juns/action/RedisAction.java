package cn.juns.action;

import cn.juns.clients.ConfigBean;
import cn.juns.clients.RedisClient;
import cn.juns.invoker.Context;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class RedisAction {

    private RedisClient client = RedisClient.getClient();

    public boolean connect() {
        Context context = Context.get();
        ConfigBean configBean = context.form(ConfigBean.class);
        return client.connect(configBean);
    }

    public Object exec() {
        Context context = Context.get();
        String command = context.getRequiredParam("command");
        return client.exec(command);
    }
}
