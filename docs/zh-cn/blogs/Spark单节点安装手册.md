🧙 Questions

> spark `3.1.1` 单节点安装

☄️ Ideas

##### 下载

```bash
nohup wget https://archive.apache.org/dist/spark/spark-3.1.1/spark-3.1.1-bin-hadoop3.2.tgz >> download_spark.log 2>&1 &  
tail -f download_spark.log
```

##### 创建安装目录

```bash
sudo mkdir -p /data/spark
sudo chown -R ispong:ispong /data/spark
```

##### 解压并创建软连接

```bash
tar -vzxf spark-3.1.1-bin-hadoop3.2.tgz -C /data/spark
sudo ln -s /data/spark/spark-3.1.1-bin-hadoop3.2 /opt/spark
```

##### 配置环境变量

```bash
sudo vim /etc/profile
```

```bash
export SPARK_HOME=/opt/spark 
export PATH=$PATH:$SPARK_HOME/bin  
export PATH=$PATH:$SPARK_HOME/sbin  
```

```bash
source /etc/profile
```

##### 测试

```bash
spark-submit --version
```

```text
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 3.1.1
      /_/
                        
Using Scala version 2.12.10, OpenJDK 64-Bit Server VM, 1.8.0_352
Branch HEAD
Compiled by user ubuntu on 2021-02-22T01:33:19Z
Revision 1d550c4e90275ab418b9161925049239227f3dc9
Url https://github.com/apache/spark
Type --help for more information.
```

🔗 Links

- [spark website](http://spark.apache.org/)
