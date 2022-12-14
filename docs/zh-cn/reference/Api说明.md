### - 执行SparkSql

> `http://localhost:8080/execute?sql=select * from ispong_db.users` 

```java
public StarResponse execute(@RequestParam String sql) {

    return starTemplate.build().sql(sql).execute();
}
```

```json

```

#### - 查询表中数据


#### - 分页查询数据


#### - 查看运行日志


#### - 查看sql运行状态


#### - 停止sql运行
