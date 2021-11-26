- [1、driver下相关](#1driver下相关)
  - [1.1 下载driver地址](#11-下载driver地址)
  - [1.2 配置环境变量](#12-配置环境变量)
- [2、等待方式](#2等待方式)
  - [2.1 直接等待](#21-直接等待)
  - [2.2 隐式等待](#22-隐式等待)
  - [2.3 显式等待](#23-显式等待)


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


