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
@NoArgsConstructor
public class StarRequest {

    private String executeId;

    private boolean hasReturn;

    private String sql;

    private List<String> columns;;

    private Integer page;

    private Integer pageSize;

    private String tableName;

    private Integer limit = 100;

    private String applicationId;

    private String db;

    private String jdbcUrl;

    private String username;

    private String password;

    private String driverClassName;

    private String dbType;

    private Map<String, Object> kafkaConfig;

    private Map<String, String> sparkConfig= new HashMap<String, String>() {{
        put("spark.executor.memory", "1g");
        put("spark.driver.memory", "1g");
    }};

    private YarnJobConfig yarnJobConfig = new YarnJobConfig("spark-star", "com.isxcode.star.Execute", "star-sql-plugin");
}
