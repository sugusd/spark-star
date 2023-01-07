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
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    public StarData execute(StarRequest starRequest) {

        // 获取star目录位置
        String starHomePath = System.getenv("STAR_HOME");

        log.info("request: {}", Base64.getEncoder().encodeToString(JSON.toJSONString(starRequest).getBytes()));

        // 封装launcher
        SparkLauncher sparkLauncher = new SparkLauncher()
            .setMaster("yarn")
            .setDeployMode("cluster")
            .setAppName(starRequest.getYarnJobConfig().getAppName())
            .setVerbose(true)
            .setMainClass(starRequest.getYarnJobConfig().getMainClass())
            .setAppResource(starHomePath + File.separator + "plugins" + File.separator + starRequest.getYarnJobConfig().getAppResourceName() + ".jar")
            .addAppArgs(Base64.getEncoder().encodeToString(JSON.toJSONString(starRequest).getBytes()));

        // 添加依赖包
        File[] jars = new File(starHomePath + File.separator + "lib" + File.separator).listFiles();
        if (jars != null) {
            for (File jar : jars) {
                try {
                    sparkLauncher.addJar(jar.toURI().toURL().toString());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 提交作业到yarn
        SparkAppHandle sparkAppHandle;
        try {
            sparkAppHandle = sparkLauncher.startApplication(new SparkAppHandle.Listener() {

                @Override
                public void stateChanged(SparkAppHandle sparkAppHandle) {
                    // do nothing
                }

                @Override
                public void infoChanged(SparkAppHandle sparkAppHandle) {
                    // do nothing
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 返回结果
        StarData.StarDataBuilder starDataBuilder = StarData.builder();

        // 默认10秒超时
        long timeoutExpiredMs = System.currentTimeMillis() + 10000;
        String applicationId;
        while (!SparkAppHandle.State.RUNNING.equals(sparkAppHandle.getState())) {

            long waitMillis = timeoutExpiredMs - System.currentTimeMillis();
            if (waitMillis <= 0) {
                starDataBuilder.appState("TIMEOUT");
                break;
            }

            if (SparkAppHandle.State.FAILED.equals(sparkAppHandle.getState())) {
                Optional<Throwable> error = sparkAppHandle.getError();
                starDataBuilder.appState("FAILED");
                starDataBuilder.error(error.get().getMessage());
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

    public StarData getStatus(StarRequest starRequest) {

        YarnClient yarnClient = initYarnClient();

        ApplicationReport applicationReport;
        try {
            applicationReport = yarnClient.getApplicationReport(ApplicationId.fromString(starRequest.getApplicationId()));
        } catch (YarnException | IOException e) {
            throw new RuntimeException(e);
        }

        return StarData.builder().appState(String.valueOf(applicationReport.getYarnApplicationState())).appFinalStatus(String.valueOf(applicationReport.getFinalApplicationStatus())).build();
    }

    public StarData getLog(StarRequest starRequest)  {

        Map<String, String> map = LogUtils.parseYarnLog(starRequest.getApplicationId());
        String stdErrLog = map.get("stderr");

        if (Strings.isEmpty(stdErrLog)) {
            throw new StarException("50013", "作业日志暂未生成");
        }

        return StarData.builder().logList(Arrays.asList(stdErrLog.split("\n"))).build();
    }

    public StarData getData(StarRequest starRequest) {

        Map<String, String> map = LogUtils.parseYarnLog(starRequest.getApplicationId());
        String stdoutLog = map.get("stdout");

        if (Strings.isEmpty(stdoutLog)) {
            return StarData.builder().build();
        }

        return JSON.parseObject(stdoutLog, StarData.class);
    }

    public StarData stopJob(StarRequest starRequest) {

        YarnClient yarnClient = initYarnClient();
        try {
            yarnClient.killApplication(ApplicationId.fromString(starRequest.getApplicationId()));
        } catch (YarnException | IOException e) {
            throw new RuntimeException(e);
        }

        return StarData.builder().build();
    }

    public YarnClient initYarnClient() {

        // 获取hadoop的配置文件目录
        String hadoopConfDir = System.getenv("HADOOP_HOME");

        // 读取配置yarn-site.yml文件
        Configuration yarnConf = new Configuration(false);
        Path path = Paths.get(hadoopConfDir + File.separator + "etc" + File.separator + "hadoop" + File.separator + "yarn-site.xml");
        try {
            yarnConf.addResource(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        YarnConfiguration yarnConfig = new YarnConfiguration(yarnConf);

        // 获取yarn客户端
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        return yarnClient;
    }

}
