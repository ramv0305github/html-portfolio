package com.walmart.sams.veda.Selenium;

import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;

import java.net.HttpURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.collections4.CollectionUtils.collect;

public class FirstBrowser
{
    @Test
    public void FirstTest() throws IOException {
        System.out.println("First Test");
        WebDriverManager.edgedriver().setup();
        System.setProperty("webdriver.chrome.driver","C:/Users/vn54joi/Documents/chromedriver");
        WebDriver driver = new EdgeDriver();
        driver.get("https://google.com");
        driver.manage().window().maximize();// window maximize
        driver.findElement(By.name("q")).sendKeys("Selenium");
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        driver.findElement(By.name("qa")).getText();
        driver.quit();//closes all the browser windows opened by web driver
        driver.manage().deleteAllCookies();//cookies delete :

        //Synchronisation
        //thread.sleep();// causes WebDriver to wait for a specific time but slows the execution
        driver.manage().timeouts().implicitlyWait(5, SECONDS);//Implicit wait waits for the defined time
        // and resumes in case the condition is met before the wait duration.
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'COMPOSE')]")));
        // the WebDriver is directed to wait until a certain condition occurs
        Wait wait1 = new FluentWait(driver) .withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5)).ignoring((Exception.class));

        //Mouse over Interactions click double click
        Actions a = new Actions(driver);
        WebElement element = driver.findElement(By.linkText("Get started free"));
        a.moveToElement(element).click();
        a.moveToElement(element).doubleClick().perform();
        a.moveToElement(element).click().keyDown(Keys.ARROW_UP).perform();

        // right click on an element thru Actions class
        a.moveToElement(element).contextClick().build().perform();

        //Window handling how to handle window popup,child window etc
        Set<String> parent=driver.getWindowHandles();
        // Now iterate using Iterator
        Iterator<String> I1= parent.iterator();
        while(I1.hasNext())
        {
            String child_window=I1.next();
            if(!parent.equals(child_window))
            {
                driver.switchTo().window(child_window);
                System.out.println(driver.switchTo().window(child_window).getTitle());
                driver.close();
                driver.switchTo().window(parent.toString());
            }
        }

        // iframes
        List<WebElement> iframeElements = driver.findElements(By.tagName("iframeResult"));
        driver.switchTo().frame(iframeElements(0));

        //Switching back to the main window
        driver.switchTo().defaultContent();

        //links count
        System.out.println(driver.findElements(By.tagName("a")).size());//count of links in the page.

        // Screen shots
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src,new File("C:\\Users\\rahul\\screenshot.png"));

        // drop down lists select command for dropdown lists etc
        Select objSelect =new Select(driver.findElement(By.id("search-box")));
        objSelect.selectByVisibleText("Automation");
        objSelect.selectByIndex(4);
        objSelect.selectByValue("Automation Testing");

        List <WebElement> elementCount = objSelect.getOptions();//This method gets all the options belonging to the Select tag
        // returns web element.
        System.out.println(elementCount.size());
        objSelect.deselectAll();
        objSelect.isMultiple();

        //Auto suggestive drop down
        List<WebElement> options =driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
        for(WebElement option :options){
            if(option.getText().equalsIgnoreCase("India"));
        }
        // checkboxes in selenium
        driver.findElement(By.id("hobbies-checkbox-1")).click();

        //Handling alerts
        driver.findElement(By.id("confirmbtn")).click();
        driver.switchTo().alert().accept();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().dismiss();

        // Scrolling in webpages using javascriptexecutor
        JavascriptExecutor js = new JavascriptExecutor() {@Override public Object executeScript(String script, Object... args) {
                return null; }
            @Override public Object executeAsyncScript(String script, Object... args) {
                return null;
            }
        };
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollTop=(500)");

        //Handling https certifications
        //SSl certificates //Desired capabilities=//general chrome profile
        DesiredCapabilities ch=DesiredCapabilities.chrome();
        ch.acceptInsecureCerts();
        ch.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        ch.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        // setting up proxy
        ChromeOptions options1 = new ChromeOptions();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("ipaddress:4444");
        options1.setCapability("proxy", proxy);

        //checking broken links
        String url = driver.findElement(By.xpath("a=yt")).getAttribute("href");
        HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
        conn.setRequestMethod("HEAD");
        conn.connect();
        int respCode = conn.getResponseCode();
        System.out.println(respCode);
        if (respCode>400)
        {
            System.out.println("The link with Text is broken with code" +respCode);
        }

        //Assertions Hard and soft
        Assert.assertTrue(false); // Hard assert script will fail if assertion fails.
        String Expected = null;
        String Actual = null;
        Assert.assertEquals(Expected,Actual);//Hard assertions
        //soft assertions are also called as verify command.
        SoftAssert a1 =new SoftAssert();
        SoftAssert b = null;
        a1.assertTrue(a1==b); // soft assertion proceed with test.
        a1.assertAll();// keep at end of the test to view assertion result.

        // Browser navigations
        driver.navigate().back(); // browser clicks on the back button in the existing browser window.
        driver.navigate().refresh();//refresh/reloads the current web page in the existing browser window
        driver.navigate().forward(); //forward button in the existing browser window
        driver.navigate().to("www.google.com");//loads a new page

        // KeyBoard controls We can perform key press of (CTRL+A) with Selenium Webdriver.
       //  We can use the Keys.chord() method to simulate this keyboard action
        String s = Keys.chord(Keys.CONTROL, "a");
        WebElement l = driver.findElement(By.id("gsc-i-id1"));
        // enter text then ctrl+a
        l.sendKeys("Selenium");
        l.sendKeys(Keys.CONTROL+"A");

        // Java Streams usage in selenium
        // Refer Lamdademo in java programs

        //navigating to new window
        // driver.switchTo().newWindow(SafariDriver.WindowType.TAB); New selenium feature
        String childWindow = null;
        driver.switchTo().window(childWindow); // control is there in the new window now

        //GEt Height & Width of webelement
        WebElement name = null;
        System.out.println(name.getRect().getDimension().getHeight());
        System.out.println(name.getRect().getDimension().getWidth());

}
    private int iframeElements(int i) {
        return 0;
    }
}
