package com.isxcode.star;

import org.apache.logging.log4j.util.Strings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Execute {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
            .config("spark.driver.memory", "1g")
            .config("spark.executor.memory", "2g")
            .config("hive.metastore.uris", "thrift://localhost:9083")
            .enableHiveSupport()
            .getOrCreate();

        Dataset<Row> rowDataset = sparkSession.sql("select * from ispong_db.users").limit(10);
        System.out.println("rowDataset: 获取dataset");
        System.out.println("===> 返回");

        String[] columns = rowDataset.columns();
        System.out.println("columns: " + Arrays.toString(columns));

        // 获取数据值
        List<List<String>> dataList = new ArrayList<>();
        List<Row> rows = rowDataset.collectAsList();
        rows.forEach(e -> {
            List<String> metaData = new ArrayList<>();
            for (int i = 0; i < e.size(); i++) {
                metaData.add(String.valueOf(e.get(i)));
            }
            dataList.add(metaData);
        });
        System.out.println("dataList: " + dataList);

        // 停止session
        sparkSession.stop();
    }
}
