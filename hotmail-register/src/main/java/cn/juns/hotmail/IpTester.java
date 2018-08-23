package cn.juns.hotmail;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by 01380763 on 2018/8/21.
 */
public class IpTester implements Runnable{

    private String proxyString;

    public IpTester(String proxyString) {
        this.proxyString = proxyString;
    }

    @Override
    public void run() {
        System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver.exe");//chromedriver服务地址
        WebDriver driver = null;
        try {
            DesiredCapabilities cap = new DesiredCapabilities();
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyString).setFtpProxy(proxyString).setSslProxy(proxyString);
            cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
            cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
            System.setProperty("http.nonProxyHosts", "localhost");
            cap.setCapability(CapabilityType.PROXY, proxy);
            driver = new ChromeDriver(cap);

            driver.get("http://httpbin.org/ip");//打开指定的网站
            System.out.println(driver.getPageSource());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();//退出浏览器
            }
        }

    }
}
