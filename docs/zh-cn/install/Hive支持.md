#### 前提

- [Hadoop(3.2.4)单节点安装](https://ispong.isxcode.com/hadoop/hadoop/hadoop%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)
- [Hadoop开启jobhistory](https://ispong.isxcode.com/hadoop/hadoop/hadoop%20Jobhistory/)
- [Spark(3.1.1)单节点安装](https://ispong.isxcode.com/hadoop/spark/spark%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)

###### 下载代码

> 推荐使用gitee下载 </br>
> `git clone https://gitee.com/ispong/spark-star.git`

```bash
git clone https://github.com/ispong/spark-star.git
```

###### 编译项目

!> [建议安装](https://ispong.isxcode.com/spring/maven/maven%20%E5%AE%89%E8%A3%85/)`3.8.6+`版本的Maven

> 打包位置 `spark-star/star-dist/target/spark-star-${version}-bin/spark-star-${version}`

```bash
mvn clean package -DskipTests
```

```log
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ star-local-test ---
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] spark-star 1.2.0 ................................... SUCCESS [  0.059 s]
[INFO] star-api 1.2.0 ..................................... SUCCESS [  1.782 s]
[INFO] star-common 1.2.0 .................................. SUCCESS [  0.552 s]
[INFO] star-client 1.2.0 .................................. SUCCESS [  3.763 s]
[INFO] star-server 1.2.0 .................................. SUCCESS [  1.374 s]
[INFO] star-plugins 1.2.0 ................................. SUCCESS [  0.001 s]
[INFO] star-sql-plugin 1.2.0 .............................. SUCCESS [  4.777 s]
[INFO] star-dist 1.2.0 .................................... SUCCESS [  0.694 s]
[INFO] use-client-example 2.7.5 ........................... SUCCESS [  0.731 s]
[INFO] star-local-test 1.2.0 .............................. SUCCESS [  0.001 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  14.223 s
[INFO] Finished at: 2022-12-15T17:52:38+08:00
[INFO] ------------------------------------------------------------------------
```

> 将打好的包软连接到`/opt/star`下，便于定位

```bash
sudo ln -s /home/ispong/spark-star/star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0 /opt/star
```

###### 配置环境变量

```bash
sudo vim /etc/profile
```

```bash
export STAR_HOME=/opt/star
export PATH=$PATH:$STAR_HOME/bin
```

```bash
source /etc/profile
```

> 查看版本

```bash
star version
```

```text
[dehoop@dcloud opt]$ star version
spark-star version is v1.2.0
```

###### 修改配置

```bash
star config
```

```yaml
# 客户端访问的端口号
server:
  port: 30156 
  
# 密钥，让客户端访问的凭证  
star:
  secret: star-key
```

###### 启动服务

> 默认端口号 `30156` </br>
> 默认密钥 `acorn-key`

```bash
star start
```

```log
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/home/dehoop/spark-star/star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0/lib/star-server.jar!/BOOT-INF/lib/logback-classic-1.2.3.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/home/dehoop/spark-star/star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0/lib/star-server.jar!/BOOT-INF/lib/slf4j-log4j12-1.7.30.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [ch.qos.logback.classic.util.ContextSelectorStaticBinder]

                            __                       __
  _________________ _______|  | __           _______/  |______ _______
 /  ___/\____ \__  \\_  __ \  |/ /  ______  /  ___/\   __\__  \\_  __ \
 \___ \ |  |_> > __ \|  | \/    <  /_____/  \___ \  |  |  / __ \|  | \/
/____  >|   __(____  /__|  |__|_ \         /____  > |__| (____  /__|
     \/ |__|       \/           \/              \/            \/


version: 1.2.0

2022-12-15 17:57:17.874  INFO 46131 --- [           main] com.isxcode.star.server.StarApplication  : Starting StarApplication v1.2.0 on dcloud with PID 46131 (/home/dehoop/spark-star/star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0/lib/star-server.jar started by dehoop in /home/dehoop/spark-star/star-dist/target/spark-star-1.2.0-bin/spark-star-1.2.0/bin)
2022-12-15 17:57:17.877  INFO 46131 --- [           main] com.isxcode.star.server.StarApplication  : The following profiles are active: star
2022-12-15 17:57:18.958  INFO 46131 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 30156 (http)
2022-12-15 17:57:18.967  INFO 46131 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-12-15 17:57:18.968  INFO 46131 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.34]
2022-12-15 17:57:19.029  INFO 46131 --- [           main] o.a.c.c.C.[.[localhost].[/spark-star]    : Initializing Spring embedded WebApplicationContext
2022-12-15 17:57:19.029  INFO 46131 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1107 ms
2022-12-15 17:57:19.387  INFO 46131 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2022-12-15 17:57:19.616  INFO 46131 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 30156 (http) with context path '/spark-star'
2022-12-15 17:57:19.618  INFO 46131 --- [           main] com.isxcode.star.server.StarApplication  : Started StarApplication in 2.12 seconds (JVM running for 2.456)
```

###### 插件关闭

```bash
star stop
```
