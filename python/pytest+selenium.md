- [1、driver下相关](#1driver下相关)
  - [1.1 下载driver地址](#11-下载driver地址)
  - [1.2 配置环境变量](#12-配置环境变量)
- [2、等待方式](#2等待方式)
  - [2.1 直接等待](#21-直接等待)
  - [2.2 隐式等待](#22-隐式等待)
  - [2.3 显式等待](#23-显式等待)
    - [2.3.1 WebDriverWait的具体参数和方法](#231-webdriverwait的具体参数和方法)
    - [2.3.2 ExpectedCondition](#232-expectedcondition)
    - [2.3.3 自定义显示等待方式](#233-自定义显示等待方式)
- [3、web控件的定位和常见操作](#3web控件的定位和常见操作)
  - [3.1 定位方式](#31-定位方式)
  - [3.2 基本交互](#32-基本交互)
    - [3.2.1 Action](#321-action)
      - [3.2.1.1 ActionChains](#3211-actionchains)
      - [3.2.1.1 TouchAction](#3211-touchaction)
    - [3.2.2 多窗口处理](#322-多窗口处理)
    - [3.2.3 frame](#323-frame)
    - [3.2.4 执行javascript脚本](#324-执行javascript脚本)
    - [3.2.5 文件上传,弹框处理](#325-文件上传弹框处理)
- [4、Page Object](#4page-object)
  - [4.1 基本原则](#41-基本原则)
  - [4.2 demo（百度首页）](#42-demo百度首页)
    - [4.2.1 BasePage](#421-basepage)
    - [4.2.2 Main](#422-main)
    - [4.2.3 Register](#423-register)


## 1、driver下相关

### 1.1 下载driver地址
https://npm.taobao.org/mirrors/chromedriver

### 1.2 配置环境变量

**windows：**

window中并不需要网上所说的添加环境变量，只需要将driver解压后的文件添加到以下两个目录

- chrome的安装路径下，与 chrome.exe 平级
- python的安装路径下，与 python.exe 平级

## 2、等待方式

### 2.1 直接等待

强制等待，线程休眠一定时间即可
```python
time.sleep(5)
```

### 2.2 隐式等待

设置一个等待时间，轮询查找元素（默认0.5s），如果没有出现就抛出异常

全局变量

```python
self.driver.implicitly_wait(5)
```

### 2.3 显式等待

- 显示等待：设置一个等待时间和一个条件，在规定时间内，每隔一段时间查看下条件是否成立，如果成立那么程序就继续执行，否则就提示一个超时异常（TimeoutException）。
- 通常情况下WebDriverWait类会结合ExpectedCondition类一起使用。

#### 2.3.1 WebDriverWait的具体参数和方法
```python
WebDriverWait(driver,timeout,poll_frequency=0.5,ignored_exceptions=None)
    driver: 浏览器驱动
    timeout: 超时时间，等待的最长时间（同时要考虑隐性等待时间）
    poll_frequency: 每次检测的间隔时间，默认是0.5秒
    ignored_exceptions:超时后的异常信息，默认情况下抛出NoSuchElementException异常


until(method,message='')
    method: 在等待期间，每隔一段时间调用这个传入的方法，直到返回值不是False
    message: 如果超时，抛出TimeoutException，将message传入异常


until_not(method,message='')
    until_not 与until相反，until是当某元素出现或什么条件成立则继续执行，
    until_not是当某元素消失或什么条件不成立则继续执行，参数也相同。
    method
    message
```

#### 2.3.2 ExpectedCondition
```python
from selenium.webdriver.support import expected_conditions as EC


# 判断标题是否和预期的一致
title_is
# 判断标题中是否包含预期的字符串
title_contains


# 判断指定元素是否加载出来
presence_of_element_located
# 判断所有元素是否加载完成
presence_of_all_elements_located


# 判断某个元素是否可见. 可见代表元素非隐藏，并且元素的宽和高都不等于0，传入参数是元组类型的locator
visibility_of_element_located
# 判断元素是否可见，传入参数是定位后的元素WebElement
visibility_of
# 判断某个元素是否不可见，或是否不存在于DOM树
invisibility_of_element_located


# 判断元素的 text 是否包含预期字符串
text_to_be_present_in_element
# 判断元素的 value 是否包含预期字符串
text_to_be_present_in_element_value


#判断frame是否可切入，可传入locator元组或者直接传入定位方式：id、name、index或WebElement
frame_to_be_available_and_switch_to_it


#判断是否有alert出现
alert_is_present


#判断元素是否可点击
element_to_be_clickable


# 判断元素是否被选中,一般用在下拉列表，传入WebElement对象
element_to_be_selected
# 判断元素是否被选中
element_located_to_be_selected
# 判断元素的选中状态是否和预期一致，传入参数：定位后的元素，相等返回True，否则返回False
element_selection_state_to_be
# 判断元素的选中状态是否和预期一致，传入参数：元素的定位，相等返回True，否则返回False
element_located_selection_state_to_be


#判断一个元素是否仍在DOM中，传入WebElement对象，可以判断页面是否刷新了
staleness_of
```

#### 2.3.3 自定义显示等待方式
```python
import time

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait


class TestWait:

    def setup(self):
        self.driver = webdriver.Chrome()
        self.driver.get("http://www.baidu.com")

    def teardown(self):
        self.driver.quit()

    def test_wait(self):
        # 自己定义一个显示等待函数,需要有true，false返回值
        def wait(x):
           return len(self.driver.find_elements(By.ID, "kw")) > 0

        # x不需要传值，会将webdriver传递进去
        WebDriverWait(self.driver, 10).until(wait)
        self.driver.find_element_by_id("kw").send_keys("1231312")
        # 等待看效果
        time.sleep(10)
```
## 3、web控件的定位和常见操作

### 3.1 定位方式

- [xpath定位](https://github.com/xujiangchen/Test-Notes/blob/main/Web-Auto-Test/%E5%85%83%E7%B4%A0%E5%AE%9A%E4%BD%8D/xpath%E5%AE%9A%E4%BD%8D%E6%96%B9%E5%BC%8F.md)
- [css定位](https://github.com/xujiangchen/Test-Notes/blob/main/Web-Auto-Test/%E5%85%83%E7%B4%A0%E5%AE%9A%E4%BD%8D/css%E5%AE%9A%E4%BD%8D.md)

### 3.2 基本交互

#### 3.2.1 Action
- `ActionChains`: 执行PC端的鼠标事件，比如单击、双击、点击鼠标右键、拖拽等等。
- `TouchAction`: 执行PC端和移动端的多种手势操作，可以对H5进行操作

##### 3.2.1.1 ActionChains

首先需要了解ActionChains的执行原理，当你调用ActionChains的方法时，不会立即执行，而是会将所有的操作按顺序存放在一个队列里，当你调用perform()方法时，队列中的时间会依次执行。

- **链式写法**
```python
menu = driver.find_element_by_css_selector(".nav")
hidden_submenu =  driver.find_element_by_css_selector(".nav #submenu1")

ActionChains(driver).move_to_element(menu).click(hidden_submenu).perform()
```

- **分步写法**
```python
menu = driver.find_element_by_css_selector(".nav")
hidden_submenu = driver.find_element_by_css_selector(".nav #submenu1")

actions = ActionChains(driver)
actions.move_to_element(menu)
actions.click(hidden_submenu)
actions.perform()
```

- **ActionChains方法列表**

```python
click(on_element=None) ——单击鼠标左键

click_and_hold(on_element=None) ——点击鼠标左键，不松开

context_click(on_element=None) ——点击鼠标右键

double_click(on_element=None) ——双击鼠标左键

drag_and_drop(source, target) ——拖拽到某个元素然后松开

drag_and_drop_by_offset(source, xoffset, yoffset) ——拖拽到某个坐标然后松开

key_down(value, element=None) ——按下某个键盘上的键

key_up(value, element=None) ——松开某个键

move_by_offset(xoffset, yoffset) ——鼠标从当前位置移动到某个坐标

move_to_element(to_element) ——鼠标移动到某个元素

move_to_element_with_offset(to_element, xoffset, yoffset) ——移动到距某个元素（左上角坐标）多少距离的位置

perform() ——执行链中的所有动作

release(on_element=None) ——在某个元素位置松开鼠标左键

send_keys(*keys_to_send) ——发送某个键到当前焦点的元素

send_keys_to_element(element, *keys_to_send) ——发送某个键到指定元素 
```

##### 3.2.1.1 TouchAction

`from selenium.webdriver import ActionChains, TouchActions`

```python
#单击
tap(on_element)
#双击   
double_tap(on_element)
#从元素开始以指定的速度移动
flick_element(on_element, xoffset, yoffset, speed)
#长按不释放
long_press(on_element)
#移动到指定的位置
move(xcoord, ycoord)
#执行链中的所有动作
perform()
#在某个位置松开操作                                                              
release(xcoord, ycoord)
#滚动到某个位置                                           
scroll(xoffset, yoffset)
#从某元素开始滚动到某个位置                                    
scroll_from_element(on_element, xoffset, yoffset)
#某点按住
tap_and_hold(xcoord, ycoord)
#以元素为起点向下滑动，实现下拉操作
scroll_from_element(on_element xoffset yoffset)
#以元素为起点以一定速度向下滑动
flick_element(on_element, xoffset, yoffset, speed)
```

#### 3.2.2 多窗口处理

在测试过程中有时候会遇到出现多个浏览器窗口的情况，这时候我们可以通过窗口的句柄来操作不同窗口的元素

句柄：可以理解为tab页的id

- 获取当前tab页面的句柄：`self.driver.current_window_handle`
- 获取当前浏览器的所有句柄：`self.driver.window_handles`
- 进行tab页面的跳转：`self.driver.switch_to.window()`

```python
class TestDemo:

    def setup(self):
        self.driver = webdriver.Chrome()
        self.driver.maximize_window()
        self.driver.get("http://www.baidu.com")
        self.driver.implicitly_wait(2)

    def teardown(self):
        time.sleep(5)
        self.driver.quit()

    def test_one(self):
        
        self.driver.find_element_by_link_text("登录").click()
        self.driver.find_element_by_link_text("立即注册").click()

        parent_handle = self.driver.current_window_handle
        all_handles = self.driver.window_handles

        for item in all_handles:
            if item != parent_handle:
                self.driver.switch_to.window(item)

        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located((By.ID, 'TANGRAM__PSP_4__userName')))

        self.driver.find_element_by_id("TANGRAM__PSP_4__userName").send_keys("21312323")
```

#### 3.2.3 frame

 frame标签有frameset、frame、iframe三种，frameset跟其他普通标签没有区别，不会影响到正常的定位，而frame与iframe会影响selenium定位

 - 切入frame：`self.driver.switch_to.frame(reference) #通过id和index切换到frame`
 - 切出frame（切回主目录）：`self.driver.switch_to.default_content() ` 
 - 嵌套frame,从子frame切回到父frame：`self.driver.switch_to.parent_frame()`

#### 3.2.4 执行javascript脚本
`self.driver.execute_script()`

**不带参数：**
```python
def test_baidu(self):
    self.driver.get("http://www.baidu.com")
    self.driver.execute_script('document.getElementById("kw").value = "test"')
    self.driver.execute_script('document.getElementById("su").click()')
```

**带参数：**
```python
username = driver.find_element_by_xpath("//*[@id='username']")
password = driver.find_element_by_xpath("//*[@id='password']")
driver.execute_script("arguments[0].value = 'admin';arguments[1].value = 'admin';", username, password)
```

#### 3.2.5 文件上传,弹框处理

**文件上传**
```python
# 如果是input标签
el = driver.find_element_by_id("上传按钮")
el.send_keys("文件路径+文件名称")
```
**弹框处理**
弹出框有三种，分别是：alert，confirm，prompt

弹出框的主要操作方法：
- ext：获取文本值
- accept() ：点击"确认"
- dismiss() ：点击"取消"或者关闭弹出框
- send_keys() ：输入文本值
- driver.switch_to.alert()：切换到弹框之中

## 4、Page Object

### 4.1 基本原则
- 1.The public methods represent the services that the page offers
  公共方法来代替页面提供的服务
- 2、Try not to expose the internals of the page
   尽量不要暴露页面的内部结构
- 3、Generally dont't make assertions
   一般不做断言
- 4、Methods return other PageObjects
   方法返回其他PageObject
- 5、Need not represent an entire page
   不需要代表整个页面，只需要封装重要的页面元素
- 6、Different results for the same action are modeled as different methods
   同一动作的不同结果被建模为不同的方法

### 4.2 demo（百度首页）

#### 4.2.1 BasePage

所有的页面都继承与它，让它共享WebDriver，并对相关参数进行初始化，对相关共同方法进行封装
```python
from selenium import webdriver
from selenium.webdriver.remote.webdriver import WebDriver



class BasePage:

    _base_url = ""

    def __init__(self, driver: WebDriver = None):
      """
      初始化方法
      """
        self._driver = None
        # 复用driver，防止重复创建
        if driver is None:
            self._driver = webdriver.Chrome()
        else:
            self._driver = driver
        # 进入指定页面
        if self._base_url != "":
            self._driver.get(self._base_url)
        self._driver.maximize_window()
        self._driver.implicitly_wait(2)

    def find(self, by, local):
      """
      封装查询element方法
      """
        return self._driver.find_element(by, local)

    def quiet_win(self):
      """
      封装关闭页面方法
      """
        self._driver.quit()
```

#### 4.2.2 Main
```python
from selenium.webdriver.common.by import By

from chapter5.page.BasePage import BasePage
from chapter5.page.Register import Register


class Main(BasePage):

    _base_url = "http://www.baidu.com"

    def goto_register(self):
        self.find(By.LINK_TEXT, "立即注册").click()
        all_handles = self._driver.window_handles
        now_handle = self._driver.current_window_handle
        for item in all_handles:
            if item != now_handle:
                self._driver.switch_to.window(item)
        return Register(self._driver)

    def goto_login(self):
        self.find(By.LINK_TEXT, "登录").click()
```

#### 4.2.3 Register
```python
import time

from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

from chapter5.page.BasePage import BasePage


class Register(BasePage):

    def insert_base(self):
        WebDriverWait(self._driver, 4).until(EC.presence_of_element_located((By.ID, 'TANGRAM__PSP_4__userName')))
        self.find(By.ID, "TANGRAM__PSP_4__userName").send_keys("HELLO")
        self.find(By.ID, "TANGRAM__PSP_4__phone").send_keys("WORLD")
        time.sleep(10)
        return True
```