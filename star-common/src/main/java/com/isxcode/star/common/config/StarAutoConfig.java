package com.isxcode.star.common.config;

import com.isxcode.star.common.constant.MsgConstants;
import com.isxcode.star.common.constant.SecurityConstants;
import com.isxcode.star.common.constant.UrlConstants;
import com.isxcode.star.common.properties.StarClientProperties;
import com.isxcode.star.common.properties.StarPluginProperties;
import com.isxcode.star.common.response.StarResponse;
import com.isxcode.star.common.template.StarTemplate;
import com.isxcode.star.common.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({StarPluginProperties.class, StarClientProperties.class})
@RequiredArgsConstructor
public class StarAutoConfig {

    private final StarClientProperties starClientProperties;

    /**
     * 初始化用户默认配置的节点信息
     *
     * @param starClientProperties 结点配置信息
     * @return starTemplate
     */
    @Bean("starTemplate")
    public StarTemplate starTemplate(StarClientProperties starClientProperties) {

        return new StarTemplate(starClientProperties);
    }

    /*
     * 测试插件连接是否正常
     *
     * @ispong
     */
    @Bean
    @ConditionalOnProperty(prefix = "star.client.workers.default", name = {"host", "port", "key"})
    public void checkServerStatus() {

        if (!starClientProperties.getCheckWorker()) {
            return;
        }

        if (starClientProperties.getWorkers() == null) {
            System.out.println("Star配置中未找到server配置");
            return;
        }

        System.out.println("=================检查节点=======================");
        starClientProperties.getWorkers().forEach((k, v) -> {
            try {
                // 检查用户配置的节点是否有效
                String heartCheckUrl = String.format(UrlConstants.BASE_URL + UrlConstants.HEART_CHECK_URL, v.getHost(), v.getPort());
                Map<String, String> headers = new HashMap<>();
                headers.put(SecurityConstants.HEADER_AUTHORIZATION, v.getKey());
                StarResponse starResponse = HttpUtils.doGet(heartCheckUrl, headers, StarResponse.class);

                if (MsgConstants.SUCCESS_CODE.equals(starResponse.getCode())) {
                    System.out.println(k + ":" + v.getHost() + ":[ok]");
                } else {
                    System.out.println(k + ":" + v.getHost() + ":[error]");
                }
            } catch (Exception e) {
                System.out.println(k + ":" + v.getHost() + ":[error]");
            }
        });
        System.out.println("==============================================");
    }
}
