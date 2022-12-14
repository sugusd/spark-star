package com.isxcode.star.example.controller;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final StarTemplate starTemplate;

    @GetMapping("/execute")
    public StarResponse execute(@RequestParam String sql) {

        return starTemplate.build().sql(sql).execute();
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
}
