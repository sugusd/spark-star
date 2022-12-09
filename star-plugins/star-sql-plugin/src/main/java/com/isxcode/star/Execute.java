package com.isxcode.star;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Execute {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
            .config("spark.driver.memory", "1g")
            .config("spark.executor.memory", "2g")
            .config("hive.metastore.uris", "thrift://localhost:30104")
            .enableHiveSupport()
            .getOrCreate();

        Dataset<Row> rowDataset = sparkSession.sql("select * from ispong_db.users");

        // 执行spark
        System.out.println("===> 返回");

        rowDataset.foreach(e -> {
            System.out.println(e.get(0));
        });

        // 停止session
        sparkSession.stop();
    }
}
