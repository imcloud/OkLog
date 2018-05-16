# OkLog
log util for android, support kotlin

# 导入
```gradle
compile 'com.accegg.oklog:oklog:0.2.0'
```
# Usage
any where
```java
  OkLog.v(log);
```

if you want configure, initialize before call other method
```java
        // init in java
        ConfigureBuilder builder = new ConfigureBuilder(BuildConfig.DEBUG);
        builder.setMethodCount(3);
        builder.setShowMethod(true);
        builder.setShowThread(true);
        builder.showTime(true);
        Configure configure = builder.build();
        OkLog.init(configure);
        
        /**
         * how to init in kotlin
         */
        OkLog.init(with(OkLog.ConfigureBuilder(BuildConfig.DEBUG), {
            showMethod(true)
            showThread(true)
            showTime(true)
            methodCount(2)
            build()
        }))
```
# 预览
![](https://github.com/imcloud/OkLog/blob/master/Screenshot/log.png)
# Blog
[https://aceegg.com](https://aceegg.com)
