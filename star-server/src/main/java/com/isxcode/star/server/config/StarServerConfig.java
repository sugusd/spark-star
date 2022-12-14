package com.isxcode.star.server.config;

import com.isxcode.star.api.properties.StarProperties;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(StarProperties.class)
public class StarServerConfig {

    private final ConfigurableApplicationContext context;

    @Bean
    @ConditionalOnClass(StarServerConfig.class)
    public void checkEnvironment() {

        String starHomePath = System.getenv("STAR_HOME");
        if (Strings.isEmpty(starHomePath)) {
            System.out.println("ERROR:请配置STAR_HOME环境变量");
            context.close();
        }

        String yarnConfDir = System.getenv("YARN_CONF_DIR");
        if (Strings.isEmpty(yarnConfDir)) {
            System.out.println("ERROR:请配置YARN_CONF_DIR环境变量");
            context.close();
        }

    }
}
