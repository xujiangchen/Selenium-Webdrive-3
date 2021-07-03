package com.test.base;

import java.util.Set;

/**
 * 继承与TestBase，对页面使用的一些公共方法进行封装
 * 所有的pageObject都应该继承它
 */
public class PageBase extends TestBase {

    /**
     * 从当前页面跳转到新页面
     *
     * @param parentWindowsHandle 当前页面的句柄
     */
    public void jumpNewWindow(String parentWindowsHandle){
        Set<String> allWindowsHandle = driver.getWindowHandles();
        for (String item : allWindowsHandle){
            if(!item.equals(parentWindowsHandle)){
                driver.switchTo().window(item);
            }
        }
    }

    /**
     * 关闭当前页面并回到父页面
     *
     * @param parentWindowsHandle 父页面的句柄
     */
    public void returnParentWindow(String parentWindowsHandle){
        String currentWindowsHandle = driver.getWindowHandle();
        // 以防未打开新页面，导致父页面被关闭
        if(!currentWindowsHandle.equals(parentWindowsHandle)){
            driver.close();
            driver.switchTo().window(parentWindowsHandle);
        }
    }


}
