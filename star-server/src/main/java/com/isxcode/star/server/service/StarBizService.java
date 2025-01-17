package com.isxcode.star.server.service;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.exception.StarException;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import com.isxcode.star.server.function.LookupFunc;
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
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.apache.spark.sql.functions.sha1;
import static org.apache.spark.sql.functions.udf;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    private final SparkSession sparkSession;

    public void executeSessionSql() {

        sparkSession.udf().register("demo_func", new LookupFunc(), DataTypes.StringType);

        Dataset<Row> jdbcDF = sparkSession.read()
            .format("jdbc")
            .option(" driver", " com.mysql.cj.jdbc.Driver")
            .option("url", "jdbc:mysql://ispong-mac.local:3306")
            .option("dbtable", "ispong_db.users")
            .option("user", "root")
            .option("password", "ispong123")
            .load();

        jdbcDF.createOrReplaceTempView("users");

        sparkSession.sql("select demo_func('vlookup','F') from users").show();
    }

    public StarData execute(StarRequest starRequest) {


        String hadoopConfDir = System.getenv("HADOOP_HOME");
        Configuration yarnConf1 = new Configuration(false);
        Path path = Paths.get(hadoopConfDir + File.separator + "etc" + File.separator + "hadoop" + File.separator + "yarn-site.xml");
        try {
            yarnConf1.addResource(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        YarnConfiguration yarnConf = new YarnConfiguration(yarnConf1);


        if (starRequest.getSparkConfig() == null) {
            Map<String, String> sparkConfig = new HashMap<>();
            sparkConfig.put("spark.executor.memory", "1g");
            sparkConfig.put("spark.driver.memory", "1g");
            starRequest.setSparkConfig(sparkConfig);
        }

        if (starRequest.getYarnJobConfig() == null) {
            YarnJobConfig yarnJobConfig = new YarnJobConfig("spark-star", "com.isxcode.star.Execute", "star-sql-plugin");
            starRequest.setYarnJobConfig(yarnJobConfig);
        }

        // 获取star目录位置
        String starHomePath = Strings.isEmpty(starRequest.getStarHome()) ? System.getenv("STAR_HOME") : starRequest.getStarHome();

        log.info("request: {}", Base64.getEncoder().encodeToString(JSON.toJSONString(starRequest).getBytes()));

        Map<String, String> env = new HashMap<>();
        env.put("HADOOP_CONF_DIR", "/opt/homebrew/Cellar/hadoop/3.3.4/libexec/etc/hadoop");
        env.put("YARN_CONF_DIR", "/opt/homebrew/Cellar/hadoop/3.3.4/libexec/etc/hadoop");

        // 封装launcher
        SparkLauncher sparkLauncher = new SparkLauncher(env)
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
//                Optional<Throwable> error = sparkAppHandle.getError();
                starDataBuilder.appState("FAILED");
                starDataBuilder.error("部署失败,请查看yarn的日志");
//                starDataBuilder.error(error.get().getMessage());
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
            applicationReport = yarnClient.getApplicationReport(formatApplicationId(starRequest.getApplicationId()));
        } catch (YarnException | IOException e) {
            throw new RuntimeException(e);
        }

        return StarData.builder().appState(String.valueOf(applicationReport.getYarnApplicationState())).appFinalStatus(String.valueOf(applicationReport.getFinalApplicationStatus())).build();
    }

    public StarData getLog(StarRequest starRequest) {

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
            yarnClient.killApplication(formatApplicationId(starRequest.getApplicationId()));
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

    public static ApplicationId formatApplicationId(String appIdStr) {

        String applicationPrefix = "application_";
        try {
            int pos1 = applicationPrefix.length() - 1;
            int pos2 = appIdStr.indexOf('_', pos1 + 1);
            long rmId = Long.parseLong(appIdStr.substring(pos1 + 1, pos2));
            int appId = Integer.parseInt(appIdStr.substring(pos2 + 1));
            return ApplicationId.newInstance(rmId, appId);
        } catch (NumberFormatException n) {
            throw new StarException("50010", "ApplicationId异常");
        }
    }
}
