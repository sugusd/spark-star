package com.isxcode.star.plugin.config;

import com.isxcode.star.common.properties.StarPluginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URI;
import java.net.URL;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SparkConfig {

    private final StarPluginProperties starPluginProperties;

    @Bean("sparkSession")
    public SparkSession sparkSession() {

        log.debug("初始化sparkSession");
        SparkSession.Builder sparkBuilder = SparkSession
            .builder()
            .appName(starPluginProperties.getAppNamePrefix())
            .master(starPluginProperties.getMaster())
            .enableHiveSupport();

        starPluginProperties.getSparkConfig().forEach(sparkBuilder::config);

        return sparkBuilder.getOrCreate();
    }
}
