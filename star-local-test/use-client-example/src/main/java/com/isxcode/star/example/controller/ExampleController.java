package com.isxcode.star.example.controller;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final StarTemplate starTemplate;

    @GetMapping("/execute")
    public StarResponse execute(@RequestParam String sql) {

        return starTemplate.build().sql(sql).execute();
    }

    @GetMapping("/executeMysql")
    public StarResponse executeMysql(@RequestParam String sql) {

        return starTemplate.build()
            .jdbcUrl("jdbc:mysql://dcloud-dev:30102/ispong_db")
            .username("ispong")
            .password("ispong123")
            .sql(sql)
            .execute();
    }

    @GetMapping("/executeSync")
    public StarResponse executeSync() {

        String sql = "" +
            "CREATE TEMPORARY VIEW users_view\n" +
            "USING org.apache.spark.sql.jdbc\n" +
            "OPTIONS (\n" +
            "    driver 'com.mysql.cj.jdbc.Driver',\n" +
            "    url 'jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    user 'ispong',\n" +
            "    password 'ispong123',\n" +
            "    dbtable 'users'\n" +
            ");\n" +
            "" +
            "insert into ispong_db.users select * from users_view where age > 18";

        return starTemplate.build().sql(sql).execute();
    }

    @GetMapping("/getStatus")
    public StarResponse getStatus(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getStatus();
    }

    @GetMapping("/getLog")
    public StarResponse getLog(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getLog();
    }

    @GetMapping("/stopJob")
    public StarResponse stopJob(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).stopJob();
    }

    @GetMapping("/getData")
    public StarResponse getData(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getData();
    }

    @GetMapping("/custom")
    public StarResponse custom(@RequestParam String sql) {

        // 配置spark运行的环境和资源
        Map<String, String> sparkConfig = new HashMap<>();
        sparkConfig.put("spark.executor.memory", "2g");
        sparkConfig.put("spark.driver.memory", "1g");
        sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

        // 配置yarn启动作业的参数，可以配置自定义插件
        YarnJobConfig yarnJobConfig = YarnJobConfig.builder()
            .appName("my app")
            .mainClass("com.isxcode.star.Execute")
            .appResourceName("star-sql-plugin")
            .build();

        return starTemplate.build()
            .sql(sql)
            .yarnJobConfig(yarnJobConfig)
            .sparkConfig(sparkConfig).execute();
    }

}
