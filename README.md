[![Maven Central](https://img.shields.io/maven-central/v/io.github.ayvytr/Logger.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ayvytr%22%20AND%20a:%22logger%22)
[![License](https://img.shields.io/badge/License-Apache--2.0%20-blue.svg)](license)


# Logger
## Simple, pretty and powerful logger for android（简单，漂亮，强大的Android日志工具）

## 提供了：
	- 线程信息
	- 类信息
	- 方法名
	- Json字符串格式化打印
	- 遇到"\n"字符换行显示
	- 清除输出
	- 跳转到源代码
	- 你想要的各种输出效果

## MavenCentral

    //3.3.1 logger的L可大写，后续版本改为小写
    implementation 'io.github.ayvytr:logger:3.3.3'


## 使用


#### 多参数使用

``` java
L.e("message", "arg1", 2, "arg3");
```
![](photos/logger/log2.png)

#### Array, Map, Set 和 List 输出
```java
L.i("Array", 1, 2, "aa", "bb");
L.i("List", list);
L.v("Map", map);
L.e("Set", set);
L.w("Collection", list, map, set);
```

![](photos/logger/log3.png)

#### 第一个参数什么类型都可以

```java
L.i(1, 2, "aa", "bb");
L.i(list);
L.v(map);
L.e(set);
L.w(list, map, set);
```
Log based
```java
L.t("mytag").d("hello");
```
![](photos/logger/log4.png)

#### 设置Tag

> ##### 随时更改
```java
L.t("MyTag").e(1);
```

> ##### 直接设置
```java
L.settings().tag("MyTag");
```

#### 更改输出的方法数量 (聪明的你是不是发现了什么？)

```java
L.settings().methodCount(10);
L.t(1).d("hello");
```

![](photos/logger/log5.png)

#### 更改方法栈偏移(方法调用信息将会前移数量，和前边一张图片比较一下)

```java
L.settings().methodOffset(10);
```

![](photos/logger/log6.png)

#### 隐藏线程信息
```java
L.settings().hideThreadInfo();
```

![](photos/logger/log7.png)

#### 不想显示方法调用信息
```java
L.settings().methodCount(0);
```

![](photos/logger/log8.png)

#### 打印Json
```java
L.json(YOUR_JSON_DATA);
```

![](photos/logger/json-log.png)

#### 直接打印异常

```java
L.e(exception,"message");
```

### 设置（入口：L.settings())

```java
L
  .settings()                  // 获取Settings
  .tag(YOUR_TAG)                  // 默认tag： PRETTYLOGGER
  .methodCount(3)                 // 默认为 2
  .hideThreadInfo()               // 隐藏线程信息（默认显示）
  .logLevel(LogLevel.NONE)        // 默认 LogLevel.FULL
  .methodOffset(2)                // default 0
  .LogAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
}

```
## 你的无(biàn)理(tài)要求我都能满足

#### 默认的样子
```java
L.d("hello");
L.d("hello", "world", 5);   
```
![](photos/logger/log1.png)

```java
L.d("hello");
L.e("hello");
L.w("hello");
L.v("hello");
L.wtf("hello");
L.json(JSON_CONTENT);
L.xml(XML_CONTENT);
L.log(DEBUG, "tag", "message", throwable);
```

#### 我要每个日志都单独被框住
```java
L.settings().showBottomBorder(true);
```
```java
L.d("hello");
L.d("hello", "world", 5);   
```

![](photos/logger/log9.png)

#### 我要每条日志连起来（默认）

![](photos/logger/log1.png)

#### 我只想要我日志内容，不要框子
```java
L.settings().justShowMessage(true);
```
```java
L.d("hello");
L.d("hello", "world", 5);   
```

![](photos/logger/log10.png)

#### 我只想用，参数都不想填一个（我会帮你打印类和方法名）

```java
L.settings().justShowMessage(true);
```
```java
L.d();
L.w();   
```

![](photos/logger/log11.png)



#### 我要屏蔽日志（试试看能不能打印出来？）
```java
L.settings().showLog(false);
```

### 替代我的LogAdapter
```java
class CustomLogAdapter Implement LogAdapter{}
settings.LogAdapter(new CustomLogAdapter())
```

### Notes
- 使用日志筛选，格式更整齐

![](photos/logger/filter.png)

- 确认关掉日志换行选项

![](photos/logger/wrap-closed.png)

- Release使用LogLevel.NONE不输出任何日志





## ChangeLog

* 3.3.3
  * 修改日志双竖线后没空格，不对齐问题
  * 修改isJustShowMessage()==true时不显示双竖线
* 3.3.2
  * 限制行默认长度为160
  * 删除json和xml打印方法
  * 修改空log：[Null or Empty]改为[NULL]
