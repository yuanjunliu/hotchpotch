package cn.juns.clients;

import net.dongliu.requests.Requests;

/**
 * Created by liuyuanjun on 2017/12/24.
 */
public class ESClient implements Client{
    private static ESClient client = null;

    public static ESClient getClient() {
        if (client == null) {
            synchronized (ESClient.class) {
                if (client == null) {
                    client = new ESClient();
                }
            }
        }
        return client;
    }


    @Override
    public boolean connect(ConfigBean configBean) {
        return false;
    }

    @Override
    public Object exec(String script) {
        return Requests.get(script).send().readToText();
    }
}
