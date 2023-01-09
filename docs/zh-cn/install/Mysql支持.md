#### Hive支持

!> 默认spark是不支持mysql数据访问的。

> 下面以Mysql数据库为例，[Mysql安装参考文档](https://ispong.isxcode.com/spring/mysql/mysql%20docker%E5%AE%89%E8%A3%85/)。

###### 下载jdbc驱动

```bash
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.22.tar.gz
tar -vzxf mysql-connector-java-8.0.22.tar.gz 
mv mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar $SPARK_HOME/jars/
```
