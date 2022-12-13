<h1 align="center">
    Spark Star
</h1>

<h4 align="center">
    通过Spring插件的形式，实现对不同服务器与不同版本的Spark做统一管理。
</h4>

<h4 align="center">
    ✨✨✨ <a href="https://spark-star.isxcode.com">https://spark-star.isxcode.com</a> ✨✨✨
</h4>

### 📢 公告

> 目前支持`3.1.1(apache)`版本，其他版本尚未支持，项目仅供参考。
 
### 📒 文档

- [快速使用](https://spark-star.isxcode.com/#/zh-cn/start/快速使用)

### 📦 使用说明

[![Maven Version](https://img.shields.io/maven-central/v/com.isxcode.star/star-common)](https://search.maven.org/artifact/com.isxcode.star/star-common)

```xml
<dependency>
    <groupId>com.isxcode.star</groupId>
    <artifactId>star-common</artifactId>
    <version>1.0.0</version>
</dependency>
```

```yaml
star:
  default:
    host: 39.99.140.167
    port: 30156
    key: star-key
```

```java
public class Demo{

    private final StarTemplate starTemplate;
    
    public void demo(){

        StarResponse starResponse = starTemplate.build()
            .sql("select * from ispong.userinfo")
            .query();
        
        log.debug("starResponse {}", starResponse.toString());
    }
}
```

```log
2022-08-03 12:11:27.926 DEBUG 21256 --- [nio-8080-exec-3] c.i.s.t.controller.TemplateController    : starResponse StarResponse(code=200, message=操作成功, starData=StarData(columnNames=[id, username, sex, birth, address, school, job, length, color, app], dataList=[[999bdcf0-8b01-11ec-adf2-9078414180e2, 澹申毋, 中, 2012-01-30, 山西省, 硕士, 品质管制工程师, 218, 靛, 喜马拉雅], [999c520c-8b01-11ec-902d-9078414180e2, 连丘, 中, 2003-04-26, 北京市, 初中, 职业运动员, 152, 橙, 微博], [999c520d-8b01-11ec-a5a7-9078414180e2, 满丰, 男, 1982-03-24, 海南省, 硕士, 时装模特儿, 163, 红, 喜马拉雅], [999c520e-8b01-11ec-8a7f-9078414180e2, 充邢, 中, 1980-12-08, 云南省, 高中, 水电工, 152, 黄, 高德]], appId=null, appState=null, log=null, eventType=null, databases=null))
```

### 👏 社区开发

- 欢迎一同维护开发，具体请参照[开发文档](https://spark-star.isxcode.com/#/zh-cn/contributing) 。
- 如需加入我们，请联系邮箱 `ispong@outlook.com` 。
