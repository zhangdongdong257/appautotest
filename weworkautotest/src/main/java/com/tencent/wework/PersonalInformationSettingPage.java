package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * @author zhangdongdong
 */
public class PersonalInformationSettingPage extends WeWorkApp{

    /**
     * 声明一个编辑按钮
     */
    private final By editButton = AppiumBy.id("com.tencent.wework:id/cd4");

    /**
     * 有参的构成方法
     * @param driver AndroidDriver的驱动
     */
    public PersonalInformationSettingPage(AndroidDriver driver){
        super(driver);
    }

    /**
     * 跳转编辑成员页面
     * @return
     */
    public EditMemberPage navigateToEditMemberPage(){
        //点击编辑成功按钮
        findElement(editButton).click();
        return new EditMemberPage(driver);
    }

}
