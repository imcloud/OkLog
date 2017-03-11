# OkLog
log util for android

# environment
### about gradle and sdk
```gradle
  // app
  compileSdkVersion 25
  buildToolsVersion "25.0.2"
  //project
  classpath 'com.android.tools.build:gradle:2.3.0'
```
### Android studio plugin
`kotlin plugin1.1.0+`

# 导入
```gradle
compile 'com.accegg.oklog:oklog:0.1.3'
```
# Usage
any where
```java
  OkLog.v(log);
```
disable print log,in your custom application
```java
  OkLog.init(false);
```
# 预览
![](https://github.com/imcloud/OkLog/blob/master/Screenshot/log.png)
