package com.isxcode.star.backend;

import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.backend.server.entity.ServerEntity;
import com.isxcode.star.backend.server.service.ServerService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories
@EnableTransactionManagement
public class StarBackendApplication {

    private final ServerService serverService;

    public static void main(String[] args) {

        SpringApplication.run(StarBackendApplication.class, args);
    }

    @GetMapping("/queryServers")
    public List<ServerEntity> queryServers() {

        List<ServerEntity> serverEntities = serverService.queryServers();
        for (ServerEntity e : serverEntities) {
            e.setPassword("*****");
        }

        return serverEntities;
    }

    @GetMapping("/deleteServer")
    public void deleteServer(@RequestParam String serverId) {

        serverService.deleteServer(serverId);
    }

    @GetMapping("/cleanServers")
    public void cleanServers(){

        serverService.cleanServers();
    }

    @PostMapping("/addServer")
    public void addServer(@RequestBody ReqDto reqDto) {

        if (Strings.isEmpty(reqDto.getHost())) {
            throw new RuntimeException("参数未空");
        }

        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setId(String.valueOf(UUID.randomUUID()));
        serverEntity.setName(reqDto.getName());
        serverEntity.setHost(reqDto.getHost());
        serverEntity.setUsername(reqDto.getUsername());
        serverEntity.setPassword(reqDto.getPassword());
        serverEntity.setPort(reqDto.getPort());
        serverEntity.setPath(reqDto.getLocation());
        serverEntity.setStatus("未安装");

        serverService.saveServer(serverEntity);
    }

    @GetMapping("/checkServerEnv")
    public boolean checkServerEnv(@RequestParam String serverId) {

        try {
            return serverService.checkServerEnv(serverId);
        } catch (JSchException | IOException | SftpException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/installStar")
    public boolean installStar(@RequestParam String serverId){

        try {
            return serverService.installStar(serverId);
        } catch (JSchException | IOException | SftpException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/checkStar")
    public void checkStar(@RequestParam String serverId) {

        serverService.checkStar(serverId);
    }

    @PostMapping("/executeSparkSql")
    public StarData executeSparkSql(@RequestBody ReqDto reqDto) {

        return serverService.executeSparkSql(reqDto);
    }

    @PostMapping("/getJobStatus")
    public StarData getJobStatus(@RequestBody ReqDto reqDto) {

        return serverService.getJobStatus(reqDto);
    }

    @PostMapping("/getData")
    public StarData getData(@RequestBody ReqDto reqDto) {

        return serverService.getData(reqDto);
    }

    @PostMapping("/getJobLog")
    public StarData getJobLog(@RequestBody ReqDto reqDto) {

        return serverService.getJobLog(reqDto);
    }

    @PostMapping("/stopJob")
    public StarData stopJob(@RequestBody ReqDto reqDto) {

        return serverService.stopJob(reqDto);
    }

    @PostMapping("/login")
    public StarData login(@RequestBody ReqDto reqDto) {

        if ("admin".equals(reqDto.getAccount()) && "ispong123".equals(reqDto.getPassword())) {
            return StarData.builder().build();
        }
        throw new RuntimeException("登录失败");
    }

}
