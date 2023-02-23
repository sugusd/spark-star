package com.isxcode.star.server.controller;

import com.isxcode.star.api.constant.URLs;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.common.response.SuccessResponse;
import com.isxcode.star.server.service.StarBizService;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.AnalysisException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StarController {

    private final StarBizService starBizService;

    @SuccessResponse("提交成功")
    @PostMapping(URLs.EXECUTE_URL)
    public StarData execute(@RequestBody StarRequest starRequest) {

        return starBizService.execute(starRequest);
    }

    @SuccessResponse("获取任务状态成功")
    @PostMapping(URLs.GET_STATUS_URL)
    public StarData getStatus(@RequestBody StarRequest starRequest) {

        return starBizService.getStatus(starRequest);
    }

    @SuccessResponse("获取作业日志成功")
    @PostMapping(URLs.GET_LOG_URL)
    public StarData getLog(@RequestBody StarRequest starRequest) {

        return starBizService.getLog(starRequest);
    }

    @SuccessResponse("获取数据成功")
    @PostMapping(URLs.GET_DATA_URL)
    public StarData getData(@RequestBody StarRequest starRequest) {

        return starBizService.getData(starRequest);
    }

    @SuccessResponse("停止作业成功")
    @PostMapping(URLs.STOP_JOB_URL)
    public StarData stopJob(@RequestBody StarRequest starRequest) {

        return starBizService.stopJob(starRequest);
    }

    @SuccessResponse
    @GetMapping(URLs.HEART_CHECK_URL)
    public StarData heartCheck() {

        return StarData.builder().build();
    }

    @SuccessResponse
    @PostMapping("/executeSessionSql")
    public void executeSessionSql(@RequestBody StarRequest starRequest) throws AnalysisException {

        starBizService.executeSessionSql(starRequest);
    }
}
