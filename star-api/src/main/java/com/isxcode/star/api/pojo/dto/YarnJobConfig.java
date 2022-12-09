package com.isxcode.star.api.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class YarnJobConfig {

    String appName;

    String mainClass;

    String appResourceName;

    public YarnJobConfig() {

        this.appName = "spark-star job";
        this.mainClass = "com.isxcode.star.Execute";
        this.appResourceName = "star-sql-plugin";
    }
}
