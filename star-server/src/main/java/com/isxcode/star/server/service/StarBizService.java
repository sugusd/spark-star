package com.isxcode.star.server.service;

import com.isxcode.star.api.constant.MsgConstants;
import com.isxcode.star.api.exception.StarException;
import com.isxcode.star.api.exception.StarExceptionEnum;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.StarData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    public void execute(StarRequest starRequest) throws IOException {

        Map<String, String> env = new HashMap<>();
        env.put("YARN_CONF_DIR", System.getenv("YARN_CONF_DIR"));

        SparkLauncher sparkLauncher = new SparkLauncher(env)
            .setSparkHome(System.getenv("SPARK_HOME"))
            .setMaster("yarn-client")
            .setDeployMode("cluster")
            .setAppName("star-test " + starRequest.getExecuteId())
            .setVerbose(true)
            .setMainClass("com.isxcode.star.Execute")
            .setAppResource("/opt/star/plugins/star-sql-plugin.jar")
//            .addJar("/home/cdh/spark-star/star/lib/star-common.jar")
            .addAppArgs("hello");

        try {
            sparkLauncher.startApplication(new SparkAppHandle.Listener() {

                @Override
                public void stateChanged(SparkAppHandle sparkAppHandle) {
                    StarData starData = StarData.builder().appId(sparkAppHandle.getAppId()).appState(sparkAppHandle.getState().toString()).build();
                    StarResponse starResponse = new StarResponse(MsgConstants.SUCCESS_CODE, MsgConstants.SUCCESS_RESPONSE_MSG, starData);
                    System.out.println(starResponse);
                }

                @Override
                public void infoChanged(SparkAppHandle sparkAppHandle) {
                    System.out.println(sparkAppHandle.getState().toString());
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StarException(StarExceptionEnum.SPARK_LAUNCHER_ERROR);
        }
    }

}
