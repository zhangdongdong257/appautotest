package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * @author zhangdongdong
 */
public class PersonalInformationPage extends WeWorkApp{

    /**
     * 声明详情按钮
     */
    private final By  detailsButton = AppiumBy.id("com.tencent.wework:id/lv8");

    /**
     * 有参的构成方法
     * @param driver AndroidDriver的驱动
     */
    public PersonalInformationPage(AndroidDriver driver){
        super(driver);
    }

    /**
     * 跳转个人信息设置页面
     * @return 个人信息设置页面对象
     */
    public PersonalInformationSettingPage navigateToPersonalInformationSettingPage(){
        //点击详情按钮
        findElement(detailsButton).click();
        return new PersonalInformationSettingPage(driver);
    }

}
