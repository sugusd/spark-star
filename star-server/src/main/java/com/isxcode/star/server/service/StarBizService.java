package com.isxcode.star.server.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.exception.StarException;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.server.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    public StarData execute(StarRequest starRequest) throws IOException {

        log.info("starRequest: {}", starRequest);

        // 获取star目录位置
        String starHomePath = System.getenv("STAR_HOME");
        if (starHomePath == null) {
            throw new StarException("50010", "请配置STAR_HOME环境变量");
        }

        // 封装launcher1
        SparkLauncher sparkLauncher = new SparkLauncher()
            .setMaster("yarn")
            .setDeployMode("cluster")
            .setAppName(starRequest.getYarnJobConfig().getAppName())
            .setVerbose(true)
            .setMainClass(starRequest.getYarnJobConfig().getMainClass())
            .setAppResource(starHomePath + File.separator + "plugins" + File.separator + starRequest.getYarnJobConfig().getAppResourceName() + ".jar")
            .addAppArgs(JSON.toJSONString(starRequest));

        // 添加依赖包
        File[] jars = new File(starHomePath + File.separator + "lib" + File.separator).listFiles();
        if (jars != null) {
            for (File jar : jars) {
                sparkLauncher.addJar(jar.toURI().toURL().toString());
            }
        }

        // 提交作业到yarn
        SparkAppHandle sparkAppHandle = sparkLauncher.startApplication(new SparkAppHandle.Listener() {
            @Override
            public void stateChanged(SparkAppHandle sparkAppHandle) {
                log.info("stateChanged: {}", sparkAppHandle.getState());
                if ("RUNNING".equals(sparkAppHandle.getState().toString())) {
                    log.info("applicationId :{}", sparkAppHandle.getAppId());
                }
            }

            @Override
            public void infoChanged(SparkAppHandle sparkAppHandle) {
                log.info("infoChanged: {}", sparkAppHandle.getState());
            }
        });

        // 返回结果
        StarData.StarDataBuilder starDataBuilder = StarData.builder();

        // 默认10秒超时
        long timeoutExpiredMs = System.currentTimeMillis() + 10000;
        String applicationId = null;
        while (!SparkAppHandle.State.RUNNING.equals(sparkAppHandle.getState())) {
            long waitMillis = timeoutExpiredMs - System.currentTimeMillis();
            if (waitMillis <= 0) {
                starDataBuilder.appState("FAILED");
                break;
            }

            applicationId = sparkAppHandle.getAppId();
            if (applicationId != null) {
                starDataBuilder.applicationId(applicationId);
                break;
            }
        }

        return starDataBuilder.build();
    }

    public StarData getWorkStatus(StarRequest starRequest) throws IOException, YarnException {

        // 获取hadoop的配置文件目录
        String hadoopConfDir = System.getenv("HADOOP_CONF_DIR");

        // 读取配置yarn-site.yml文件
        Configuration hadoopConf = new Configuration(false);
        Path path = Paths.get(hadoopConfDir + File.separator + "yarn-site.xml");
        hadoopConf.addResource(Files.newInputStream(path));
        YarnConfiguration yarnConfig = new YarnConfiguration(hadoopConf);

        // 获取yarn客户端
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        ApplicationReport applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(starRequest.getApplicationId()));

        return StarData.builder().appState(applicationReport.getYarnApplicationState().toString()).build();
    }

    public StarData getWorkLog(StarRequest starRequest)  {


        Map<String, String> map = LogUtils.parseYarnLog(starRequest.getApplicationId());
        String stdErrLog = map.get("stderr");

        return StarData.builder().log(stdErrLog).build();
    }

    public StarData queryData(StarRequest starRequest)  {


        Map<String, String> map = LogUtils.parseYarnLog(starRequest.getApplicationId());
        String stdoutLog = map.get("stdout");

        return JSON.parseObject(stdoutLog, StarData.class);
    }

}
