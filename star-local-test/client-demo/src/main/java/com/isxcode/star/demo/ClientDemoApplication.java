package com.isxcode.star.demo;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.client.template.StarTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@SpringBootApplication
public class ClientDemoApplication {

    private final StarTemplate starTemplate;

    public static void main(String[] args) {

        SpringApplication.run(ClientDemoApplication.class, args);
    }

    @PostMapping("/executeSql")
    public StarResponse executeSql(@RequestBody StarReq starReq) {

        return starTemplate.build().sql(starReq.getSparkSql()).execute();
    }

    @PostMapping("/executeMysql")
    public StarResponse executeMysql(@RequestBody StarReq starReq) {

        return starTemplate.build()
            .jdbcUrl("jdbc:mysql://ispong-mac.local:3306/ispong_db")
            .username("root")
            .password("ispong123")
            .sql(starReq.getSparkSql()).execute();
    }

    @PostMapping("/getStatus")
    public StarResponse getStatus(@RequestBody StarReq starReq) {

        return starTemplate.build().applicationId(starReq.getApplicationId()).getStatus();
    }

    @PostMapping("/getLog")
    public StarResponse getLog(@RequestBody StarReq starReq) {

        return starTemplate.build().applicationId(starReq.getApplicationId()).getLog();
    }

    @PostMapping("/stopJob")
    public StarResponse stopJob(@RequestBody StarReq starReq) {

        return starTemplate.build().applicationId(starReq.getApplicationId()).stopJob();
    }

    @PostMapping("/getData")
    public StarResponse getData(@RequestBody StarReq starReq) {

        return starTemplate.build().applicationId(starReq.getApplicationId()).getData();
    }
}
