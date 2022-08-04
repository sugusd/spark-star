package com.isxcode.star.plugin.config;

import com.isxcode.star.common.properties.StarPluginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SparkConfig {

    private final StarPluginProperties starPluginProperties;

    @Bean("sparkSession")
    public SparkSession sparkSession() {

        String confPath = System.getenv("HIVE_CONF_DIR");
        String homePath = System.getenv("HIVE_HOME");
        System.out.println("打印环境变量" + confPath + ":" + homePath);

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
