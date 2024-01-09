package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * @author zhangdongdong
 */
public class AddContactsPage extends WeWorkApp{

    /**
     * 声明手动添加按钮
     */

    private final By manuallyAddButton =  AppiumBy.xpath("//android.widget.TextView[@text='手动输入添加']");

    /**
     * 声明一个有参的构造方法
     * @param driver Androidriver的驱动
     */
    public AddContactsPage(AndroidDriver driver){
        super(driver);
    }

    public ManuallyAddContactsPage navigateToManuallyAddContactsPage(){
        //点击手动输入添加
        findElement(manuallyAddButton).click();
        return  new ManuallyAddContactsPage(driver);
    }




}
