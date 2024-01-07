import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.offset.ElementOption;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

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

public class SampleTest {

    private static  AndroidDriver driver;

    @BeforeAll
    public static void setUp() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("VAUBB23907002613")
                .setAppPackage("com.tencent.wework")
                .setAppActivity(".launch.LaunchSplashActivity")
                .setNoReset(true);

        try {
            driver = new AndroidDriver(
                    new URL("http://127.0.0.1:4723/wd/hub"), options
            );
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Description("添加新成员")
    public void sampleTest() {
        driver.findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        for (int i = 0; i < 10; i++) {
            try {
                driver.findElement(AppiumBy.xpath("//*[@text='添加成员']")).click();
            } catch (Exception e) {
                scrollDown();
            }
        }

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
    @Description("搜索用户")
    public void searchTest(){
        driver.findElement(AppiumBy.xpath("//*[@text='通讯录']")).click();
        driver.findElement(AppiumBy.id("com.tencent.wework:id/lvi")).click();
        driver.findElement(AppiumBy.id("com.tencent.wework:id/k24")).sendKeys("张三");

        try {
            screenShot();
            Thread.sleep(10000);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
