### Selenium 常用 API

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


