# 前言
说一个自己经历过的事情，有一次我在开发一个通过csv文件批量导入交易的job的时候，在UAT环境上进行性能测试，发现执行失败了。通过查看日志发现，机器空间不足了，*df -h*一看发现32G的机器只有20k的空间，然后一看日志文件的大小，就占了20G。日志这东西，不能记得太多，不然影响性能而且占空间，也不能记得太少，不然出了问题，日志查不到关键的信息

# 经验
## 1. 使用直接SLF4J，而非具体的日志框架
在项目开发中，应该在代码中直接使用[SLF4J](https://www.slf4j.org/)，而非[LOG4J](https://logging.apache.org/log4j)、[Logback](https://logback.qos.ch/)等一些框架。SLF4J是Java里上事实上的日志标准接口，通过门面模式，可以很方便替换具体日志框架的实现，降低了业务代码与日志框架的耦合度。
## 2. 生产环境只打印INFO级别的日志信息
一般而言，debug及其以下级别的信息都是没有必要显示出来的，除非是开发定位bug的时候。
## 3. 使用isDebugEanbled等相似方法降低日志性能消耗
频繁计算日志是否应该记录会对系统性能产生影响，通过显示调用判断是否需要打印方法如isDebugEanbled来优化性能:
```Java
if (logger.isDebugEnabled()) {
    logger.debug("this is debug message");
}
```
## 4. 使用lambda表达式
该经验针对第三条进一步优化。
如果我们简单封装了日志方法，比如
```Java
public void degbug(String message) {
    if (logger.isDebugEnabled()) {
        logger.debug(message);
    }
}
```
这样虽然通过isDebugEnabled方法判断使用需要打印日志提高了性能，但是如果message本身是有多个字符串拼接而成的话仍然会消耗一定的资源，比如调用上文的debug方法：
```Java
debug("client" + (clientId) + "is error");
```
尽管使用isDebugEnabled方法，但是在该方法调用入参的时候会先拼接message这个参数，然后在调用isDebugEnabled方法进行判断,如果此时是不需要打印的话，那么这个拼接message的消耗就白白浪费了。应该先判断是否需要打印，然后在拼接字符串。我们可以使用lambda表达式懒求值的特性实现:
```Java
public void degbug(Supplier<String> supplier) {
    if (logger.isDebugEnabled()) {
        logger.debug(supplier != null ? supplier.get() : null);
    }
}
```

# 说明
基于slf4j简单封装，实现了以上经验的第三、四点。下载地址：[Github](https://github.com/damingerdai/damingerdailogger), [码云](https://gitee.com/damingerdai/damingerdailogger)。