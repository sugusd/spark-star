package com.isxcode.star.example.controller;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.YarnJobConfig;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final StarTemplate starTemplate;

    // 内存 10核50GB
    // 最小 1核1GB
    // 最大 2核5GB
    @GetMapping("/executeHive")
    public StarResponse executeHive() {

        // 20核40GB
        Map<String, String> sparkConfig = new HashMap<>();

        // 配置spark可使用的资源  1个容器一个核  * 最大的容器内存
        sparkConfig.put("spark.yarn.am.memoryr", "50g"); // 默认512m
        sparkConfig.put("spark.yarn.am.cores", "10"); // 默认1

        // driver 负责加载数据，需要大并发，可以不用配置 配置无效
        // sparkConfig.put("spark.driver.memory","4g");
        // driver的个数
        //sparkConfig.put("spark.driver.cores","20");

        // 单个执行器的配置 处理数据，处理数据10个并发进程，由于spark计算需要比实际要大，所以要比容器最大值，低一点点，必须是整数
        // 这个值需要调大  （4g<5g 需要小于yarn的容器）
        sparkConfig.put("spark.executor.memory", "4g");
        // 每个执行器执行2个task (需要小于yarn的容器最大核数)
        sparkConfig.put("spark.executor.cores", "2");
        // 执行器的个数 （2*9=18个执行器）需要分配 9+1=10 yarn开启10个容器
        sparkConfig.put("spark.executor.instances", "9");

        sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

        String sql = "insert overwrite table dws_dfg_logsheet_report\n" +
            "select \n" +
            "    mw.block_id,\n" +
            "    mw.tran_time,\n" +
            "    mw.res_id,\n" +
            "    mw.mat_id,\n" +
            "    ef.recipe_id,\n" +
            "    me.lot_id,\n" +
            "    mw.in_mat_num,\n" +
            "    mw.out_mat_num,\n" +
            "    tqs.wafer_id,\n" +
            "    tqs.center_thickness,\n" +
            "    tqs.ttv_gbir,\n" +
            "    me.mf1_wlm,\n" +
            "    me.mf1_blm,\n" +
            "    me.mf1_bsd,\n" +
            "    me.mf2_wpc,\n" +
            "    me.mf2_bpc,\n" +
            "    me.mf2_bsd,\n" +
            "    os.bad_type,\n" +
            "    os.loss_qty,\n" +
            "    me.usable_tooth,\n" +
            "    me.wheel_well_ratio,\n" +
            "    me.tatal_grainding_time,\n" +
            "    me.warm_up_time_current,\n" +
            "    me.z_spindle_current,\n" +
            "    me.spindle_rpm,\n" +
            "    me.cooling_water,\n" +
            "    me.cutting_water,\n" +
            "    me.safty_cutting_water,\n" +
            "    me.ipg_cooling_water,\n" +
            "    me.usable_tooth_2,\n" +
            "    me.wheel_well_ratio_2,\n" +
            "    me.tatal_grainding_time_2,\n" +
            "    me.warm_up_time_current_2,\n" +
            "    me.z_spindle_current_2,\n" +
            "    me.spindle_rpm_2,\n" +
            "    me.cooling_water_2,\n" +
            "    me.cutting_water_2,\n" +
            "    me.safty_cutting_water_2,\n" +
            "    me.ipg_cooling_water_2,\n" +
            "    mw.remark\n" +
            "from dwd_mwiplothis mw\n" +
            "left join dwd_MRCPMFODEF ef on ef.mat_id = mw.mat_id and ef.flow = mw.flow and ef.oper = mw.oper\n" +
            "left join  dwd_MWIPLOTLOS os on mw.lot_id = os.lot_id \n" +
            "left join dwd_TQS_SUMMARY_DATA tqs on mw.lot_id = tqs.wafer_id\n" +
            "left join dwd_MEDCRESDAT me on tqs.wafer_id = me.unit_id";

        return starTemplate.build().sparkConfig(sparkConfig).sql(sql).execute();
    }

    @GetMapping("/executeMysql")
    public StarResponse executeMysql(@RequestParam String sql) {

        return starTemplate.build()
            .jdbcUrl("jdbc:mysql://dcloud-dev:30102/ispong_db")
            .username("ispong")
            .password("ispong123")
            .sql(sql)
            .execute();
    }


    @GetMapping("/executeKafka")
    public StarResponse executeKafka() {

        Map<String, Object> kafkaConfig = new HashMap<>();
        kafkaConfig.put("topic", "ispong-topic");
        kafkaConfig.put("name", "users_tmp");
        kafkaConfig.put("columns", "username,age,index");
        kafkaConfig.put("data-type", "csv");
        kafkaConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "dcloud-dev:30120");
        kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "none");
        kafkaConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return starTemplate.build()
            .sql("insert into ispong_db.users select username, 1 from users_tmp")
            .kafkaConfig(kafkaConfig)
            .execute();
    }

    @GetMapping("/executeSync")
    public StarResponse executeSync() {

        // 20核40GB
        Map<String, String> sparkConfig = new HashMap<>();

        // 配置spark可使用的资源  1个容器一个核  * 最大的容器内存
        sparkConfig.put("spark.yarn.am.memory", "50g"); // 默认512m
        sparkConfig.put("spark.yarn.am.cores", "10"); // 默认1

        // driver 负责加载数据，需要大并发，可以不用配置 配置无效
        // sparkConfig.put("spark.driver.memory","4g");
        // driver的个数
        //sparkConfig.put("spark.driver.cores","20");

        // 单个执行器的配置 处理数据，处理数据10个并发进程，由于spark计算需要比实际要大，所以要比容器最大值，低一点点，必须是整数
        // 这个值需要调大  （4g<5g 需要小于yarn的容器）
        sparkConfig.put("spark.executor.memory", "4g");
        // 每个执行器执行2个task (需要小于yarn的容器最大核数)
        sparkConfig.put("spark.executor.cores", "2");
        // 执行器的个数 （2*9=18个执行器）需要分配 9+1=10 yarn开启10个容器
        sparkConfig.put("spark.executor.instances", "1");

        sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

//        String sql = "" +
//            "CREATE TEMPORARY VIEW users_view\n" +
//            "USING org.apache.spark.sql.jdbc\n" +
//            "OPTIONS (\n" +
//            "    driver 'com.mysql.cj.jdbc.Driver',\n" +
//            "    url 'jdbc:mysql://47.92.168.116:30102/fanruan_report',\n" +
//            "    user 'fanruan',\n" +
//            "    password 'fanruan2023',\n" +
//            "    dbtable 'ads_dfg_logsheet_report'\n" +
//            ");\n" +
//            "" +
//            "insert into users_view select * from dws_dfg_logsheet_report";

        String sql = "" +
            "CREATE TEMPORARY VIEW users_view\n" +
            "USING org.apache.spark.sql.jdbc\n" +
            "OPTIONS (\n" +
            "    driver 'com.mysql.cj.jdbc.Driver',\n" +
            "    url 'jdbc:mysql://dcloud-dev:30102/ispong_db',\n" +
            "    user 'ispong',\n" +
            "    password 'ispong123',\n" +
            "    dbtable 'users'\n" +
            ");\n" +
            "" +
            "insert into users_view select * from dws_dfg_logsheet_report";

        return starTemplate.build().sparkConfig(sparkConfig).sql(sql).execute();
    }

    @GetMapping("/getStatus")
    public StarResponse getStatus(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getStatus();
    }

    @GetMapping("/getLog")
    public StarResponse getLog(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getLog();
    }

    @GetMapping("/stopJob")
    public StarResponse stopJob(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).stopJob();
    }

    @GetMapping("/getData")
    public StarResponse getData(@RequestParam String applicationId) {

        return starTemplate.build().applicationId(applicationId).getData();
    }

    @GetMapping("/custom")
    public StarResponse custom(@RequestParam String sql) {

        // 配置spark运行的环境和资源
        Map<String, String> sparkConfig = new HashMap<>();
        sparkConfig.put("spark.executor.memory", "2g");
        sparkConfig.put("spark.driver.memory", "1g");
        sparkConfig.put("hive.metastore.uris", "thrift://localhost:9083");

        // 配置yarn启动作业的参数，可以配置自定义插件
        YarnJobConfig yarnJobConfig = YarnJobConfig.builder()
            .appName("my app")
            .mainClass("com.isxcode.star.Execute")
            .appResourceName("star-sql-plugin")
            .build();

        return starTemplate.build()
            .sql(sql)
            .yarnJobConfig(yarnJobConfig)
            .sparkConfig(sparkConfig).execute();
    }

}
