#### Hive支持

!> 默认spark是不支持hive数据访问的。

> [Hive单节点安装参考文档](https://ispong.isxcode.com/hadoop/hive/hive%20%E5%8D%95%E8%8A%82%E7%82%B9%E5%AE%89%E8%A3%85/)

###### 修改hive-site配置文件

> 新增配置 `hive.metastore.uris`

```bash
vim /opt/hive/conf/hive-site.xml
```

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>hive.metastore.port</name>
        <value>9083</value>
    </property>

    <property>
        <name>hive.metastore.uris</name>
        <value>thrift://isxcode:9083</value>
    </property>
</configuration>
```

###### 配置spark支持Hive访问

> 需要将`hive-site.xml`这个文件软链接到`$SPARK_HOME/conf/`下

```bash
sudo ln -s $HIVE_HOME/conf/hive-site.xml $SPARK_HOME/conf/hive-site.xml
```

###### 使用案例

```java
String sql="select * from ispong_db.users";

starTemplate.build().sql(sql).execute();
```
