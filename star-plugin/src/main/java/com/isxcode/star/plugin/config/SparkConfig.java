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

        String confPath = System.getenv("HIVE_CONF_DIR");
        String homePath = System.getenv("HIVE_HOME");
        System.out.println("打印环境变量" + confPath + ":" + homePath);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        findConfigFile(classLoader, "hivemetastore-site.xml", false);
        System.out.println("获取成功");

        log.debug("初始化sparkSession");
        SparkSession.Builder sparkBuilder = SparkSession
            .builder()
            .appName(starPluginProperties.getAppNamePrefix())
            .master(starPluginProperties.getMaster())
            .enableHiveSupport();

        starPluginProperties.getSparkConfig().forEach(sparkBuilder::config);

        return sparkBuilder.getOrCreate();
    }

    private static URL checkConfigFile(File f) {
        try {
            return (f.exists() && f.isFile()) ? f.toURI().toURL() : null;
        } catch (Throwable e) {
            System.err.println("Error looking for config " + f + ": " + e.getMessage());
            return null;
        }
    }

    private static URL findConfigFile(ClassLoader classLoader, String name, boolean doLog) {
        URL result = classLoader.getResource(name);
        if (result == null) {
            String confPath = System.getenv("HIVE_CONF_DIR");
            result = checkConfigFile(new File(confPath, name));
            if (result == null) {
                String homePath = System.getenv("HIVE_HOME");
                String nameInConf = "conf" + File.separator + name;
                result = checkConfigFile(new File(homePath, nameInConf));
                if (result == null) {
                    URI jarUri = null;
                    try {
                        jarUri = HiveConf.class.getProtectionDomain().getCodeSource().getLocation().toURI();
                    } catch (Throwable e) {
                        System.err.println("Cannot get jar URI: " + e.getMessage());
                    }
                    System.out.println("jarUri:" + jarUri);
                    System.out.println("nameInConf:" + nameInConf);
                    result = checkConfigFile(new File(new File(jarUri).getParentFile(), nameInConf));
                }
            }
        }
        return result;
    }
}
