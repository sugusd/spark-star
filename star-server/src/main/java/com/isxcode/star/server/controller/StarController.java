package com.isxcode.star.server.controller;

import com.isxcode.oxygen.common.response.SuccessResponse;
import com.isxcode.star.api.constant.UrlConstants;
import com.isxcode.star.api.pojo.StarRequest;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.server.service.StarBizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class StarController {

    private final StarBizService starBizService;

    @SuccessResponse
    @PostMapping(UrlConstants.EXECUTE_URL)
    public StarData executeSql(@RequestBody StarRequest starRequest) throws IOException {

        return starBizService.execute(starRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_WORK_STATUS_URL)
    public StarData getWorkStatus(@RequestBody StarRequest starRequest) throws IOException {

        return starBizService.getWorkStatus(starRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_WORK_LOG_URL)
    public StarData getWorkLog(@RequestBody StarRequest starRequest) throws IOException {

        return starBizService.execute(starRequest);
    }

    @SuccessResponse
    @PostMapping(UrlConstants.GET_WORK_DATA_URL)
    public StarData getWorkData(@RequestBody StarRequest starRequest) throws IOException {

        return starBizService.execute(starRequest);
    }

    @SuccessResponse
    @GetMapping(UrlConstants.HEART_CHECK_URL)
    public StarData heartCheck() {

        return StarData.builder().log("正常").build();
    }
}
