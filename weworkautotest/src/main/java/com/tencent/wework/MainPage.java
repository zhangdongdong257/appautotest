package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;

/**
 * @author zhangdongdong
 */
public class MainPage extends WeWorkApp{

    /**
    * 跳转到联系人页面
    * */
    @Step("跳转到联系人页面")
    public ContactsPage navigateToContactsPage(){
        //查找通讯录
        findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        return new ContactsPage(driver);
    }
}
