package com.isxcode.star.server.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LogUtils {

    public static Map<String, String> parseYarnLog(String applicationId) {

        String yarnConfDirConfDir = System.getenv("YARN_CONF_DIR");

        Configuration yarnConf = new Configuration(false);
        Path path = Paths.get(yarnConfDirConfDir + File.separator + "yarn-site.xml");
        try {
            yarnConf.addResource(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("未找到yarn配置文件");
        }
        YarnConfiguration yarnConfig = new YarnConfiguration(yarnConf);

        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfig);
        yarnClient.start();

        String managerAddress = yarnConf.get("yarn.resourcemanager.webapp.address");

        Map appInfoMap = new RestTemplate().getForObject("http://" + managerAddress + "/ws/v1/cluster/apps/" + applicationId, Map.class);
        Map<String, Map<String, Object>> appMap = (Map<String, Map<String, Object>>) appInfoMap.get("app");
        String amContainerLogsUrl = String.valueOf(appMap.get("amContainerLogs"));

        Document doc = Jsoup.connect(amContainerLogsUrl).get();

        Elements contentEls = doc.getElementsByClass("content");
        if (contentEls.isEmpty()) {
            throw new RuntimeException("数据解析异常");
        }

        Element el = contentEls.get(0);

        Map<String, String> resultLog = new HashMap<>();
        Elements preElements = el.getElementsByTag("pre");

        String jobHistoryAddress = yarnConfig.get("mapreduce.jobhistory.webapp.address");

        for (Element element : preElements) {

            String elementText = element.text();
            if (elementText.isEmpty()) {
                continue;
            }

            StringBuilder logBuilder = new StringBuilder();
            Element thirdElement = element.previousElementSibling();
            String logUrl = thirdElement.select("a[href]").attr("href");
            if (!logUrl.isEmpty()) {
                logBuilder.append(Jsoup.connect(jobHistoryAddress + logUrl).get().body().text());
                thirdElement = thirdElement.previousElementSibling();
            } else {
                logBuilder.append(elementText);
            }
            Element firstElement = thirdElement.previousElementSibling().previousElementSibling();

            resultLog.put(firstElement.text().replace("Log Type:", ""), logBuilder.toString());
        }

        return resultLog;
    }

}
