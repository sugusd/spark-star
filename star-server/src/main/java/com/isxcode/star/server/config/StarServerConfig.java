package com.isxcode.star.server.config;


import com.isxcode.star.api.properties.StarProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarProperties.class)
public class StarServerConfig {

}
