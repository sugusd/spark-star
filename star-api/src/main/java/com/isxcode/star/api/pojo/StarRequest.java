package com.isxcode.star.api.pojo;

import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class StarRequest {

    public StarRequest() {
        this.sparkConfig = new HashMap<>();
        this.sparkConfig.put("spark.executor.memory", "2g");
        this.sparkConfig.put("spark.driver.memory", "1g");
        this.sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

        this.yarnJobConfig = new YarnJobConfig();
        this.yarnJobConfig.setAppName("spark-star job");
        this.yarnJobConfig.setMainClass("com.isxcode.star.Execute");
        this.yarnJobConfig.setAppResourceName("star-sql-plugin");
    }

    private String executeId;

    private boolean hasReturn;

    private String sql;

    private List<String> columns;;

    private Integer page;

    private Integer pageSize;

    private String tableName;

    private Integer limit = 100;

    private String appId;

    private String db;

    private Map<String, String> sparkConfig;

    private YarnJobConfig yarnJobConfig;
}
