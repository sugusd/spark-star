import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoupTest {

    @Test
    public void parseHtml() throws IOException {

        Document doc = Jsoup.connect("http://definesys:8042/node/containerlogs/container_1670493775656_0021_01_000001/dehoop").get();

        Elements contentEls = doc.getElementsByClass("content");
        if (contentEls.isEmpty()) {
            throw new RuntimeException("数据解析异常");
        }

        Element el = contentEls.get(0);

        Map<String, String> resultLog = new HashMap<>();
        Elements preElements = el.getElementsByTag("pre");

        for (Element element : preElements) {

            String elementText = element.text();
            if (elementText.isEmpty()) {
                continue;
            }

            StringBuilder logBuilder = new StringBuilder();
            Element thirdElement = element.previousElementSibling();
            String logUrl = thirdElement.select("a[href]").attr("href");
            if (!logUrl.isEmpty()) {
                logBuilder.append(Jsoup.connect("http://definesys:19888" + logUrl).get().body().text());
                thirdElement = thirdElement.previousElementSibling();
            }else{
                logBuilder.append(elementText);
            }
            Element firstElement = thirdElement.previousElementSibling().previousElementSibling();

            resultLog.put(firstElement.text().replace("Log Type:", ""), logBuilder.toString());
        }

        System.out.println(resultLog);

        // return  和

        // http://definesys:19888/jobhistory/logs/localhost:36442/container_1670493775656_0021_01_000001/container_1670493775656_0021_01_000001/dehoop

//
//        // 获取jobManager日志内容url
//        Elements afs = el.get(0).select("a[href]");
//        String jobManagerLogUrl = afs.attr("href");
//        String jobHistoryAddress = hadoopConf.get("mapreduce.jobhistory.webapp.address");
//
//        // 使用Jsoup爬取jobManager的日志
//        Document managerDoc = Jsoup.connect("http://" + jobHistoryAddress + jobManagerLogUrl).get();
//
//        System.out.println("====================yarn jobManager Log=================>");
//        System.out.println(managerDoc.body().text());
//        System.out.println("============================================>");
    }
}
