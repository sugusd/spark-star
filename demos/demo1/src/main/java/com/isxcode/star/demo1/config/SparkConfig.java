package com.isxcode.star.demo1.config;

import com.isxcode.star.demo1.properties.DemoProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class SparkConfig {

    private final DemoProperties demoProperties;

    public SparkConfig(DemoProperties demoProperties) {
        this.demoProperties = demoProperties;
    }

    public void initConfigFile() {

        List<String> siteFileList = new ArrayList<>();
        siteFileList.add("core-site.xml");
        siteFileList.add("hdfs-site.xml");
        siteFileList.add("mapred-site.xml");
        siteFileList.add("yarn-site.xml");

        // 读取外部文件
        siteFileList.forEach(e -> {
            Path path = Paths.get(demoProperties.getHadoopConfigPath() + e);
            try {
                UrlResource urlResource = new UrlResource(path.toUri());
                String content = new BufferedReader(new InputStreamReader(urlResource.getInputStream())).lines().collect(Collectors.joining("\n"));
//                System.out.println(e + "==" + content);
                File file = new ClassPathResource(e).getFile();
                System.out.println("file ==" + new BufferedReader(new InputStreamReader(new FileInputStream(file))).lines().collect(Collectors.joining("\n")));
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
//                bufferedWriter.write(content);
//                bufferedWriter.flush();
//                bufferedWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Bean("SparkSession")
    public SparkSession sparkBean() {

        // 初始化配置文件进入spring的项目流中
        initConfigFile();

        log.info("初始化sparkSession");
        SparkSession.Builder sparkBuilder = SparkSession
                .builder()
                .appName(demoProperties.getAppName())
                .master(demoProperties.getMaster());

        demoProperties.getSparkConfig().forEach(sparkBuilder::config);

        return sparkBuilder.enableHiveSupport().getOrCreate();
    }
}
