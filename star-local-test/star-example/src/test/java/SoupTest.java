import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SoupTest {

    @Test
    public void parseHtml() throws IOException {

        // 通过url获取html的内容
        Document doc = Jsoup.connect("http://definesys:8042/node/containerlogs/container_1670493775656_0021_01_000001/dehoop").get();
        Elements el = doc.getElementsByClass("content");

        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : el.get(0).children()) {

            String elementText = element.text();
            if (elementText.contains("Log Type:")) {
                stringBuilder.append(elementText.split("Log Type:")[1]);
                System.out.println(stringBuilder);
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(element.text());
        }

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
