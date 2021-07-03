package com.test.base;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.utils.WebEventListener;
import com.test.utils.YamlUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.yaml.snakeyaml.Yaml;

/**
 * 公共的底层类，用于简化WebDriver的配置，对页面操作进行监听
 */
public class TestBase {

    public static long PAGE_LOAD_TIMEOUT = 10;
    public static long IMPLICIT_WAIT = 10;

    public static WebDriver driver;
    public static JSONObject jsonObject;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;

    public TestBase(){
        try {
            String path =  TestBase.class.getClassLoader().getResource("config.yml").getPath();
            Yaml yaml = new Yaml();
            File file = new File(path);
            Object result = yaml.load(new FileInputStream(file));
            jsonObject = JSONObject.parseObject(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initialization(){
        String browserName = (String) YamlUtils.getValue("base.browser");

        if(browserName.equals("chrome")){
            // TODO 参考火狐浏览器
            driver = new ChromeDriver();
        }
        else if(browserName.equals("FF")){
            // 通过相对路径获取启动器的路径
            String initiator = TestBase.class.getClassLoader().getResource("geckodriver.exe").getPath();
            System.setProperty("webdriver.gecko.driver",initiator);
            driver = new FirefoxDriver();
        }


        e_driver = new EventFiringWebDriver(driver);
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);

        // 设置固定分辨率
        Dimension dimension = new Dimension(1382,744);
        driver.manage().window().setSize(dimension);

        String url = (String) YamlUtils.getValue("base.url");
        driver.get(url);
    }

}