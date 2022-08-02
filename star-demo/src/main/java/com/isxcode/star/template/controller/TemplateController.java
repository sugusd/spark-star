package com.isxcode.star.template.controller;

import com.isxcode.star.common.response.StarResponse;
import com.isxcode.star.common.template.StarTemplate;
import com.isxcode.star.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    private final StarTemplate starTemplate;

    @PostMapping("/queryDatabases")
    public void queryDatabases() {

        StarResponse starResponse = starTemplate.build().queryDBs();

        log.debug("starResponse {}", starResponse);
    }

    @GetMapping("/executePageQuery")
    public String executePageQuery() {

        templateService.executePageQuery();

        return "运行成功等待结果";
    }

    @GetMapping("/executeMultiSql")
    public String executeMultiSql() {

        templateService.executeMultiSql();

        return "运行成功等待结果";
    }

//    @GetMapping("/getLog")
//    public String getLog(@RequestParam String appId) {
//
//        StarRequest starRequest = StarRequest.builder()
//            .appId(appId)
//            .build();
//
//        StarResponse starResponse = starTemplate.build().getLog(starRequest);
//        return starResponse.toString();
//    }
//
//    @GetMapping("/stopJob")
//    public String stopJob(@RequestParam String appId) {
//
//        StarRequest starRequest = StarRequest.builder()
//            .appId(appId)
//            .build();
//
//        StarResponse starResponse = starTemplate.build().stopJob(starRequest);
//        return starResponse.toString();
//    }

}
