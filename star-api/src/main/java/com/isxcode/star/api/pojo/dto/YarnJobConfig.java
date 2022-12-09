package com.isxcode.star.api.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YarnJobConfig {

    String appName = "spark-star job";

    String mainClass = "com.isxcode.star.Execute";

    String appResourceName = "star-sql-plugin";

}
