package cn.juns.hotmail;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        String[] proxyIps = RegisterUtil.getProxyIPs(1);
        if (proxyIps == null) {
            System.out.println("未获取到IP");
            return;
        }
        int len = proxyIps.length;
        Thread[] threads = new Thread[len];
        for (int i = 0; i < len; i++) {
            System.out.println(proxyIps[i] + "======================");
            threads[i] = new Thread(new IpTester(proxyIps[i]));
//            threads[i] = new Thread(new Register(proxyIps[i]));
        }

        for (int i = 0; i < len; i++) {
            threads[i].join();
        }

        for (int i = 0; i < len; i++) {
            threads[i].start();
        }
    }

}
