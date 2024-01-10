package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author zhangdongdong
 */
public class ContactsPage extends WeWorkApp{

    /**
     * 声明搜索图标元素
     */
    private final By searchImage = AppiumBy.id("com.tencent.wework:id/lvi");


    /**
     * 声明添加成员元素
     */
    private final By addContacts = AppiumBy.xpath("//*[@text='添加成员']");


    /**
     * 声明一个有参的构造方法
     * @param driver Androidriver的驱动
     */
    public ContactsPage(AndroidDriver driver){
        //使用父类的构造方法使用同一个driver
        super(driver);
    }

    /**
     * 跳转搜索页面
     * @return SearchContactsPage对象
     */
    @Step("跳转搜索页面")
    public SearchContactsPage  navigateToSearchContactsPage(){
        //点击搜索图标
        findElement(searchImage).click();
        return new SearchContactsPage(driver);
    }

    /**
     * 跳转添加页面
     * @return AddContactsPage对象
     */
    @Step("跳转添加页面")
    public AddContactsPage navigateToAddContactsPage(){
        ExpectedCondition<String> add = input -> {
            //向下滑动
            scrollDown();
            //查找添加元素
            findElement(addContacts).click();
            return "success";
        };
        //向下滑动直到找到添加成员
        waitUntil().until(add);
        return new AddContactsPage(driver);
    }


    /**
     * 搜索出成员列表
     * @param name 搜索用户名称
     * @return List<WebElement> 成员列表
     */
    @Step("搜索出成员列表")
    public PersonalInformationPage navigateToPersonalInformationPage(String name){
        ExpectedCondition<String> deleteName = input -> {
            //向下滑动
            scrollDown();
            //查找添加元素
            findElements(AppiumBy.xpath("//*[@text='"+name+"']")).get(0).click();

            return "success";
        };
        //向下滑动直到找到添加成员
        waitUntil().until(deleteName);
        return new PersonalInformationPage(driver);
    }

}
