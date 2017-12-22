package cn.juns.core;

import org.eclipse.jetty.server.Server;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class Application {
    public static void main(String[] args) {
        Server server = new Server(1234);
        server.setHandler(new HelloHandler());
        try {
            server.start();
            server.dumpStdErr();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
