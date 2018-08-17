package cn.juns.hotmail;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class Register implements Runnable {
    private String proxyString;

    public Register(String proxyString) {
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

            WebDriver.Timeouts timeouts = driver.manage().timeouts();
            driver.get("https://signup.live.com/");//打开指定的网站
            String memberName = RegisterUtil.getMemberName();

            driver.findElement(By.id("MemberName")).sendKeys(memberName);
            timeouts.implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("iSignupAction")).click(); //点击按扭

            String password = RegisterUtil.getPassword();
            driver.findElement(By.id("PasswordInput")).sendKeys(password);
            timeouts.implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("iSignupAction")).click(); //点击按扭

            driver.findElement(By.id("LastName")).sendKeys(RegisterUtil.getLastName());
            driver.findElement(By.id("FirstName")).sendKeys(RegisterUtil.getFirstName());
            timeouts.implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("iSignupAction")).click(); //点击按扭

            RegisterUtil.BirthDate birthDate = RegisterUtil.getBirthDate();
            driver.findElement(By.id("BirthYear")).sendKeys(birthDate.year);
            driver.findElement(By.id("BirthMonth")).sendKeys(birthDate.month);
            driver.findElement(By.id("BirthDay")).sendKeys(birthDate.day);
            timeouts.implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(By.id("iSignupAction")).click(); //点击按扭

            // 验证码
//            driver.findElement(By.xpath("//div[@id='hipTemplateContainer']//input")).sendKeys("");
//            driver.findElement(By.id("iSignupAction")).click(); //点击按扭
//            timeouts.implicitlyWait(3, TimeUnit.SECONDS);

            System.out.println("Register success, memberName: " + memberName + ",password: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (driver != null) {
//                driver.quit();//退出浏览器
//            }
        }
    }
}
