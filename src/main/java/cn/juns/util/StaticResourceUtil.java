package cn.juns.util;

import cn.juns.exception.ClientException;

import java.io.*;

/**
 * Created by liuyuanjun on 2017/12/23.
 */
public class StaticResourceUtil {
    public static class StaticResource {
        public String contentType;
        public String content;
        public byte[] raw;

        public StaticResource(String contentType, String content) {
            this.contentType = contentType;
            this.content = content;
        }

        public StaticResource(String contentType, byte[] raw) {
            this.contentType = contentType;
            this.raw = raw;
        }
    }

    public static StaticResource getStaticResource(String sourceName) {
        String suffix = sourceName.substring(sourceName.lastIndexOf(".") + 1);
        try {
            String classPath = StaticResourceUtil.class.getResource("/").getPath();
            FileInputStream in = new FileInputStream(new File(classPath + sourceName));
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            String contentType = ContentType.valueOf(suffix).getType();
            if (contentType.contains("image")) {
                return new StaticResource(contentType, buffer);
            } else {
                return new StaticResource(contentType, new String(buffer, "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientException("找不到资源文件" + sourceName);
        }
    }

    public static String welcomeHtml() {
        return getStaticResource("index.html").content;
    }
}
