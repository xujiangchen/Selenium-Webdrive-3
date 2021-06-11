- [PageObject模式](#pageobject--)
  * [1、什么是面向对象的思想](#1-----------)
  * [2、什么事PageObject的思想](#2----pageobject---)
  * [3、PageObject的简单Demo](#3-pageobject---demo)
      - [3.1 原始写法](#31-----)
      - [3.2 使用Page Objects模式](#32---page-objects--)
        * [3.2.1 百度首页的pageObject](#321------pageobject)
        * [3.2.2 测试用例](#322-----)

## 1、什么是面向对象的思想

**面向对象(Object Oriented,OO)**
	
面向对象编程是一种解决软件复用的设计和编程方法。 这种方法把软件系统中相近相似的操作逻辑和操作 应用数据、状态,以类的型式描述出来,以对象实例的形式在软件系统中复用,以达到提高软件开发效率的作用。
	
在开发的时候，开发者应该将对象的许多功能事先已经编写好了，**在使用时，只需要关注功能的运用，而不需要这个功能的具体实现过程。**
	
面向对象的优势：

- 1.符合人们的思考习惯
- 2.把执行者变成指挥者
- 3.简化功能，把复杂的事情简单化

## 2、什么事PageObject的思想

在前端自动化测试过程中我们经常要进行查找元素，操作元素的操作，一般来说，代码是如下这个样子的：

`self.driver.find_element_by_xpath("//option[@value='10']")`

如果这个元素会被多个method用到，那么当元素变化时（例如XPATH变了）， 我们就不得不更新每个引用到这个元素的代码,非常麻烦。

有没有好的办法解决这个问题呢？ PageObject 应运而生

PageObject是一种程序设计模式，**将面向过程转变为面向对象(页面对象)，将测试对象及单个的测试步骤封装在每个Page对象中，以page为单位进行管理。**可以使代码复用，降低维护成本，提高程序可读性和编写效率。
PageObject可以将页面定位和业务操作分开，分离测试对象（元素对象）和测试脚本（用例脚本），提高用例的可维护性。

用PageObject开发的好处也是显而易见的：

- 创建可在多个测试用例之间共享的可重用代码
- 减少重复代码的数量
- 如果用户界面发生变化，修复只需要在一处更改 

## 3、PageObject的简单Demo

主要做如下几个动作：

1. 打开百度首页
2. 断言页面标题出现
3. 在查询框中输入字符
4. 点击找找看按钮

#### 3.1 原始写法

```java
public class Test {
    
    private WebDriver webDriver;
    
    @BeforeClass
    public void initInfo(){
        String baseUrl = "http://www.baidu.com";
    	webDriver = new FirefoxDriver();
    	webDriver.get(baseUrl);
    }
    
    @Test
    public void TestOne(){
        // 对页面标题进行断言
        assertEquals("百度一下，你就知道", driver.getTitle());
        // 获取输入框
        WebElement searchBox = driver.findElement(By.id("kw"));
        // 获取点击按钮
        WebElement button = driver.findElement(By.id("su"));
        // 在输入框中输入文字
        searchBox.sendKeys("webdriver3");
        // 点击查询按钮
        button.click();
    }
    
    @AfterClass
    public void quit(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }
    
   
}
```

>这种写法缺点很多， 可读性差，页面元素，操作HTML，测试逻辑全部在一起。

#### 3.2 使用Page Objects模式

```
WebDriver为了支持PageObject模式,支持库包含一个叫PageFactory的工厂类， 
我们通过PageFactory的initElements方法来实例化PageObject的实例.
```

##### 3.2.1 百度首页的pageObject

```java
public class HomePage{
    
    // 输入框
    @FindBy(id = "kw")
    private WebElement searchBox;
    
    // 查询按钮
    @FindBy(id = "su")
    private WebElement button;
    
    // 查询方法
    public void searchFor(String content) {  
        searchBox.sendKeys(content);
        button.click();    
    }
    
}
```

##### 3.2.2 测试用例

```java
public class Test {
    
    private WebDriver webDriver;
    
    @BeforeClass
    public void initInfo(){
        String baseUrl = "http://www.baidu.com";
    	webDriver = new FirefoxDriver();
    	webDriver.get(baseUrl);
    }
    
    @Test
    public void TestOne(){
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.searchFor("webdriver3");
    }
    
    @AfterClass
    public void quit(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
    }
   
}
```

 
