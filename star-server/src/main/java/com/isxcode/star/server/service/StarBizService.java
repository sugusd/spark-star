package com.isxcode.star.server.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.exception.StarException;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    public StarData execute(StarRequest starRequest) throws IOException {

        // 获取star目录位置
        String starHomePath = System.getenv("STAR_HOME");
        if (starHomePath == null) {
            throw new StarException("50010", "请配置STAR_HOME环境变量");
        }

        // 封装launcher
        SparkLauncher sparkLauncher = new SparkLauncher()
            .setMaster("yarn")
            .setDeployMode("cluster")
            .setAppName(starRequest.getYarnJobConfig().getAppName())
            .setVerbose(true)
            .setMainClass(starRequest.getYarnJobConfig().getMainClass())
            .setAppResource(starHomePath + "/plugins/" + starRequest.getYarnJobConfig().getAppResourceName() + ".jar")
            .addAppArgs(JSON.toJSONString(starRequest));

        // 添加依赖包
        File[] jars = new File(starHomePath + "/lib/").listFiles();
        if (jars != null) {
            for (File jar : jars) {
                sparkLauncher.addJar(jar.toURI().toURL().toString());
            }
        }

        // 提交作业到yarn
        SparkAppHandle sparkAppHandle = sparkLauncher.startApplication();

        // 返回结果
        return StarData.builder().applicationId(sparkAppHandle.getAppId()).build();
    }

}
