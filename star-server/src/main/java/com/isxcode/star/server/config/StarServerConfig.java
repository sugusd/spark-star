package com.isxcode.star.server.config;


import com.isxcode.star.api.exception.StarException;
import com.isxcode.star.api.properties.StarProperties;
import com.isxcode.star.common.config.CommonAutoConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarProperties.class)
public class StarServerConfig {

    @Bean
    @ConditionalOnClass(StarServerConfig.class)
    public void checkEnvironment() {

        String starHomePath = System.getenv("STAR_HOME");
        if (Strings.isEmpty(starHomePath)) {
            throw new StarException("50010", "请配置STAR_HOME环境变量");
        }

        String yarnConfDir = System.getenv("YARN_CONF_DIR");
        if (Strings.isEmpty(yarnConfDir)) {
            throw new StarException("50011", "请配置YARN_CONF_DIR环境变量");
        }

    }
}
