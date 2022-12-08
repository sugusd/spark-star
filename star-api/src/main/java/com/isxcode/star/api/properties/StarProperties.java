package com.isxcode.star.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "star")
public class StarProperties {

    private String appNamePrefix = "spark-star application ";

    private String master = "local";

    private String deployMode = "cluster";

    private Map<String, String> sparkConfig = new HashMap<>();

    private String secret = "star-key";

    private Map<String, ServerInfoProperties> servers;

    private Boolean checkServers = false;
}
