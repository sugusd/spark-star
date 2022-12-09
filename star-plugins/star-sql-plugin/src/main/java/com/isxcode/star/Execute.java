package com.isxcode.star;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.*;

public class Execute {

    public static SparkSession initSparkSession(StarRequest starRequest) {

        SparkSession.Builder sparkSessionBuilder = SparkSession.builder();

        SparkConf conf = new SparkConf();
        for (Map.Entry<String, String> entry : starRequest.getSparkConfig().entrySet()) {
            conf.set(entry.getKey(), entry.getValue());
        }

        return sparkSessionBuilder
            .config(conf)
            .enableHiveSupport()
            .getOrCreate();
    }

    public static void checkRequest(StarRequest starRequest) {

        if (Strings.isEmpty(starRequest.getSql())) {
            throw new RuntimeException("sql is empty");
        }

        if (starRequest.getLimit() > 500) {
            throw new RuntimeException("limit must low than 500");
        }
    }

    public static void exportResult(Dataset<Row> rowDataset) {

        StarData.StarDataBuilder starDataBuilder = StarData.builder();

        // 获取列信息
        String[] columns = rowDataset.columns();
        starDataBuilder.columnNames(Collections.singletonList(Arrays.toString(columns)));

        // 获取数据
        List<List<String>> dataList = new ArrayList<>();
        List<Row> rows = rowDataset.collectAsList();
        rows.forEach(e -> {
            List<String> metaData = new ArrayList<>();
            for (int i = 0; i < e.size(); i++) {
                metaData.add(String.valueOf(e.get(i)));
            }
            dataList.add(metaData);
        });
        StarData starData = starDataBuilder.dataList(dataList).build();

        // 打印结果
        System.out.println(JSON.toJSONString(starData));
    }

    public static void main(String[] args) {

        // 解析请求参数
        StarRequest starRequest = JSON.parseObject(String.valueOf(args[0]) + "}}", StarRequest.class);

        // 校验请求参数
        checkRequest(starRequest);

        // 初始化sparkSession
        SparkSession sparkSession = initSparkSession(starRequest);

        // 执行sql
        Dataset<Row> rowDataset = sparkSession.sql(starRequest.getSql()).limit(starRequest.getLimit());

        // 导出输出
        exportResult(rowDataset);

        // 停止sparkSession
        sparkSession.stop();
    }
}
