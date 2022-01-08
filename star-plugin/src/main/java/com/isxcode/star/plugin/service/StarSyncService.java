package com.isxcode.star.plugin.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.common.constant.KafkaConfigConstants;
import com.isxcode.star.common.exception.StarExceptionEnum;
import com.isxcode.star.common.properties.StarPluginProperties;
import com.isxcode.star.common.response.StarRequest;
import com.isxcode.star.common.response.StarResponse;
import com.isxcode.star.plugin.exception.StarException;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 所有的异步服务
 */
@Slf4j
@Service
public class StarSyncService {

    private final SparkSession sparkSession;

    private final StarPluginProperties starPluginProperties;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public StarSyncService(SparkSession sparkSession,
                           StarPluginProperties starPluginProperties,
                           KafkaTemplate<String, String> kafkaTemplate) {

        this.sparkSession = sparkSession;
        this.starPluginProperties = starPluginProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public void executeSqlByKafka(StarRequest starRequest) {

        if (starRequest.getExecuteId() == null) {
            throw new StarException(StarExceptionEnum.REQUEST_VALUE_EMPTY);
        }

        log.debug("开始执行sparkSql");

        sparkSession.sql(starRequest.getSql());

        log.debug("将结果推送kafka");

        StarResponse starResponse = StarResponse.builder().build();
        kafkaTemplate.send(starPluginProperties.getKafkaConfig().get(KafkaConfigConstants.TOPIC_NAME), starRequest.getExecuteId(), JSON.toJSONString(starResponse));
    }

}
