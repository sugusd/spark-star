package com.isxcode.demo1.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Bean("SparkSession")
    public SparkSession sparkBean() {

        return SparkSession
                .builder()
                .appName("isxcode spark demo1")
                .master("yarn")
                .config("hive.metastore.uris", "thrift://172.26.34.162:9083")
                .config("spark.hadoop.yarn.resourcemanager.address", "172.26.34.162:8032")
                .enableHiveSupport()
                .getOrCreate();
    }
}
