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
    @GetMapping(UrlConstants.HEART_CHECK_URL)
    public StarData heartCheck() {

        return StarData.builder().log("正常").build();
    }
}
