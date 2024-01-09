package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * @author zhangdongdong
 */
public class WeWorkApp extends BasePage{

    private final By contactList = AppiumBy.xpath("//*[@text='通讯录']");

    /**
    * 无参构造方法默认初始化driver
    * */
    public WeWorkApp(){
        UiAutomator2Options options = new UiAutomator2Options()
                //设置自动化的平台是Android
                .setPlatformName("Android")
                //声明自动化工具名称
                .setAutomationName("uiautomator2")
                //声明设备ID
                .setDeviceName("VAUBB23907002613")
                //配置APP的包名
                .setAppPackage("com.tencent.wework")
                //配置APP启动的Activity
                .setAppActivity(".launch.LaunchSplashActivity")
                //不重置APP的信息，解决登录的问题
                .setNoReset(true)
                //强制
                .amend("appium:forceAppLaunch", true)
                .amend("appium:shouldTerminateApp", true);

        try {
            //初始化Android的driver
            driver = new AndroidDriver(
                    new URL("http://127.0.0.1:4723/wd/hub"), options
            );
            //隐式等待：全局变量设置一次，所有find操作都可以使用
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * 有参构造方法子类调用时直接把driver传递给子类,子类不用重新new一个driver
    * */
    public WeWorkApp(AndroidDriver androidDriver){
        driver = androidDriver;
    }

    /**
    * 返回到企业微信主页面
    * */
    public void backToMainPage(){
        ExpectedCondition<WebElement> condition = input -> {
            //向上返回一次
            driver.navigate().back();

            return findElement(contactList);//查找通讯录元素
        };
        //显示等待30s内查找元素，每隔1秒执行一次，直到找到该元素，超过30s未找到则报错
        waitUntil().until(condition);
    }





}
