
  * [1、访问某网页地址](#1访问某网页地址)
  * [2、单击浏览器的前进和后退](#2单击浏览器的前进和后退)
  * [3、刷新当前网页](#3刷新当前网页)
  * [4、 获取页面的Title属性](#4获取页面的Title属性)
  * [5、获取页面的源代码](#5获取页面的源代码)
  * [6、获取当前页面的URL地址](#6获取当前页面的URL地址)
  * [7、输入框中输入指定内容并清除](#7输入框中输入指定内容并清除)
  * [8、按钮点击](#8按钮点击)
  * [9、单选列表操作](#9单选列表操作)
  * [10、对当前浏览器窗口进行截屏](#10对当前浏览器窗口进行截屏)
  * [11、执行JavaScript脚本](#11执行JavaScript脚本)
  * [12、拖曳页面元素](#12拖曳页面元素)
  * [13、模拟键盘的操作](#13模拟键盘的操作)
  * [14、模拟鼠标右键操作](#14模拟鼠标右键操作)
  * [15、在指定元素上方进行鼠标悬浮](#15在指定元素上方进行鼠标悬浮)
  * [16、在指定元素上进行鼠标单击左键和释放的操作](#16在指定元素上进行鼠标单击左键和释放的操作)
  * [17、查看页面元素的属性](#17查看页面元素的属性)
  * [18、获取页面元素的CSS属性值](#18获取页面元素的CSS属性值)
  * [19、使用Title属性识别和操作新弹出的浏览器窗口](#19使用Title属性识别和操作新弹出的浏览器窗口)
  * [20、通过句柄识别和操作新弹出的浏览器窗口](#20通过句柄识别和操作新弹出的浏览器窗口)
  * [21、操作JavaScript的Alert,Confirm,prompt弹窗](#21操作JavaScript的Alert,Confirm,prompt弹窗)
  * [22、操作frame中的页面元素](#22操作frame中的页面元素)

**准备**

```java
public class seleniumDemo {
    
    // Selenium 常用 API以 火狐浏览器为例
	public static void main(String[] args) throws InterruptedException{
    	// 获取火狐驱动的路径
        String initiator = seleniumDemo.class.getClassLoader().getResource("geckodriver.exe").getPath();
        // 配置系统环境
        System.setProperty("webdriver.gecko.driver", initiator);
        // 启动浏览器
        WebDriver driver = new FirefoxDriver();
        // -------------------- 案例代码位置开始----------------------------
        
        // -------------------- 案例代码位置结束----------------------------
        
        // 暂停5s查看页面效果
       	Thread.sleep(5000L);
        // 退出浏览器
        driver.quit();
    }
}
```

#### 1、访问某网页地址

```java
String url = "https://www.baidu.com";
// 方式一
driver.get(url);
// 方式二 
driver.navigate().to(url);
```

**`driver.get()` 和`driver.navigate()`的区别：**

- `driver.get(url)`用于转到特定的网页，但是它**不保留浏览器历史记录和cookie**，并且要等到页面完全加载为止。

- `driver.navigate`用于转到特定的网页，它**维护浏览器历史记录和cookie**，并且不会完全加载页面，并且在页面之间进行导航，后退，前进和刷新。   

- 所有当想要 并且想要从当前URL重定向到新的URL，最好使用 `driver.navigate().to()`

#### 2、单击浏览器的前进和后退

- 前进  `driver.navigate().forward();`
- 后退  `driver.navigate().back()`

```java
String firstUrl = "http://www.baidu.com";
String secondUrl = "https://www.sogou.com";
// 进入第一个页面
driver.navigate().to(firstUrl);
// 重定向到第二个页面
driver.navigate().to(secondUrl);
// 后退到第一个页面
driver.navigate().back();
// 前进到第二个页面
driver.navigate().forward();
```

#### 3、刷新当前网页

- `driver.navigate().refresh()`

```java
String url = "https://www.baidu.com";
// 跳转到指定网页
driver.navigate().to(url);
// 刷新当前页面
driver.navigate().refresh();
```

#### 4、 获取页面的Title属性

- `driver.getTitle()`

```java
String url = "https://www.baidu.com";
// 跳转到指定网页
driver.navigate().to(url);
// 获取页面的Title属性
driver.getTitle();
```

#### 5、获取页面的源代码

- `driver.getPageSource()`

```java
String url = "https://www.baidu.com";
// 跳转到指定网页
driver.navigate().to(url);
// 获取页面的源代码
driver.getPageSource();
```

#### 6、获取当前页面的URL地址

- `driver.getCurrentUrl()`

```java
String url = "https://www.baidu.com";
// 跳转到指定网页
driver.navigate().to(url);
// 获取当前页面的URL地址
driver.getCurrentUrl();
```

#### 7、输入框中输入指定内容并清除

- 输入指定内容 `webElement.sendKeys()`
- 清空内容 `webElement.clear()`

```java
driver.get("http://www.baidu.com");
// 找到输入框
WebElement webElement = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
// 输入指定内容
webElement.sendKeys("Selenium");
// 清空内容
webElement.clear();
```

#### 8、按钮点击

- 单击按钮 `webElement.click()`
- 双击按钮 

```java
driver.navigate().to("http://www.baidu.com");
// 文本框里输入
driver.findElementById("kw").sendKeys("china"); 
// 
Actions action = new Actions(driver);
// 对按钮进行双击
// action.doubleClick(driver.findElementById("su")).build().perform();
action.doubleClick(driver.findElementById("su")).perform();
```

> `Actions` 用于真实的模拟用户交互的一个类
>
> `build().perform()`  的含义是建立行动链，并执行。但是如果查看源码就可以知道，`perform()` 的实现就包括了`build()` 的步骤。

#### 9、单选列表操作

- 案例 HTML 代码

```html
<select name="fruit" size="1">
    <option id="peach" value="taozi">桃子</option>
    <option id="watermelon" value="xigua">西瓜</option>
    <option id="orange" value="juzi">橘子</option>
    <option id="kiwifruit" value="mihoutao">猕猴桃</option>
    <option id="matbush" value="shanzha">山楂</option>
    <option id="litchi" value="lizhi">荔枝</option>
</select>
```

- 案例 Java 代码

```java
 Select select = new Select(driver.findElement(By.name("fruit")));
// 判断是否为多选
select.isMultiple();
// 获取当前选择的数据
select.getFirstSelectedOption();
// 根据序号选择数据
select.selectByIndex(2);
// 根据value选择
select.selectByValue("taozi");
// 根据显示的文本选择
select.selectByVisibleText("桃子");
```

#### 10、对当前浏览器窗口进行截屏

- `((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE)`

```java
driver.get("http://www.baidu.com");
File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
try {
    //把File对象转换为一个保存在C盘下testing目录中名为test.png的图片文件
    FileUtils.copyFile(scrFile, new File("d:\\testing\\test.png"));
} catch (IOException e) {
    e.printStackTrace();
}
```

#### 11、执行JavaScript脚本

- 打开百度首页，将百度按钮改为MyLove

```java
driver.get("http://www.baidu.com");
WebElement element = driver.findElement(By.id("su"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("document.getElementById('su').setAttribute('value','MyLove');",element);
```

- 点击百度数据的查询按钮

```java
driver.get("http://www.baidu.com");
WebElement element = driver.findElement(By.id("su"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click",element);
```

**JavascriptExecutor执行js代码的两种方法**

- Object executeScript(String script, Object... args);

- Object executeAsyncScript(String script, Object... args);

> **script**，javascript代码片段，这段js代码片段是作为js函数的完整方法体，可以使用return语句作为函数的返回值。
>
> **args**, 参数数组，参数数组用于将外部数据传递给script(js代码片段)，script中可以通过arguments[index]方式索引args数组中的参数；参数数据类型必须是以下几种（number, boolean, String, WebElement, 或者以上数据类型的List集合），当然无参数可以保留为空。
>
> **返回值**，返回值是由js代码片段计算后通过return语句返回，返回值数据类型可以为（WebElement，Double，Long，Boolean，String，List或Map），没有return语句，这里返回数据为null。

#### 12、拖曳页面元素 

```java
driver.find("https://jqueryui.com/resources/demos/draggable/scroll.html");
WebElement draggable = driver.findElement(By.id("draggable"));
Actions action = new Actions(driver);
// 向下拖动10个元素，拖动5次
for (int i = 0; i < 5; i++) {
    action.dragAndDropBy(draggable, 0, 10).perform();
}
// 向右拖动10个元素，拖动5次
for (int i = 0; i < 5; i++) {
    action.dragAndDropBy(draggable, 10, 0).perform();
}
```

#### 13、模拟键盘的操作

- 按下 `action.keyDown`
- 松开 `action.keyUp`

```java
driver.get("https://www.baidu.com");  
Actions action = new Actions(driver);
driver.findElementById("kw").sendKeys("seleniumm");
//删除多于的m字母
action.sendKeys(driver.findElementById("kw"),Keys.BACK_SPACE).perform();
//ctrl+a
action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
//ctrl+c
action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();  
```

#### 14、模拟鼠标右键操作

- `action.contextClick`

```java
driver.get("https://www.baidu.com");            
Thread.sleep(5000);
Actions action = new Actions(driver);
Thread.sleep(5000);  
//鼠标在某个元素上右
action.contextClick(driver.findElementByLinkText("地图")).perform();   
```

#### 15、在指定元素上方进行鼠标悬浮

- `action.moveToElement`

```java
driver.navigate().to("http://www.baidu.com");
Actions action = new Actions(driver);
//鼠标悬浮在 设置  元素上面
action.moveToElement(driver.findElementByLinkText("设置")).perform(); 
```

#### 16、在指定元素上进行鼠标单击左键和释放的操作

```java
Actions action = new Actions(driver);
// 单击左键,不进行释放
action.clickAndHold().perform(div);
// 释放
action.release(div).perform();
```

#### 17、查看页面元素的属性

- `getAttribute`

```java
driver.navigate().to("http://www.baidu.com");
driver.findElementById("kw").sendKeys("中国美国");
System.out.println(driver.findElementById("kw").getAttribute("value"));
```

#### 18、获取页面元素的CSS属性值

- `getCssValue`

```java
driver.navigate().to("http://www.baidu.com");
WebElement element = driver.findElement(By.id("su"));
String cssColor = element.getCssValue("background-color");
System.out.println(cssColor);
```

#### 19、使用Title属性识别和操作新弹出的浏览器窗口

- 获取句柄  `driver.getWindowHandles`

```java
driver.navigate().to("http://www.baidu.com"); 
//先将当前浏览器窗口句柄存储到变量中。
String parentWindowHandle = driver.getWindowHandle(); 
WebElement element = driver.findElement(By.xpaht("//a[@href=\"http://www.baidu.com/more/\" and text()='更多' and @name=\"tj_briicon\"]"));
element.click();
//把打开的所有浏览器句柄，存放到一个set容器中
Set<String> allWindowsHandles = driver.getWindowHandles();
if (!allWindowsHandles.isEmpty()){
    for (String windowHandle : allWindowsHandles){
        System.out.println(driver.switchTo().window(windowHandle).getTitle());
        Thread.sleep(10000L);
    }
}
```

> 浏览器句柄: 就是浏览器tab页的一个唯一性标识符，一串数字。可以通过句柄在浏览器的tab页中进行跳转。

#### 20、通过句柄识别和操作新弹出的浏览器窗口

- `driver.switchTo().window()`

```java
driver.navigate().to("http://www.baidu.com"); 
//先将当前浏览器窗口句柄存储到变量中。
String parentWindowHandle = driver.getWindowHandle(); 
WebElement element = driver.findElement(By.xpaht("//a[@href=\"http://www.baidu.com/more/\" and text()='更多' and @name=\"tj_briicon\"]"));
element.click();

// 获取当前浏览器的所有句柄
Set<String> allowWindowsHandle = driver.getWindowHandles();
// 进入新打开的浏览器
for (String item : allowWindowsHandle) {
    if (!parentWindowsHandle.equals(item)) {
        driver.switchTo().window(item);
    }
}	
```
#### 21、操作JavaScript的Alert,Confirm,prompt弹窗

**Alert,Confirm,prompt弹出框，其实并不在当前页面的dom树之中**

- **HTML代码**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<input id="button" type="button" onclick="alert('这是一个alert弹出框！')" value="点击"/>
<input id="button" type="button" onclick="confirm('这是一个confirm弹出框！')" value="点击"/>
<input id="button" type="button" onclick="prompt('这是一个prompt弹出框！')" value="点击"/>
</body>
</html>
```

- **java代码**

  - **Alert弹窗**

  ```java
  // 使用 driver.switchTo() 获取alert对象
  Alert alert = driver.switchTo().alert();
  // 点击确定
  alert.accept();
  ```

  - **confirm弹窗**

  ```java
  // 使用 driver.switchTo() 获取Confirm
  Alert alert = driver.switchTo().alert();
  // 点击确定
  alert.accept();
  // 点击取消
  alert.dismiss();
  ```

  - **prompt弹窗**

  ```java
  // 使用 driver.switchTo() 获取prompt
  Alert alert = driver.switchTo().alert();
  // 点击确定
  alert.accept();
  // 在prompt的弹出框中添加文本信息
  alert.sendKeys("");
  // 点击取消
  alert.dismiss();
  ```
#### 22、操作frame中的页面元素

**iframe 中的元素存在于独立的DOM树之中，所以如果只是单纯的使用定位，是无法定位到iframe中的元素。**

**进入iframe 的方法**

- ```java
  dr.switchTo().frame("frameA");
  ```

**回到主窗口**

- ```java
  dr.switchTo().defaultContent();
  ```

> 需要注意的是，如果页面存在多个iframe的时候，当你进入第一个iframe后，如果想要进入第二个iframe，需要先退出当前的iframe，才能进入下一个iframe。
  
