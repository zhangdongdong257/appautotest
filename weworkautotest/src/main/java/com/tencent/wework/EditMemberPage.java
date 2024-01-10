package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author zhangdongdong
 */
public class EditMemberPage extends WeWorkApp{

    /**
     * 声明一个有参的构造方法
     * @param driver Androidriver的驱动
     */
    public EditMemberPage(AndroidDriver driver){
        super(driver);
    }

    /**
     * 删除联系人
     * @return String "删除成功"
     */
    @Step("删除联系人")
    public String deleteContact(){
        ExpectedCondition<String> delete = input -> {
            //向下滑动
            scrollDown();
            //查找添加元素
            findElement(AppiumBy.xpath("//*[@text='删除成员']")).click();
            return "success";
        };
        //向下滑动直到找到添加成员
        waitUntil().until(delete);
        findElement(AppiumBy.id("com.tencent.wework:id/d1b")).click();
        return "删除成功";
    }
}
