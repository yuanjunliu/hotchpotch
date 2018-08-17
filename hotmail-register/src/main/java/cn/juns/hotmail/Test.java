package cn.juns.hotmail;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        String proxyIpAndPort = "124.172.117.189:80";
//        Thread t = new Thread(new Register(proxyIpAndPort));
//        t.join();
//        t.start();

        System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver.exe");//chromedriver服务地址

        DesiredCapabilities cap = new DesiredCapabilities();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        cap.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new ChromeDriver(cap);
        driver.get("http://httpbin.org/ip");//打开指定的网站
    }

}
