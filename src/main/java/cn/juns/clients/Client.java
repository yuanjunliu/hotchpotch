package cn.juns.clients;

/**
 * Created by liuyuanjun on 2017/12/24.
 */
public interface Client {

    boolean connect(ConfigBean configBean);

    Object exec(String script);
}
