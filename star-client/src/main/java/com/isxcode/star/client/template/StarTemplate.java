package com.isxcode.star.client.template;

import com.isxcode.oxygen.common.properties.CommonProperties;
import com.isxcode.star.api.constant.UrlConstants;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.StarResponse;
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

    private final CommonProperties commonProperties;

    public StarTemplate.Builder build(String workerName, CommonProperties commonProperties) {

        ServerInfoProperties workerProperties = starProperties.getServers().get(workerName);
        return new Builder(workerProperties, commonProperties);
    }

    public StarTemplate.Builder build(String workerName) {

        return build(workerName, commonProperties);
    }

    public StarTemplate.Builder build() {

        return build("default", commonProperties);
    }

    public StarTemplate.Builder build(String host, int port, String key) {

        return new Builder(host, port, key, commonProperties);
    }

    public static class Builder {

        public StarRequest starRequest = new StarRequest();

        private final ServerInfoProperties serverInfoProperties;

        private final CommonProperties commonProperties;

        public Builder(ServerInfoProperties serverInfoProperties, CommonProperties commonProperties) {

            this.serverInfoProperties = serverInfoProperties;
            this.commonProperties = commonProperties;
        }

        public Builder(String host, int port, String key, CommonProperties commonProperties) {

            ServerInfoProperties workerProperties = new ServerInfoProperties();
            workerProperties.setHost(host);
            workerProperties.setPort(port);
            workerProperties.setKey(key);
            this.serverInfoProperties = workerProperties;
            this.commonProperties = commonProperties;
        }

        public Builder sql(String sql) {

            starRequest.setSql(sql);
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

        public StarResponse requestStarServer(String url, StarRequest starRequest) {

            Map<String, String> headers = new HashMap<>();
            headers.put("star-key", serverInfoProperties.getKey());

            try {
                return HttpUtils.doPost(url, headers, starRequest, StarResponse.class);
            } catch (IOException e) {
                return new StarResponse("500", e.getMessage());
            }
        }

        public StarResponse execute() {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse query() {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_QUERY_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse executePageQuery(StarRequest starRequest) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_PAGE_QUERY_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse executeMultiSql(StarRequest starRequest) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.EXECUTE_MULTI_SQL_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse getLog(StarRequest starRequest) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.GET_JOB_LOG_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse stopJob(StarRequest starRequest) {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.STOP_JOB_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse queryDBs() {

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.QUERY_DBS_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }

        public StarResponse quickExecuteQuery(StarRequest starRequest) {

            if (starRequest.getLimit() == null) {
                starRequest.setLimit(100);
            }

            String executeUrl = String.format(UrlConstants.BASE_URL + UrlConstants.QUICK_EXECUTE_QUERY_URL, serverInfoProperties.getHost(), serverInfoProperties.getPort());
            return requestStarServer(executeUrl, starRequest);
        }
    }

}
