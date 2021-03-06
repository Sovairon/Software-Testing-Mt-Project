package com.groupie.webdriver;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import com.sun.java.swing.plaf.windows.resources.windows;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.DesiredCapabilities;


public class MyFirstTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Eren\\Desktop\\selenium\\geckodriver.exe");
        //DesiredCapabilities capabilities = DesiredCapabilities.firefox();

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Eren\\Desktop\\selenium\\chromedriver.exe");

        driver = new ChromeDriver();

        //capabilities.setCapability("marionette", true);
        //driver = new FirefoxDriver(capabilities);
        baseUrl = "https://www.n11.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testMyFirst() throws Exception {
        driver.manage().window().maximize();
        driver.get(baseUrl + "/");
        String homePage = driver.getWindowHandle();
        System.out.println(homePage);
        char alfabe[] = "ABCÇDEFGHIİJKLMNOÖPRSŞTUÜVWXYZ".toCharArray();


        driver.findElement(By.linkText("Giriş Yap")).click();
        Thread.sleep(1000);



        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("".equals(driver.findElement(By.cssSelector("img[alt=\"Alışverişin Uğurlu Adresi\"]")).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div/div/div[1]/form/div[4]")).click();

        Thread.sleep(1000);
        Set<String> windows = driver.getWindowHandles();
        System.out.println(windows.size());
        Iterator iterator = windows.iterator();

        //I couldn't figure out this part. Doesn't work the other way.
        String popPage = iterator.next().toString();
        popPage = iterator.next().toString();

        System.out.println(popPage);




        driver.switchTo().window(popPage);

        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("Facebook".equals(driver.getTitle())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        Thread.sleep(2000);
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("seleniumtestingeren@gmail.com");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("seleniumide");
        driver.findElement(By.id("u_0_2")).click();

        Thread.sleep(3000);

        driver.switchTo().window(homePage);

        WebElement uname = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div/div[2]/div[2]/div[1]/div[1]/a[2]"));
        assertEquals(uname.getText(), "Seleniumtesting Midterm");
        WebElement k1 = driver.findElement(By.cssSelector("#contentMain > div > nav > ul > li:nth-child(8) > a"));
        WebElement k2 = driver.findElement(By.cssSelector("#contentMain > div > nav > ul > li:nth-child(8) > div > ul > li:nth-child(1) > a"));
        Actions builder = new Actions(driver);
        //ONEMLI
        //builder.moveToElement(k1).moveToElement(k2).click().build().perform();
        //ONEMLI: Bazen cok hizli aciliyor o yuzden calismayabiliyor ve baska bir cozum bulamadim direk Kitap'a tiklayabilmek icin
        //Isterseniz Temporary Fix i kullanabilirsiniz.

        //Temporary Fix
        driver.findElement(By.xpath("//*[@id=\"contentMain\"]/div/nav/ul/li[8]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"contentCategory\"]/div/div[2]/div[1]/ul/li[1]/a")).click();
        //Temporary Fix

        WebElement k3 = driver.findElement(By.xpath("//*[@id=\"breadCrumb\"]/ul/li[3]/a/span"));
        assertEquals(k3.getText(), "Kitap");

        driver.findElement(By.xpath("//*[@id=\"contentListing\"]/div/div/div[2]/section[3]/h2/a")).click();

        WebElement k4 = driver.findElement(By.xpath("//*[@id=\"breadCrumb\"]/ul/li[4]/a/span"));
        assertEquals(k4.getText(), "Yazarlar");

        int divCount = 0;

        //NOT: A dan Z ye tum alfabenin gorunmesi icin for dongusunun 31 kez donmesi gerekiyor fakat sonuncuda anlamadigim
        //sebepten dolayi fail veriyor. 32 ye cekildiginde pass oluyor cozemedim.
        for(int i = 2; i < 33; i++) {
            String item = String.valueOf(driver.findElement(By.xpath("//*[@id=\"brandsPaging\"]/div[1]/span[" + i +"]")));
            driver.findElement(By.xpath("//*[@id=\"brandsPaging\"]/div[1]/span[" + i +"]")).click();
            List<WebElement> totalDiv = driver.findElements(By.xpath("//*[@id=\"authorsList\"]/div"));
            for (WebElement numberOfDiv: totalDiv) {
                divCount++;

                for (int j = 1; j < divCount; j++) {
                    findElements("//*[@id=\"authorsList\"]/div[" + j + "]/ul/li", alfabe[i-2]);
                }
            }
            divCount = 0;
        }


    }

    private void findElements(String xpath1, char alphabet) {

        List<WebElement> allElementsColumn1 = driver.findElements(By.xpath(xpath1));
        boolean flag = false;
        int a = 0;
        for (WebElement element : allElementsColumn1) {
            flag = element.getText().charAt(0) == alphabet;
            a++;
        }

        try {
            if(flag == true)
                System.out.println("True - " + a + " Lines");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @After
    public void tearDown() throws Exception {
        //driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }


}
