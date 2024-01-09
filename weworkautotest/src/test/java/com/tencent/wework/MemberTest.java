package com.tencent.wework;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemberTest {

    /**
     * 声明一个主页对象
     */
    public static MainPage mainPage;


    /**
     * new一个新的主页对象
     */
    @BeforeAll
    public static void setUpClass(){
        mainPage = new MainPage();
    }

    /**
     * 每个测试方法执行完成后返回主页
     */
    @AfterEach
    public void backToMain(){
        mainPage.backToMainPage();
    }

    @Test
    @DisplayName("搜索用户")
    public void searchContacts(){
        List<WebElement> contacts = mainPage
                .navigateToContactsPage()
                .navigateToSearchContactsPage()
                .searchContacts("张三");

        assertTrue(contacts.stream().allMatch(name -> name.getText().contains("张三")));
    }

    @Test
    @DisplayName("搜索用户失败")
    public void searchContactFail(){
        String res = mainPage
                .navigateToContactsPage()
                .navigateToSearchContactsPage()
                .searchContactsFail("李四");
        assertEquals("没有找到相关结果", res);
    }

    @Test
    @DisplayName("添加用户")
    public void addContact(){
        String res = mainPage
                .navigateToContactsPage()
                .navigateToAddContactsPage()
                .navigateToManuallyAddContactsPage()
                .addContact("张三");

        assertEquals("添加成功", res);
    }

    @Test
    @DisplayName("添加用户失败")
    public void addContactFail() throws FileNotFoundException {
        String res = mainPage
                .navigateToContactsPage()
                .navigateToAddContactsPage()
                .navigateToManuallyAddContactsPage()
                .addContactFail("张三", "13758192141");
        mainPage.screentShotAllure("添加失败");
        assertEquals("手机已存在于通讯录，无法添加", res);
    }

    @Test
    @DisplayName("删除用户")
    public void deleteContact(){
        String res = mainPage
                .navigateToContactsPage()
                .navigateToAddContactsPage()
                .navigateToManuallyAddContactsPage()
                .addContact("张三");

        mainPage.backToMainPage();

        String res2 = mainPage
                .navigateToContactsPage()
                .navigateToPersonalInformationPage("张三")
                .navigateToPersonalInformationSettingPage()
                .navigateToEditMemberPage()
                .deleteContact();

        assertEquals("删除成功", res2);
    }


    @AfterAll
    @DisplayName("driver关闭")
    public static void tearDown() {
        mainPage.driver.quit();
    }




}
