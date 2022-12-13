package com.isxcode.star.common.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * config flysql
 *
 * @author ispong
 * @since 0.0.1
 */
@Data
@ConfigurationProperties("oxygen.common")
public class CommonProperties {

    private String headerAuthorization;

    private String secret;

    private Boolean enable = false;
}
