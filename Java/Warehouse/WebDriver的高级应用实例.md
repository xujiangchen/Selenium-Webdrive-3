- [1、使用JavaScriptExecutor单击元素](#1使用javascriptexecutor单击元素)
- [2、在使用Ajax方式产生的浮动框中，单击选择包含某个关键字的选项](#2在使用ajax方式产生的浮动框中单击选择包含某个关键字的选项)
- [3、设置一个页面对象的属性值](#3设置一个页面对象的属性值)
- [4、在日期选择器上进行日期选择](#4在日期选择器上进行日期选择)
- [5、无人化自动下载某个文件](#5无人化自动下载某个文件)
- [6、附件上传](#6附件上传)
- [7、Web页面滚动条](#7web页面滚动条)
- [8、Robot操作键盘](#8robot操作键盘)
- [9、操作富文本框的两种方法](#9操作富文本框的两种方法)
- [10 、精确比对网页截图图片](#10-精确比对网页截图图片)
- [11、控制基于HTML5语言实现的视频播放器](#11控制基于html5语言实现的视频播放器)
- [12、在HTML5的画布元素上进行绘画操作](#12在html5的画布元素上进行绘画操作)
- [13、操作HTML5的存储对象](#13操作html5的存储对象)
- [14、操作chrome开发者工具](#14操作chrome开发者工具)

#### 1、使用JavaScriptExecutor单击元素

```java
driver.get("http://www.baidu.com");
//查找搜索按钮
WebElement but = driver.findElement(By.id("su"));
WebElement input = driver.findElement(By.id("kw"));

JavascriptExecutor js = (JavascriptExecutor) driver;
input.sendKeys("使用JavaScript语句来进行页面元素的单击");
// 进行点击操作
js.executeScript("arguments[0].click();", but);
```



#### 2、在使用Ajax方式产生的浮动框中，单击选择包含某个关键字的选项

```java
driver.get("http://www.sogou.com");
WebElement input=driver.findElement(By.id("query"));
//点击搜索框，使得浮动框显示出来
input.click();
//把浮动框里的所有选项都存在suggetionOptions里
List<WebElement> suggetionOptions=driver.findElements(By.xpath("//div[@id='vl']/div/ul/li"));
//for循环，查找想要的对象
for(WebElement element:suggetionOptions){
    //把每一项的内容与目标内容进行比较，查找到想要的那个element，点击
    System.out.println(element.getText());
    if(element.getText().contains("无人机")){//这里比较的内容根据实际悬浮的内容来填写
        System.out.println(element.getText());
        element.click();
        break;
    }
}
```



#### 3、设置一个页面对象的属性值

- **HTML代码**

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>设置文本框属性</title>
</head>
<body>
	<input type="text" id="text" value="watermelon" size=100>文本框</input>
</body>
</html>
```

- **Java代码**

```java
WebElement textbox = driver.findElement(By.id("text"));
setAttribute(textbox, driver, "value", "文本框的文字和长度属性已经被修改");
setAttribute(textbox, driver, "size", "10");
RemoveAttribute(textbox, driver, "size");


public void setAttribute(WebElement e, WebDriver d, String attributeName, String value) {
	JavascriptExecutor js = (JavascriptExecutor) d;
    // 执行JavaScriptdiamante修改页面元素属性。arguments[0]-[2]后面会用e,attributeName,value替换并执行
    js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", e, attributeName, value);
}

public void RemoveAttribute(WebElement e, WebDriver d, String attributeName) {
    JavascriptExecutor js = (JavascriptExecutor) d;
    // 执行JavaScriptdiamante修改页面元素属性。arguments[0]-[2]后面会用e,attributeName,value替换并执行
    js.executeScript("arguments[0].removeAttribute(arguments[1])", e, attributeName);

}
```



#### 4、在日期选择器上进行日期选择

```java
driver.get("https://jqueryui.com/datepicker/#content");
WebElement webElement = driver.findElement(By.id("datepicker"));
webElement.setKey("12/31/2021")
```

在实际自动化测试的时候我们经常会碰到下面的时间日期插件(这个时候这个文本框是不运行我们输入时间的)，这种选择时间的input标签都会有一个**readonly=""标签**，我们可以用java获取当前日期，然后用Selenium结合JS代码就可以直接往文本框输入内容。

```java
Date date = new Date();//先获取当前日期
String dataStr = new SimpleDateFormat("yyyy-MM-dd").format(date);//对日期进行格式化
String startDate = "document.getElementById('startDate').removeAttribute('readOnly');document.getElementById('startDate').setAttribute('value','" + dataStr + "');";
```



#### 5、无人化自动下载某个文件

**以火狐浏览器为例**

```java
public class DownloadDemo {

    private WebDriver webDriver;
    private String baseUrl;

    @BeforeMethod
    public void initInfo(){
        baseUrl = "http://ftp.mozilla.org/pub/firefox/releases/35.0b8/win32/zh-CN/";
        System.setProperty("webdriver.gecko.driver","G:\\IdeaProjects\\webDriver\\src\\main\\resources\\geckodriver2.exe");
        webDriver = new FirefoxDriver();
    }

    @AfterMethod
    public void quit(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }

    @Test
    public void test(){
        webDriver = new FirefoxDriver(FirefoxDriverOptions());
        webDriver.get(baseUrl);
        webDriver.findElement(By.partialLinkText("Stub")).click();

    }

    private FirefoxOptions FirefoxDriverOptions(){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxProfile Profile = new FirefoxProfile();
        // 指定下载按规定路径保存
        Profile.setPreference("browser.download.folderList",2);
        // 启动下载时是否显示文件下载窗口
        Profile.setPreference("browser.download.manager.showWhenStarting",false);
        // 指定下载的目录
        Profile.setPreference("browser.download.dir","G:\\");
        // 指定下载类型
        Profile.setPreference("browser.helperApps.neverAsk.openFile","application/x-msdownload");
        // 指定保存类型
        Profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/x-msdownload");
        // 针对未知的文件类型会弹窗，是否让用户手动处理
        Profile.setPreference("browser.helperApps.alwaysAsk.force",false);
        // 下载的文件类型是 exe 的时候，需不需要弹出警告框
        Profile.setPreference("browser.download.manager.alertOnEXEOpen",false);
        // 下载时下载框需不需要获得焦点
        Profile.setPreference("browser.download.manager.focusWhenStarting",false);
        // 下载时是否对下载框进行隐藏，false:隐藏
        Profile.setPreference("browser.download.manager.useWindow",false);
        // 下载完成是否显示提示框
        Profile.setPreference("browser.download.manager.showAlertOnComplete",false);
        // 下载结束后是否自动关闭下载框
        Profile.setPreference("browser.download.manager.closeWhenDone",false);

        firefoxOptions.setProfile(Profile);
        return firefoxOptions;
    }
}
```

#### 6、附件上传

```java
public class uploadDemo {

    private WebDriver webDriver;
    private String baseUrl;

    @BeforeMethod
    public void initInfo(){
        // 文件上传的页面地址
        baseUrl = "G:\\IdeaProjects\\webDriver\\src\\main\\resources\\html\\UploadDemo.html";
        System.setProperty("webdriver.gecko.driver","G:\\IdeaProjects\\webDriver\\src\\main\\resources\\geckodriver2.exe");
        webDriver = new FirefoxDriver();
    }

    @AfterMethod
    public void quit(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }

    @Test
    public void test(){
        webDriver.get(baseUrl);
        WebElement element = webDriver.findElement(By.id("file"));
        element.sendKeys("G:\\test.txt");
        // 使用显示等待，判断页面按钮是否可以被点击
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("fileSubmit")));
        WebElement submit = webDriver.findElement(By.id("fileSubmit"));
        submit.click();
    }
}
```

#### 7、Web页面滚动条

```java
baseUrl = "https://v.sogou.com";
webDriver.get(baseUrl);

JavascriptExecutor js = (JavascriptExecutor)webDriver;
// 将滚动条拉到底部
js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
// 将滚动条拉到某一元素
WebElement element = webDriver.findElement(By.id("app"));
js.executeScript("arguments[0].scrollIntoView()",element);
// 将滚动条拉到某一指定位置
js.executeScript("window.scrollBy(0,800)");
```

#### 8、Robot操作键盘

```java
baseUrl = "https://sogou.com";
webDriver.get(baseUrl);

WebDriverWait wait = new WebDriverWait(webDriver,10);
// visibilityofelementlocated和presenceofelementlocated 前者是可见性，后者是存在，存在的东西不一定可见。
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("query")));
ctrlVandC("hello world");
// 按tab键到查询按钮
pressTab();
// 按回车进行查询
pressEnter();


/**
     * 模拟 CV 大法
     * @param data
     */
private void ctrlVandC(String data) {
    // StringSelection此类用于传输选取的文本
    StringSelection stringSelection = new StringSelection(data);
    // 将信息放入剪切板中
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    // 声明Robot对象
    Robot robot = null;
    try {
        robot = new Robot();
    } catch (Exception e) {
        e.printStackTrace();
    }
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);

    robot.keyRelease(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);
}

/**
     * 模拟 TAB
     * @param data
     */
private void pressTab(){
    Robot robot = null;
    try {
        robot = new Robot();
    }catch (Exception e){
        e.printStackTrace();
    }
    robot.keyPress(KeyEvent.VK_TAB);
    robot.keyRelease(KeyEvent.VK_TAB);
}

/**
     * 模拟 ENTER
     * @param data
     */
private void pressEnter(){
    Robot robot = null;
    try {
        robot = new Robot();
    }catch (Exception e){
        e.printStackTrace();
    }
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
}

```

#### 9、操作富文本框的两种方法

**以火狐测试邮箱为例子**

- 通过`webDriver.findElement`方法进行操作符文本框

```java
@BeforeMethod
public void initInfo(){
    baseUrl = "https://mail.sohu.com";
    webDriver = new FirefoxDriver();
    webDriver.get(baseUrl);
}

@Test
public void test(){
    WebDriverWait wait = new WebDriverWait(webDriver, 10);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='account']")));
    // 登录邮箱
    WebElement userName = webDriver.findElement(By.xpath("//input[@ng-model='account']"));
    WebElement password = webDriver.findElement(By.xpath("//input[@ng-model='pwd']"));
    WebElement loginSubmit = webDriver.findElement(By.xpath("//input[@value=\"登 录\"]"));
    userName.clear();
    userName.sendKeys("fosterwu");
    password.clear();
    password.sendKeys("1111");
    loginSubmit.click();
    // 显示等待是否登录成功
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(text(),'写邮件')]")));
    // 获取写邮件的按钮
    WebElement writeMail = webDriver.findElement(By.xpath("//li[contains(text(),'写邮件')]"));
    writeMail.click();
    // 等待页面加载
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // 找到收件人和主题，输入测试数据
    WebElement recipients = webDriver.findElement(By.xpath("//div[@arr=\"mail.to_render\"]/span/input"));
    WebElement subject = webDriver.findElement(By.xpath("//input[@ng-model=\"mail.subject\"]"));
    recipients.sendKeys("fosterwu@sohu.com");
    subject.sendKeys("发送一封测试邮件Xu");
    // 进入富文本框
    WebElement fremaCss = webDriver.findElement(By.cssSelector("iframe[id^='ueditor']"));
    webDriver.switchTo().frame(fremaCss);
    // 向富文本框中添加数据
    JavascriptExecutor js = (JavascriptExecutor)webDriver;
    js.executeScript("document.getElementsByTagName('body')[0].innerHTML='测试'");
    // 回到默认页面
    webDriver.switchTo().defaultContent();
    // 找到发送按钮
    WebElement submit = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[2]/span[1]"));
    submit.click();
}
```

- 通过Tab，去操作富文本框

在某些浏览器中会出现假死现象，通用的操作富文本框方法，使用tab键

```java
@BeforeMethod
public void initInfo(){
    baseUrl = "https://mail.sohu.com";
    webDriver = new FirefoxDriver();
    webDriver.get(baseUrl);
}

@Test
public void testTab(){
    WebDriverWait wait = new WebDriverWait(webDriver, 10);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-model='account']")));
    // 登录邮箱
    WebElement userName = webDriver.findElement(By.xpath("//input[@ng-model='account']"));
    WebElement password = webDriver.findElement(By.xpath("//input[@ng-model='pwd']"));
    WebElement loginSubmit = webDriver.findElement(By.xpath("//input[@value=\"登 录\"]"));
    userName.clear();
    userName.sendKeys("fosterwu");
    password.clear();
    password.sendKeys("1111");
    loginSubmit.click();
    // 显示等待是否登录成功
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[contains(text(),'写邮件')]")));
    // 获取写邮件的按钮
    WebElement writeMail = webDriver.findElement(By.xpath("//li[contains(text(),'写邮件')]"));
    writeMail.click();
    // 等待页面加载
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // 找到收件人和主题，输入测试数据
    WebElement recipients = webDriver.findElement(By.xpath("//div[@arr=\"mail.to_render\"]/span/input"));
    WebElement subject = webDriver.findElement(By.xpath("//input[@ng-model=\"mail.subject\"]"));
    recipients.sendKeys("fosterwu@sohu.com");
    subject.sendKeys("发送一封测试邮件Xu");
    pressTab();
    pressTab();
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    ctrlVandC("测试共同方法");
    WebElement submit = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[2]/span[1]"));
    submit.click();
}


/**
     * 模拟 TAB
     * @param data
     */
private void pressTab(){
    Robot robot = null;
    try {
        robot = new Robot();
    }catch (Exception e){
        e.printStackTrace();
    }
    robot.keyPress(KeyEvent.VK_TAB);
    robot.keyRelease(KeyEvent.VK_TAB);
}

/**
     * 模拟 CV 大法
     * @param data
     */
private void ctrlVandC(String data) {
    // StringSelection此类用于传输选取的文本
    StringSelection stringSelection = new StringSelection(data);
    // 将信息放入剪切板中
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    // 声明Robot对象
    Robot robot = null;
    try {
        robot = new Robot();
    } catch (Exception e) {
        e.printStackTrace();
    }
    robot.keyPress(KeyEvent.VK_CONTROL);
    robot.keyPress(KeyEvent.VK_V);

    robot.keyRelease(KeyEvent.VK_V);
    robot.keyRelease(KeyEvent.VK_CONTROL);
}
```

#### 10 、精确比对网页截图图片

```java
/**
     * 两张图片精确对比
     *
     * @param exceptedImgPath 期待结果截图位置
     * @param actualImgPath   实际结果截图
     * @return
     */
public static boolean compareImg(String exceptedImgPath, String actualImgPath) throws IOException {
    boolean result = true;
    // 期待结果截图
    File exceptedImg = new File(exceptedImgPath);
    // 实际结果截图
    File actualImg = new File(actualImgPath);
    // 期待结果截图信息
    BufferedImage bufferedExceptedImg = ImageIO.read(exceptedImg);
    DataBuffer dataBufferExceptedImg = bufferedExceptedImg.getData().getDataBuffer();
    int exceptedImgSize = dataBufferExceptedImg.getSize();
    // 实际结果截图信息
    BufferedImage bufferedActualImg = ImageIO.read(actualImg);
    DataBuffer dataBufferActualImg = bufferedActualImg.getData().getDataBuffer();
    int actualImgSize = dataBufferActualImg.getSize();
    // 进行判断
    if (exceptedImgSize == actualImgSize) {
        for (int index = 0; index < exceptedImgSize; index++) {
            if (dataBufferExceptedImg.getElem(index) != dataBufferActualImg.getElem(index)) {
                result = false;
                break;
            }
        }
    }
    return result;
}
```

#### 11、控制基于HTML5语言实现的视频播放器

```java
String url = "http://www.w3school.com.cn/tiy/t.asp?f=html5_video_all";
driver.get(url);
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

WebElement iframe = driver.findElement(By.xpath(".//*[@id='result']/iframe"));
driver.switchTo().frame(iframe);
WebElement videoPlayer = driver.findElement(By.tagName("video"));
JavascriptExecutor js = (JavascriptExecutor) driver;
// currentSrc属性获取视频文件的网络存储地址
String videoSrc = (String) js.executeScript("return arguments[0].currentSrc;", videoPlayer);
System.out.println(videoSrc);
// 断言视频网络地址是否符合期望
Assert.assertEquals("http://www.w3school.com.cn/i/movie.ogg", videoSrc);

// duration属性获取视频文件的播放时长
Double videoDuration = (Double) js.executeScript("return arguments[0].duration", videoPlayer);
System.out.println(videoDuration.intValue());

// 等待5秒让视频加载完成
Thread.sleep(5000);
// 使用JavaScript，调用播放器内部的play
js.executeScript("return arguments[0].play()", videoPlayer);
// 等待两秒，使用pause方法来暂停
Thread.sleep(2000);

js.executeScript("return arguments[0].pause()", videoPlayer);

// 停顿3秒证明是否已暂停
Thread.sleep(3000);
```

#### 12、在HTML5的画布元素上进行绘画操作

```java
String url = "http://www.w3school.com.cn/tiy/t.asp?f=html5_canvas_line";
driver.get(url);
JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

// video对象放在iframe里，要先切换到iframe中，才能定位到video元素
WebElement iframe = driver.findElement(By.xpath(".//*[@id='result']/iframe"));
driver.switchTo().frame(iframe);

/*
 * 调用JavaScript语句，在画布元素上画一个红色矩形的团 getElementById('myCanvas')语句获取页面上的画布元素
 * var cxt=c.getContext('2d')设定画布为2d cxt.fillStyle='#FF0000'设定填充色为红色
 * cxt.fillRect(0,0,150,150);画布上绘制矩形
 */
jsExecutor.executeScript("var c=document.getElementById('myCanvas');" + "var cxt=c.getContext('2d');" + "cxt.fillStyle='#FF0000';" + "cxt.fillRect(0,0,150,150);");
```

#### 13、操作HTML5的存储对象

```java
String localStoreUrl = "http://www.w3school.com.cn/tiy/t.asp?f=html5_webstorage_local";
String sessionStoreUrl = "http://www.w3school.com.cn/tiy/t.asp?f=html5_webstorage_session";

@Test
public void testHtml5localStorage() {
    driver.get(localStoreUrl);
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    // 调用JavaScript语句“return
    // localStorage.lastname”获取存储在localStorage中“lastname”的存储值
    String lastname = (String) jsExecutor.executeScript("return localStorage.lastname;");
    // 断言获取的存储值是否为“Smith”
    Assert.assertEquals("Gates", lastname);
    // 调用JavaScript语句“localStorage.clear()”清除所有存储在localStorage中的变量值
    jsExecutor.executeScript("localStorage.clear();");
}

@Test
public void testHtml5sessionStorage() {
    driver.get(sessionStoreUrl);
    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    // 调用JavaScript语句“return
    // localStorage.lastname”获取存储在localStorage中“lastname”的存储值
    jsExecutor.executeScript("sessionStorage.setItem('lastname','Gates');");
    String lastname = (String) jsExecutor.executeScript("return sessionStorage.lastname;");
    // 断言获取的存储值是否为“Smith”
    Assert.assertEquals("Gates", lastname);
    //清除存储在sessionStorage中的“lastname”项
    jsExecutor.executeScript("sessionStorage.removeItem('lastname');");
    // 调用JavaScript语句“localStorage.clear()”清除所有存储在localStorage中的变量值
    jsExecutor.executeScript("sessionStorage.clear();");
}
```

#### 14、操作chrome开发者工具

在最新 java版本的 Selenium 4.x 版本中，新增了对开发者工具的操作，这个要求chrome的版本要高于86版本！！！

```java

ChromeOptions options = new ChromeOptions();
options.addArguments("--user-data-dir=" + System.getevn("USERPROFILE") + "/AppData/Local/Google/Chrome/User Data/Default")
// chrome浏览器的exe文件的绝对路径
options.setBinary("chrome.exe")

DevTool devTool = driver.getDevTools();
devTool.createSession();
devTool.send(Network.enable(of(1000000000), of(1000000000), Optional.empty()));

// 添加监听
devTool.addListener(Network.responseRecevied(), responseReceived->{
    RequestId requestId = responseReceived.getRequestId();
    String url = responseReceived.getResponse().getUrl();
    String mimeType = responseReceived.getResponse().getMimeType();

    Thread.sleep(3000L)
    String reponseBody = devTool.send(Network.getResponseBody(requestId)).getBody();
});

```