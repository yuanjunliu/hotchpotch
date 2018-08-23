package cn.juns.hotmail;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by 01380763 on 2018/8/17.
 */
public class Register implements Runnable {
//    private static final String REGISTER_URL = "https://signup.live.com/";
    private static final String REGISTER_URL = "https://signup.live.com/signup?lcid=1022&lic=2&uaid=7430344233784330bfff6bce443438a2";

    private String proxyString;

    public Register(String proxyString) {
        this.proxyString = proxyString;
    }

    private WebElement isElementPresent(WebDriver driver, By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

   @Override
    public void run() {
        System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\chromedriver.exe");//chromedriver服务地址
        WebDriver driver = null;
        try {
            if (proxyString != null) {
                DesiredCapabilities cap = new DesiredCapabilities();
                Proxy proxy = new Proxy();
                proxy.setHttpProxy(proxyString).setFtpProxy(proxyString).setSslProxy(proxyString);
                cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
                cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);
                System.setProperty("http.nonProxyHosts", "localhost");
                cap.setCapability(CapabilityType.PROXY, proxy);
                driver = new ChromeDriver(cap);
            } else {
                driver = new ChromeDriver();
            }

            // 全局等待时间
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            // 显示等待时间
            WebDriverWait webDriverWait = new WebDriverWait(driver, 10);

            driver.get(REGISTER_URL);//打开指定的网站
            String memberName = RegisterUtil.getMemberName();

            webDriverWait.until(d -> isElementPresent(d, By.id("MemberName"))).sendKeys(memberName);
            sleep();
            webDriverWait.until(d -> isElementPresent(d, By.id("iSignupAction"))).click();
            sleep();

            String password = RegisterUtil.getPassword();
            webDriverWait.until(d -> isElementPresent(d, By.id("PasswordInput"))).sendKeys(password);
            sleep();

//            driver.findElement(By.id("iSignupAction")).click(); //点击按扭
            webDriverWait.until(d -> isElementPresent(d, By.id("iSignupAction"))).click();
            sleep();

//            driver.findElement(By.id("LastName")).sendKeys(RegisterUtil.getLastName());
//            driver.findElement(By.id("FirstName")).sendKeys(RegisterUtil.getFirstName());
            webDriverWait.until(d -> isElementPresent(d, By.id("LastName"))).sendKeys(RegisterUtil.getLastName());
            webDriverWait.until(d -> isElementPresent(d, By.id("FirstName"))).sendKeys(RegisterUtil.getFirstName());
            sleep();

//            driver.findElement(By.id("iSignupAction")).click(); //点击按扭
            webDriverWait.until(d -> isElementPresent(d, By.id("iSignupAction"))).click();
            sleep();
            RegisterUtil.BirthDate birthDate = RegisterUtil.getBirthDate();
//            driver.findElement(By.id("BirthYear")).sendKeys(birthDate.year);
//            driver.findElement(By.id("BirthMonth")).sendKeys(birthDate.month);
//            driver.findElement(By.id("BirthDay")).sendKeys(birthDate.day);
            webDriverWait.until(d -> isElementPresent(d, By.id("BirthYear"))).sendKeys(birthDate.year);
            webDriverWait.until(d -> isElementPresent(d, By.id("BirthMonth"))).sendKeys(birthDate.month);
            webDriverWait.until(d -> isElementPresent(d, By.id("BirthDay"))).sendKeys(birthDate.day);
            sleep();

//            driver.findElement(By.id("iSignupAction")).click(); //点击按扭
            webDriverWait.until(d -> isElementPresent(d, By.id("iSignupAction"))).click();

            // 验证码
//            driver.findElement(By.xpath("//div[@id='hipTemplateContainer']//input")).sendKeys("");
//            driver.findElement(By.id("iSignupAction")).click(); //点击按扭

            System.out.println("Register success, memberName: " + memberName + ",password: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (driver != null) {
//                driver.quit();//退出浏览器
//            }
        }
    }

    public void sleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(RegisterUtil.getSleepTime());
    }
}
