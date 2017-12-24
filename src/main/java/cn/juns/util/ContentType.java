package cn.juns.util;

/**
 * Created by liuyuanjun on 2017/12/23.
 */
public enum ContentType {
    /**
     * http://tool.oschina.net/commons/
     */
    html("text/html"),
    png("image/png"),
    jpg("image/jpeg"),
    jpe("image/jpeg"),
    jpeg("image/jpeg"),
    css("text/css"),
    js("application/x-javascript"),
    map("text/html");

    private String type;

    ContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
