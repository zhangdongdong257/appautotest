package com.tencent.wework;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;

/**
 * @author zhangdongdong
 */
public class BasePage {

    /**
     * 声明一个Android的driver
     * */
    public AndroidDriver driver;

    /**
     * 声明全局的日志类
     * */
    public static final Logger log = getLogger(lookup().lookupClass());

    /**
     * 声明一个显示等待
     * */
    public WebDriverWait waitUntil(){
        //最长等待60秒，每隔5秒轮询一次
        return new WebDriverWait(driver, Duration.ofSeconds(60), Duration.ofSeconds(5));
    }


    /**
    * 查找单个元素
    * */
    public WebElement findElement(By by){
        log.debug("查找单个元素————————————————》{}", by);
        return driver.findElement(by);
    }

    /**
     * 查找多个元素
     * */
    public List<WebElement> findElements(By by){
        log.debug("查找多个元素————————————————》{}", by);
        return driver.findElements(by);
    }


    /**
    * 截图方法
    * */
    public void screentShotAllure(String imageName) throws FileNotFoundException {
        //调用截图方法
        File screentShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并格式化时间
        String currentTimeStamp = sdf.format(new Date());
        System.out.println(currentTimeStamp);
        //把截图添加到allure报告中
        Allure.addAttachment(imageName+currentTimeStamp, "image/png", new FileInputStream(screentShot), ".png");
    }

    /**
     * 查找元素并截图
     * @param by 查找元素方式
     * @param flag 是否截图
     * @return 元素
     */
    public WebElement findElementAndscreentShot(By by, boolean flag){
        WebElement element = driver.findElement(by);
        if(flag){
            try {
                log.info("被截图的元素为:{}", by);
                screentShotAllure(by.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return element;
    }

    /**
     * 以时间命名截图
     */
    public void screenShot() throws FileNotFoundException {
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //声明日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //获取当前时间并格式化时间
        String currentTimeStamp = sdf.format(new Date());
        System.out.println(currentTimeStamp);
        Allure.addAttachment(currentTimeStamp, "image/png",new FileInputStream(screen).toString(), ".jpg");
    }



    /**
    * 屏幕向下滑动
    * */
    public void scrollDown(){
        //获取屏幕的高度和宽度
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();

        //设置开始坐标
        int startX = screenHeight/3;
        int startY = (int)(screenHeight * 0.8);

        //设置结束坐标
        int endX = screenWidth/2;
        int endY = (int)(screenHeight * 0.2);

        //  longPress -> 长按  moveTo -》移动到 某个位置
        new TouchAction(driver).longPress(PointOption.point(startX, startY))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform();

    }

    public void quit(){
        driver.quit();
    }

}
