package cn.juns.clients;

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
        return null;
    }
}
