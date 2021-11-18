package com.test.utils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotUtils {

    /**
     * CV大法
     */
    public static void CV(String data){
        // StringSelection用于传输选择的文本
        StringSelection stringSelection = new StringSelection(data);
        // 将信息放入剪切板中
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        Robot robot = null;
        try{
            robot = new Robot();
        }catch (Exception e){
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);

        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    /**
     * 模拟Tab
     */
    public static void pressTab(){
        Robot robot = null;
        try{
            robot = new Robot();
        }catch (Exception e){
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }

    /**
     * 模拟回车
     */
    public static void pressEnter(){
        Robot robot = null;
        try{
            robot = new Robot();
        }catch (Exception e){
            e.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}
