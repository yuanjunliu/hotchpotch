package cn.juns.clients;

import cn.juns.exception.ClientException;
import org.eclipse.jetty.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;

import java.util.Objects;

/**
 * Created by liuyuanjun on 2017/12/21.
 */


public class RedisClient extends redis.clients.jedis.Connection implements Client{
    /**
     * 是个单例，同时只支持一个连接
     */
    private static RedisClient client = null;

    private Jedis jedis;

    private RedisClient() {
    }

    public static RedisClient getClient() {
        if (client == null) {
            synchronized (RedisClient.class) {
                if (client == null) {
                    client = new RedisClient();
                }
            }
        }
        return client;
    }

    public boolean connect(ConfigBean configBean) {
        disconnect();
        String host = StringUtil.isBlank(configBean.getHost()) ? Protocol.DEFAULT_HOST : configBean.getHost();
        int port = configBean.getPort() > 0 ? configBean.getPort() : Protocol.DEFAULT_PORT;
        int soTimeout = configBean.getSoTimeout() > 0 ? configBean.getSoTimeout() : Protocol.DEFAULT_TIMEOUT;
        super.setHost(host);
        super.setPort(port);
        super.setSoTimeout(soTimeout);
        connect();
        return true;
    }

    public Object exec(String script) {
        assert Objects.nonNull(script);
        String[] strs = script.split("\\s+");
        if (strs.length < 2) {
            throw new ClientException("命令或者参数不对");
        }
        String cmd = strs[0];
        Protocol.Command command = Protocol.Command.valueOf(cmd.toUpperCase());
        int argLen = strs.length - 1;
        String[] args = new String[argLen];
        for (int i = 0; i < argLen; i++) {
            args[i] = strs[i + 1];
        }
        sendCommand(command, args);
        return getBulkReply();
    }
}
