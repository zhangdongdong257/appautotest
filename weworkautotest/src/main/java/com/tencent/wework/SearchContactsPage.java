package com.tencent.wework;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author zhangdongdong
 */
public class SearchContactsPage extends WeWorkApp{

    /**
     * 声明搜索按钮
     */
    private final By searchButton = AppiumBy.id("com.tencent.wework:id/k24");

    /**
     * 声明搜索列表
     */
    private final By memberList = AppiumBy.xpath("//*[@text='张三']");

    /**
     * 声明搜索失败的文本
     */
    private final By searchFailText = AppiumBy.xpath("//*[@text='没有找到相关结果']");

    /**
     * 有参构造方法
     * @param driver AndroidDriver的驱动
     */
    public SearchContactsPage(AndroidDriver driver){
        super(driver);
    }

    /**
     * 搜索出成员列表
     * @param name 搜索用户名称
     * @return List<WebElement> 成员列表
     */
    public List<WebElement> searchContacts(String name){
        findElement(searchButton).sendKeys(name);
        return findElements(memberList);
    }

    /**
     * 搜索失败
     * @param name 搜索用户名称
     * @return String 返回失败提示文案
     */
    public String searchContactsFail(String name){
        //输入搜索的用户名
        findElement(searchButton).sendKeys(name);
        //获取搜索失败的文本
        String result = findElement(searchFailText).getText();
        return result;
    }










}
