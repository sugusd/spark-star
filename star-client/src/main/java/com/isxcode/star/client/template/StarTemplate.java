package com.isxcode.star.client.template;

import com.isxcode.star.api.constant.URLs;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import com.isxcode.star.api.properties.ServerInfoProperties;
import com.isxcode.star.api.properties.StarProperties;
import com.isxcode.star.api.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarTemplate {

    private final StarProperties starProperties;

    public StarTemplate.Builder build(String workerName) {

        ServerInfoProperties workerProperties = starProperties.getServers().get(workerName);
        return new Builder(workerProperties);
    }

    public StarTemplate.Builder build() {

        return build("default");
    }

    public StarTemplate.Builder build(String host, int port, String key) {

        return new Builder(host, port, key);
    }

    public static class Builder {

        public StarRequest starRequest = new StarRequest();

        private final ServerInfoProperties serverInfoProperties;

        public Builder(ServerInfoProperties serverInfoProperties) {

            this.serverInfoProperties = serverInfoProperties;
        }

        public Builder(String host, int port, String key) {

            ServerInfoProperties workerProperties = new ServerInfoProperties();
            workerProperties.setHost(host);
            workerProperties.setPort(port);
            workerProperties.setKey(key);
            this.serverInfoProperties = workerProperties;
        }

        public Builder sql(String sql) {

            starRequest.setSql(sql);
            return this;
        }

        public Builder yarnJobConfig(YarnJobConfig yarnJobConfig) {

            starRequest.setYarnJobConfig(yarnJobConfig);
            return this;
        }

        public Builder sparkConfig(Map<String,String> sparkConfig) {

            starRequest.getSparkConfig().putAll(sparkConfig);
            return this;
        }

        public Builder applicationId(String applicationId) {

            starRequest.setApplicationId(applicationId);
            return this;
        }

        public Builder limit(Integer limit) {

            starRequest.setLimit(limit);
            return this;
        }

        public Builder db(String database) {

            starRequest.setDb(database);
            return this;
        }

        public StarResponse execute() {

            String executeUrl = parseExecuteUrl(URLs.EXECUTE_URL);
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse getData() {

            String executeUrl = parseExecuteUrl(URLs.GET_DATA_URL);
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse getStatus() {

            String executeUrl = parseExecuteUrl(URLs.GET_STATUS_URL);
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse getLog() {

            String executeUrl = parseExecuteUrl(URLs.GET_LOG_URL);
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse stopJob() {

            String executeUrl = parseExecuteUrl(URLs.STOP_JOB_URL);
            return requestStarServer(executeUrl, starRequest);
        }

        public String parseExecuteUrl(String url) {

            return String.format(URLs.HTTP + URLs.BASE_URL + url, serverInfoProperties.getHost(), serverInfoProperties.getPort());
        }

        public StarResponse requestStarServer(String url, StarRequest starRequest) {

            Map<String, String> headers = new HashMap<>();
            headers.put("star-key", serverInfoProperties.getKey());

            try {
                return HttpUtils.doPost(url, headers, starRequest, StarResponse.class);
            } catch (IOException e) {
                return new StarResponse("500", e.getMessage());
            }
        }
    }

}
