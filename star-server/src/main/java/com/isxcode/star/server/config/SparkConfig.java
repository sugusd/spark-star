package com.isxcode.star.server.config;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SparkConfig {

    @Bean("sparkSession")
    public SparkSession initSparkSession() {

        SparkConf sparkConf = new SparkConf();

        sparkConf.set("spark.app.name", "star-session");
        sparkConf.set("spark.master", "local[*]");
        sparkConf.set("spark.ui.port", "30126");
        sparkConf.set("spark.driver.memory", "2g");
        sparkConf.set("spark.executor.memory", "2g");
        sparkConf.set("spark.sql.storeAssignmentPolicy", "LEGACY");

        return SparkSession.builder()
            .config(sparkConf)
            .getOrCreate();
    }
}
