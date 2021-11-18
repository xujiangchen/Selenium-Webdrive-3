package com.test.utils;

import com.test.base.TestBase;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 用于处理测试过程中各种图片的工具类
 */
public class ScreensUtils extends TestBase {

    /**
     * 如果Webdriver发生异常自动截图并放入测试报告
     */
    public static void takeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        String path = currentDir + "/screenshots/" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(scrFile, new File(path));
        try(InputStream is = Files.newInputStream(Paths.get(path))){
            Allure.addAttachment("测试截图",is);
        }
    }

    /**
     * Allure报告的截图
     */
    public static void takeScreenAllure(String name) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String currentDir = System.getProperty("user.dir");
        String path = currentDir + "/screenshots/allure/" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(scrFile, new File(path));
        try(InputStream is = Files.newInputStream(Paths.get(path))){
            Allure.addAttachment(name,is);
        }
    }

    /**
     * 两个图片进行精确对比
     */
    public static boolean compareImage(String expectedImgPath, String actualImgPath) throws Exception{
        boolean result = true;
        // 期待结果截图
        File expectImage = new File(expectedImgPath);
        // 实际结果截图
        File actualImage = new File(actualImgPath);
        // 期待截图结果信息
        BufferedImage bufferedExpectImg = ImageIO.read(expectImage);
        DataBuffer dataBufferExpectImg = bufferedExpectImg.getData().getDataBuffer();
        int exceptImgSize = dataBufferExpectImg.getSize();
        // 实际截图结果信息
        BufferedImage bufferedActualImg = ImageIO.read(expectImage);
        DataBuffer dataBufferActualImg = bufferedActualImg.getData().getDataBuffer();
        int actualImgSize = dataBufferActualImg.getSize();
        if(actualImgSize == exceptImgSize){
            for (int i = 0; i < actualImgSize; i++){
                if(dataBufferActualImg.getElem(i) != dataBufferExpectImg.getElem(i)){
                    result = false;
                    break;
                }
            }
        }else {
            result = false;
        }
        return result;
    }


}
