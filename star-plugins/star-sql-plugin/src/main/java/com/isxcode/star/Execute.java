package com.isxcode.star;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.api.utils.ArgsUtils;
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
        starDataBuilder.columnNames(Arrays.asList(columns));

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
        StarRequest starRequest = ArgsUtils.parse(args);

        System.out.println(starRequest.toString());

        // 校验请求参数
        checkRequest(starRequest);

        // 初始化sparkSession
        SparkSession sparkSession = initSparkSession(starRequest);

        Dataset<Row> rowDataset;
        if (!Strings.isEmpty(starRequest.getJdbcUrl())) {
            // 解析sql，加载所有相关的数据库中的table
            SqlParseUtils sqlParseUtils = new SqlParseUtils();
            List<String> tableNames = sqlParseUtils.parseHiveSql(starRequest.getSql());
            System.out.println("tableNames" + tableNames);
            StringBuilder createTemplateTableBuilder = new StringBuilder();
            tableNames.forEach(e -> createTemplateTableBuilder.append(generateCreateTableSql(e, starRequest)));

            System.out.println("createTemplateTableBuilder" + createTemplateTableBuilder);
            starRequest.setSql(createTemplateTableBuilder + starRequest.getSql());
        }

        System.out.println("执行sparksql" + starRequest.getSql());
        rowDataset = sparkSession.sql(starRequest.getSql()).limit(starRequest.getLimit());

        // 导出输出
        exportResult(rowDataset);

        // 停止sparkSession
        sparkSession.stop();
    }

    public static String generateCreateTableSql(String tableName, StarRequest starRequest) {

        String sqlTemplate = "CREATE TEMPORARY VIEW " + tableName + "\n" +
            "USING org.apache.spark.sql.jdbc\n" +
            "OPTIONS (\n" +
            "  url '" + starRequest.getJdbcUrl() + "',\n" +
            "  dbtable \"" + tableName + "\",\n" +
            "  user '" + starRequest.getUsername() + "',\n" +
            "  password '" + starRequest.getPassword() + "'\n" +
            "); \n";

        return sqlTemplate;
    }

}
