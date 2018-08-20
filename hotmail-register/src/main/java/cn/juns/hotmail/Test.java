package cn.juns.hotmail;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        String proxyIpAndPort = "183.129.207.73:23197";
//        Thread t = new Thread(new Register(proxyIpAndPort));
//        t.join();
//        t.start();

//        System.setProperty("webdriver.chrome.driver", "/Users/juns/IdeaProjects/hotchpotch/hotmail-register/src/main/resources/chromedriver");//chromedriver服务地址

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyIpAndPort).setFtpProxy(proxyIpAndPort).setSslProxy(proxyIpAndPort);
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
        System.setProperty("http.nonProxyHosts", "localhost");
        cap.setCapability(CapabilityType.PROXY, proxy);
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://httpbin.org/ip");//打开指定的网站

        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), cap);
        driver.get("http://httpbin.org/ip");//打开指定的网站
        System.out.println(driver.getPageSource());
    }

}