package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

/**
 * @author zhangdongdong
 */
public class ManuallyAddContactsPage extends WeWorkApp{

    /**
     * 声明一个名字输入框
     */
    private final By nameInput = AppiumBy.id("com.tencent.wework:id/c69");

    /**
     * 声明一个电话号码输入框
     */
    private final By mobileNumberInput = AppiumBy.id("com.tencent.wework:id/inv");

    /**
     * 声明一个保存并继续的按钮
     */
    private final By saveAndContinueButton = AppiumBy.id("com.tencent.wework:id/b47");

    /**
     * 声明一个添加成功的toast
     */
    private final By successMessage = AppiumBy.xpath("//*[@class='android.widget.Toast']");

    /**
     * 声明一个搜索失败的提示文案
     */
    private final By failText = AppiumBy.id("com.tencent.wework:id/ct7");


    /**
     * 有参的构成方法
     * @param driver AndroidDriver的驱动
     */
    public ManuallyAddContactsPage(AndroidDriver driver){
        super(driver);
    }

    /**
     * 添加成员
     * @param name 添加姓名
     * @return String 添加成功
     */
    public String addContact(String name){
        //点击姓名输入框，输入姓名
        findElement(nameInput).sendKeys(name);
        //返回当前时间戳
        String account = Long.toString(System.currentTimeMillis());
        //截取11位手机号
        String phone = account.substring(0, 11);
        //找到手机号输入框，输入手机号
        findElement(mobileNumberInput).sendKeys(phone);
        //点击保存并继续按钮
        findElement(saveAndContinueButton).click();

        //获取添加成功的toast提示
        String addSuccess = findElement(successMessage).getText();
        return addSuccess;
    }

    /**
     * 添加成功失败
     * @param name 添加姓名
     * @param mobileNumber 手机号码
     * @return String 添加失败的toast提示
     */
    public String addContactFail(String name, String mobileNumber){
        //点击姓名输入框，输入姓名
        findElement(nameInput).sendKeys(name);
        //输入已存在的手机号
        findElement(mobileNumberInput).sendKeys(mobileNumber);
        //点击保存并继续按钮
        findElement(saveAndContinueButton).click();

        //获取添加失败的toast提示
        String addFail = findElement(failText).getText();
        return addFail;
    }

}
