package cn.juns.clients;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class ConfigBean {
    private String host;
    private int port;
    private int soTimeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }
}
