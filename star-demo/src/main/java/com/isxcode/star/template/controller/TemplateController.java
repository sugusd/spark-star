package com.isxcode.star.template.controller;

import com.isxcode.star.common.response.StarResponse;
import com.isxcode.star.common.template.StarTemplate;
import com.isxcode.star.template.pojo.TemplateReq;
import com.isxcode.star.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    private final StarTemplate starTemplate;

    @GetMapping("/queryDatabases")
    public void queryDatabases() {

        StarResponse starResponse = starTemplate.build().queryDBs();

        log.debug("starResponse {}", starResponse.toString());
    }

    @PostMapping("/executeSql")
    public void executeSql(@RequestBody TemplateReq templateReq) {

        StarResponse starResponse = starTemplate.build().sql(templateReq.getSql()).execute();
        log.debug("starResponse {}", starResponse.toString());
    }

    @PostMapping("/executeQuery")
    public void executeQuery(@RequestBody TemplateReq templateReq) {

        StarResponse starResponse = starTemplate.build().db(templateReq.getDatabase()).sql(templateReq.getSql()).limit(templateReq.getLimit()).query();
        log.debug("starResponse {}", starResponse.toString());
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
