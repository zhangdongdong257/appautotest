package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

/**
 * @author zhangdongdong
 */
public class MainPage extends WeWorkApp{

    /**
    * 跳转到联系人页面
    * */
    public ContactsPage navigateToContactsPage(){
        //查找通讯录
        findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        return new ContactsPage(driver);
    }
}
