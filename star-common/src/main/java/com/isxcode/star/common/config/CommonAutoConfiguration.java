package com.isxcode.star.common.config;

import com.isxcode.star.common.controller.ExceptionController;
import com.isxcode.star.common.filter.CommonKeyFilter;
import com.isxcode.star.common.properties.CommonProperties;
import com.isxcode.star.common.response.GlobalExceptionAdvice;
import com.isxcode.star.common.response.SuccessResponseAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(CommonProperties.class)
public class CommonAutoConfiguration {

    private final CommonProperties commonProperties;

    private final MessageSource messageSource;

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    @ConditionalOnProperty(prefix = "oxygen.common", name = "enable", havingValue = "true")
    private CommonKeyFilter initCommonFilter() {

        return new CommonKeyFilter(commonProperties);
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private ExceptionController initExceptionController() {

        return new ExceptionController();
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private GlobalExceptionAdvice initGlobalExceptionAdvice() {

        return new GlobalExceptionAdvice();
    }

    @Bean
    @ConditionalOnClass(CommonAutoConfiguration.class)
    private SuccessResponseAdvice initSuccessResponseAdvice() {

        return new SuccessResponseAdvice(messageSource);
    }
}
