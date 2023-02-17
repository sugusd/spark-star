<p align="center">
  <a href="https://github.com/ispong/flink-acorn" style="border-bottom: none !important;">
    <img alt="flink-acorn" width="400" src="https://img.isxcode.com/isxcode_img/spark-star/logo.png">
  </a>
</p>

<h1 align="center">
    Spark Star
</h1>

<h4 align="center">
    ⭐ 基于Spring对远程Spark服务二次封装，实现SparkSql执行、动态资源分配以及获取运行日志等。
</h4>

<h4 align="center">
    ✨ <a href="https://spark-star.isxcode.com">https://spark-star.isxcode.com</a> ✨
</h4>

<div align="center" class="badge">

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.star/star-client)](https://search.maven.org/artifact/com.isxcode.star/star-client)

</div>

### ⚠️ 注意

> 目前仅支持`Yarn-Per-Job`模式，即一个sql执行一个yarn容器。
 
### 📒 文档

- [快速使用](https://spark-star.isxcode.com/#/zh-cn/start/%E5%BF%AB%E9%80%9F%E4%BD%BF%E7%94%A8)
- [快速安装](https://spark-star.isxcode.com/#/zh-cn/install/%E5%BF%AB%E9%80%9F%E5%AE%89%E8%A3%85)
- [Api说明](https://spark-star.isxcode.com/#/zh-cn/reference/Api%E8%AF%B4%E6%98%8E)

### 📦 使用说明

```xml
<dependency>
    <groupId>com.isxcode.star</groupId>
    <artifactId>star-client</artifactId>
    <version>1.2.0</version>
</dependency>
```

```yaml
star:
  check-servers: true
  servers:
    default:
      host: isxcode
      port: 30155
      key: acorn-key
```

```java
package com.isxcode.star.demo.controller;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final StarTemplate starTemplate;

    @GetMapping("/execute")
    public StarResponse execute(@RequestParam String sql) {

        // 配置spark运行的环境和资源
        Map<String, String> sparkConfig = new HashMap<>();
        sparkConfig.put("spark.executor.memory", "2g");
        sparkConfig.put("spark.driver.memory", "1g");
        sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

        return starTemplate.build()
                .sql(sql)
                .sparkConfig(sparkConfig)
                .execute();
    }

    @GetMapping("/getData")
    public StarResponse getData(@RequestParam String applicationId) {

        return starTemplate.build()
                .applicationId(applicationId)
                .getData();
    }

}
```

```json
{
  "code": "200",
  "msg": "提交成功",
  "data": {
    "applicationId": "application_1671005804173_0001"
  }
}
```

```json
{
    "code": "200",
    "msg": "获取数据成功",
    "data": {
        "columnNames": [
            "username",
            "age",
            "birth"
        ],
        "dataList": [
            [
                "ispong",
                "18",
                "2020-12-12"
            ]
        ]
    }
}
```

***

**Thanks for free JetBrains Open Source license**

<a href="https://www.jetbrains.com/?from=spring-demo" target="_blank" style="border-bottom: none !important;">
    <img src="https://img.isxcode.com/index_img/jetbrains/jetbrains-3.png" height="100" alt="jetbrains"/>
</a>
