package cn.juns.action;

import cn.juns.clients.ESClient;
import cn.juns.invoker.Context;

/**
 * Created by liuyuanjun on 2017/12/23.
 */
public class EsAction {
    private final static ESClient client = ESClient.getClient();

    public Object exec() {
        Context context = Context.get();
        String command = context.getRequiredParam("command");
        return client.exec(command);
    }
}
