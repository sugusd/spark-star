package com.isxcode.star.client.config;

import com.isxcode.oxygen.common.properties.CommonProperties;
import com.isxcode.star.api.constant.MsgConstants;
import com.isxcode.star.api.constant.UrlConstants;
import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.properties.ServerInfoProperties;
import com.isxcode.star.api.properties.StarProperties;
import com.isxcode.star.api.utils.HttpUtils;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({StarProperties.class, ServerInfoProperties.class})
@RequiredArgsConstructor
public class StarAutoConfig {

    private final StarProperties starProperties;

    private final CommonProperties commonProperties;

    @Bean("starTemplate")
    public StarTemplate starTemplate(StarProperties starProperties,  CommonProperties commonProperties) {

        return new StarTemplate(starProperties, commonProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "star.servers.default", name = {"host", "port", "key"})
    public void checkServerStatus() {

        if (!starProperties.getCheckServers()) {
            return;
        }

        if (starProperties.getServers() == null) {
            System.out.println("Star配置中未找到server配置");
            return;
        }

        System.out.println("=================检查节点=======================");
        starProperties.getServers().forEach((k, v) -> {
            try {
                // 检查用户配置的节点是否有效
                String heartCheckUrl = String.format(UrlConstants.BASE_URL + UrlConstants.HEART_CHECK_URL, v.getHost(), v.getPort());
                Map<String, String> headers = new HashMap<>();
                headers.put("star-key", v.getKey());
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
