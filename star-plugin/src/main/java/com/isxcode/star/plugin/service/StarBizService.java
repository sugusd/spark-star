package com.isxcode.star.plugin.service;

import com.isxcode.star.common.exception.StarExceptionEnum;
import com.isxcode.star.common.pojo.dto.StarData;
import com.isxcode.star.common.response.StarRequest;
import com.isxcode.star.common.utils.CommandUtils;
import com.isxcode.star.plugin.constant.SqlConstants;
import com.isxcode.star.plugin.exception.StarException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarBizService {

    private final StarSyncService starSyncService;

    private final StarService starService;

    private final SparkSession sparkSession;

    public StarData executeSql(StarRequest starRequest) {

        if (!Strings.isEmpty(starRequest.getDb())) {
            sparkSession.sql("use " + starRequest.getDb());
            log.debug("database: {} ", starRequest.getDb());
        }

        log.debug("sql: {}", starRequest.getSql());
        sparkSession.sql(starRequest.getSql());
        return StarData.builder().log("运行成功").build();
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
            log.debug(e.getMessage());
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
            log.debug(e.getMessage());
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
