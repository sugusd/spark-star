package com.isxcode.star.plugin.service;

import com.isxcode.star.common.exception.StarExceptionEnum;
import com.isxcode.star.common.pojo.dto.StarData;
import com.isxcode.star.common.response.StarRequest;
import com.isxcode.star.common.utils.CommandUtils;
import com.isxcode.star.plugin.constant.SqlConstants;
import com.isxcode.star.plugin.exception.StarException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StarBizService {

    private final StarSyncService starSyncService;

    private final StarService starService;

    private final SparkSession sparkSession;

    public StarData executeSql(StarRequest starRequest) {

        if (!Strings.isEmpty(starRequest.getDb())) {
            sparkSession.sql(SqlConstants.USE_SQL + starRequest.getDb());
            System.out.println("database: " + starRequest.getDb());
        }

        System.out.println("sql: " + starRequest.getSql());
        sparkSession.sql(starRequest.getSql());
        return StarData.builder().log("运行成功").build();
    }

    public StarData executeQuerySql(StarRequest starRequest) {

        System.out.println("starRequest: " + starRequest.toString());

        if (!Strings.isEmpty(starRequest.getDb())) {
            sparkSession.sql(SqlConstants.USE_SQL + starRequest.getDb());
        }

        Dataset<Row> rowDataset = sparkSession.sql(starRequest.getSql()).limit(starRequest.getLimit());
        System.out.println("rowDataset: 获取dataset");

        // 初始化返回对象
        StarData.StarDataBuilder starDataBuilder = StarData.builder();

        // 获取字段列名
        String[] columns = rowDataset.columns();
        System.out.println("columns: " + Arrays.toString(columns));
        starDataBuilder.columnNames(Arrays.asList(columns));

        // 获取数据值
        List<List<String>> dataList = new ArrayList<>();
        List<Row> rows = rowDataset.collectAsList();
        rows.forEach(e -> {
            List<String> metaData = new ArrayList<>();
            for (int i = 0; i < e.size(); i++) {
                metaData.add(String.valueOf(e.get(i)));
            }
            dataList.add(metaData);
        });
        System.out.println("dataList: " + dataList);

        // 返回结果
        return starDataBuilder.dataList(dataList).build();
    }

    public StarData executeSyncWork(StarRequest starRequest, String url) {

        starSyncService.executeSyncWork(starRequest, url);

        return StarData.builder().build();
    }

    public StarData getJobLog(StarRequest starRequest) {

        if (starRequest.getAppId() == null) {
            throw new StarException(StarExceptionEnum.REQUEST_VALUE_EMPTY);
        }

        String getLogCommand = "yarn logs -applicationId " + starRequest.getAppId();
        try {
            String log = CommandUtils.executeBackCommand(getLogCommand);
            return StarData.builder().log(log).build();
        } catch (IOException e) {
            throw new StarException(StarExceptionEnum.COMMAND_EXECUTE_ERROR);
        }
    }

    public StarData stopJob(StarRequest starRequest) {

        if (starRequest.getAppId() == null) {
            throw new StarException(StarExceptionEnum.REQUEST_VALUE_EMPTY);
        }

        String killJobCommand = "yarn application -kill " + starRequest.getAppId();
        try {
            CommandUtils.executeNoBackCommand(killJobCommand);
            return StarData.builder().build();
        } catch (IOException e) {
            throw new StarException(StarExceptionEnum.COMMAND_EXECUTE_ERROR);
        }
    }

    public StarData quickExecuteQuerySql(StarRequest starRequest) {

        return starService.queryData(starRequest);
    }

    public StarData queryDbs() {

        List<String> databases = new ArrayList<>();
        String QUERY_DBS_SQL = SqlConstants.QUERY_DBS_SQL;
        Dataset<Row> databasesRow = sparkSession.sql(QUERY_DBS_SQL);

        List<Row> rows = databasesRow.collectAsList();
        rows.forEach(e -> databases.add(e.getString(0)));

        return StarData.builder().databases(databases).build();
    }
}
