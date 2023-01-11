package com.isxcode.star;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.api.utils.ArgsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.*;

@Slf4j
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

        starDataBuilder.columnNames(Arrays.asList(rowDataset.columns()));

        List<List<String>> dataList = new ArrayList<>();
        rowDataset.collectAsList().forEach(e -> {
            List<String> metaData = new ArrayList<>();
            for (int i = 0; i < e.size(); i++) {
                metaData.add(String.valueOf(e.get(i)));
            }
            dataList.add(metaData);
        });
        StarData starData = starDataBuilder.dataList(dataList).build();

        System.out.println(JSON.toJSONString(starData));
    }

    public static void main(String[] args) throws InterruptedException {

        StarRequest starRequest = ArgsUtils.parse(args);
        log.info("starRequest: {}", starRequest);

        checkRequest(starRequest);

        if (starRequest.getKafkaConfig() != null) {

            SparkConf conf = new SparkConf();
            for (Map.Entry<String, String> entry : starRequest.getSparkConfig().entrySet()) {
                conf.set(entry.getKey(), entry.getValue());
            }

            Map<String, Object> kafkaConfig = starRequest.getKafkaConfig();
            kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

            try (JavaStreamingContext javaStreamingContext = new JavaStreamingContext(conf, new Duration(1000))) {

                SparkSession sparkSession = initSparkSession(starRequest);
                HiveContext hiveContext = new HiveContext(javaStreamingContext.sparkContext());

                HashSet<String> topics = new HashSet<>();
                topics.add(String.valueOf(starRequest.getKafkaConfig().get("topic")));

                JavaDStream<ConsumerRecord<String, String>> directStream = KafkaUtils.createDirectStream(
                    javaStreamingContext,
                    LocationStrategies.PreferConsistent(),
                    ConsumerStrategies.Subscribe(topics, starRequest.getKafkaConfig()));

                List<String> columns = Arrays.asList(String.valueOf(starRequest.getKafkaConfig().get("columns")).split(","));

                directStream
                    .filter(d -> {
                        String[] split = d.value().split(",");
                        return split.length >= columns.size();
                    }).map(rdd -> {
                        KafkaRow record = new KafkaRow();
                        record.setRecord(rdd.value());
                        return record;
                    }).foreachRDD(e -> {
                        if (e.count() > 0) {
                            Dataset<Row> dataFrame = sparkSession.createDataFrame(e, KafkaRow.class);
                            for (String column : columns) {
                                UDF1<String, String> SplitRecord = (record) -> record.split(",")[columns.indexOf(column)];
                                sparkSession.udf().register("SplitRecord", SplitRecord, DataTypes.StringType);
                                dataFrame = dataFrame.withColumn(column, functions.callUDF("SplitRecord", dataFrame.col("record")));
                            }

                            dataFrame = dataFrame.drop(dataFrame.col("record"));
                            dataFrame.createOrReplaceTempView(String.valueOf(starRequest.getKafkaConfig().get("name")));
                            hiveContext.sql(starRequest.getSql());
                        }
                    });

                javaStreamingContext.start();
                javaStreamingContext.awaitTermination();
            }
        } else {
            SparkSession sparkSession = initSparkSession(starRequest);

            Dataset<Row> rowDataset;
            if (!Strings.isEmpty(starRequest.getJdbcUrl())) {
                SqlParseUtils sqlParseUtils = new SqlParseUtils();
                List<String> tableNames = sqlParseUtils.parseHiveSql(starRequest.getSql());
                tableNames.forEach(e -> {
                    String createTableSql = generateCreateTableSql(e, starRequest);
                    sparkSession.sql(createTableSql);
                });
            }
            if (starRequest.getSql().contains(";")) {
                Arrays.asList(starRequest.getSql().split(";")).forEach(sparkSession::sql);
            } else {
                rowDataset = sparkSession.sql(starRequest.getSql()).limit(starRequest.getLimit());
                exportResult(rowDataset);
                sparkSession.stop();
            }
        }
    }

    public static String generateCreateTableSql(String tableName, StarRequest starRequest) {

        return  "CREATE TEMPORARY VIEW " + tableName + "\n" +
            "USING org.apache.spark.sql.jdbc\n" +
            "OPTIONS (\n" +
            "  driver '" + starRequest.getDriverClassName() + "',\n" +
            "  url '" + starRequest.getJdbcUrl() + "',\n" +
            "  dbtable \"" + tableName + "\",\n" +
            "  user '" + starRequest.getUsername() + "',\n" +
            "  password '" + starRequest.getPassword() + "'\n" +
            "); \n";
    }
}

