import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.offset.ElementOption;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMember {

    //AppiumServer版本2.3.0
    //前置条件登录成功
    private static  AndroidDriver driver;//声明一个AndroidDriver

    public static WebDriverWait wait;//声明一个显示等待的wait

    @BeforeAll
    public static void setUp() {
        UiAutomator2Options options = new UiAutomator2Options()
                //设置自动化的平台是Android
                .setPlatformName("Android")
                //声明自动化工具名称
                .setAutomationName("uiautomator2")
                //声明设备ID
                .setDeviceName("VAUBB23907002613")
                //配置APP的包名
                .setAppPackage("com.tencent.wework")
                //配置APP启动的Activity
                .setAppActivity(".launch.LaunchSplashActivity")
                //不重置APP的信息，解决登录的问题
                .setNoReset(true)
                //强制
                .amend("appium:forceAppLaunch", true)
                .amend("appium:shouldTerminateApp", true);

        try {
            driver = new AndroidDriver(
                    new URL("http://127.0.0.1:4723/wd/hub"), options
            );
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            wait = new WebDriverWait(driver, Duration.ofSeconds(30),Duration.ofSeconds(3));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    @Description("每次执行完后回退到主页面")
    public void mainPage(){
        // 使用for 循环实现，一直找通讯录页面，如果找不到则回退，直到找到为止。
        // 三种等待的区别： 强制等待-> 在代码线程中做了强制阻塞 Thread.sleep()
        // 隐式等待：driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //    全局变量，设置一次，所有的find 操作都可以生效
        // 显式等待:  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // 显示等待： 传入一个函数进去。循环执行，如果报错，则做异常捕获，直到抛出超时为止。
        // 条件检查函数
        ExpectedCondition<WebElement> condition = input -> {
            driver.navigate().back();
            return driver.findElement(AppiumBy.xpath("//*[@text='通讯录']"));
        };
        wait.until(condition);

//        while(true) {
//            try {
//                WebElement element = driver.findElement(AppiumBy.xpath("//*[@text='通讯录']"));
//                if( element != null){
//                    break;
//                }
//            }catch (Exception e){
//                driver.navigate().back();
//            }
//        }

    }


    @Test
    @DisplayName("添加新成员")
    public void addMemberTest() {
        driver.findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
//        for (int i = 0; i < 10; i++) {
//            try {
//                driver.findElement(AppiumBy.xpath("//*[@text='添加成员']")).click();
//            } catch (Exception e) {
//                scrollDown();
//            }
//        }
        ExpectedCondition<String> add = input -> {
            scrollDown();
            driver.findElement(AppiumBy.xpath("//*[@text='添加成员']")).click();
            return "success";
        };
        wait.until(add);

        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='手动输入添加']")).click();

        driver.findElement(AppiumBy.id("com.tencent.wework:id/c69")).sendKeys("张三");
        String account = Long.toString(System.currentTimeMillis());
        //截取11位手机号
        String phone = account.substring(0, 11);
        driver.findElement(AppiumBy.id("com.tencent.wework:id/inv")).sendKeys(phone);
        driver.findElement(AppiumBy.id("com.tencent.wework:id/b47")).click();

        String addSuccess = driver.findElement(AppiumBy.xpath("//*[@class='android.widget.Toast']")).getText();

        assertEquals("添加成功", addSuccess);

    }

    @Test
    @DisplayName("添加新成员失败")
    public void addMemberFailTest() throws FileNotFoundException {
        driver.findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        ExpectedCondition<String> add = input -> {
            scrollDown();
            driver.findElement(AppiumBy.xpath("//*[@text='添加成员']")).click();
            return "success";
        };
        wait.until(add);

        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='手动输入添加']")).click();

        driver.findElement(AppiumBy.id("com.tencent.wework:id/c69")).sendKeys("张三");
        driver.findElement(AppiumBy.id("com.tencent.wework:id/inv")).sendKeys("13758192141");
        driver.findElement(AppiumBy.id("com.tencent.wework:id/b47")).click();

        String addFail = driver.findElement(AppiumBy.id("com.tencent.wework:id/ct7")).getText();
        screenShot();
        assertEquals("手机已存在于通讯录，无法添加", addFail);

    }

    @Test
    @DisplayName("搜索用户")
    public void searchTest(){
        driver.findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        driver.findElement(AppiumBy.id("com.tencent.wework:id/lvi")).click();
        driver.findElement(AppiumBy.id("com.tencent.wework:id/k24")).sendKeys("张三");

        try {
            screenShot();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<WebElement>   names = driver.findElements(AppiumBy.xpath("//*[@text='张三']"));
        assertTrue(names.stream().allMatch(name -> name.getText().contains("张三")));
    }



    //传入参数的截图
    public void screenShot(String fileName) throws FileNotFoundException {
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Allure.addAttachment(fileName, "image/png",new FileInputStream(screen).toString(), ".jpg");
    }

    //以时间命名截图
    public void screenShot() throws FileNotFoundException {
        File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String currentTimeStamp = sdf.format(new Date());
        System.out.println(currentTimeStamp);
        Allure.addAttachment(currentTimeStamp, "image/png",new FileInputStream(screen).toString(), ".jpg");
    }


    public void scrollDown(){
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();

        int startX = width / 3;
        int startY = (int)(height * 0.7);

        int endX = width / 2;
        int endY = (int)(height * 0.3);

        TouchAction touchAction = new TouchAction(driver);
        //  longPress -> 长按  moveTo -》移动到 某个位置
        touchAction.longPress(ElementOption.point(startX, startY))
                .moveTo(ElementOption.point(endX, endY))
                .release()
                .perform();

    }

    @AfterAll
    public  static  void tearDown() {
        driver.quit();
    }
}
