package com.isxcode.star.server.controller;

import com.isxcode.star.api.constant.URLs;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.common.response.SuccessResponse;
import com.isxcode.star.server.service.StarBizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class StarController {

    private final StarBizService starBizService;

    @SuccessResponse
    @PostMapping(URLs.EXECUTE_URL)
    public StarData execute(@RequestBody StarRequest starRequest) {

        return starBizService.execute(starRequest);
    }

    @SuccessResponse
    @PostMapping(URLs.GET_STATUS_URL)
    public StarData getStatus(@RequestBody StarRequest starRequest) {

        return starBizService.getStatus(starRequest);
    }

    @SuccessResponse
    @PostMapping(URLs.GET_LOG_URL)
    public StarData getLog(@RequestBody StarRequest starRequest) {

        return starBizService.getLog(starRequest);
    }

    @SuccessResponse
    @PostMapping(URLs.GET_DATA_URL)
    public StarData getData(@RequestBody StarRequest starRequest) {

        return starBizService.getData(starRequest);
    }

    @SuccessResponse
    @PostMapping(URLs.STOP_JOB_URL)
    public StarData stopJob(@RequestBody StarRequest starRequest) {

        return starBizService.stopJob(starRequest);
    }

    @SuccessResponse
    @GetMapping(URLs.HEART_CHECK_URL)
    public StarData heartCheck() {

        return StarData.builder().log("正常").build();
    }
}
