package com.isxcode.star.backend;

import com.isxcode.star.backend.server.entity.ServerEntity;
import com.isxcode.star.backend.server.service.ServerService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@SpringBootApplication
@RequiredArgsConstructor
public class StarBackendApplication {

    private final ServerService serverService;

    public static void main(String[] args) {

        SpringApplication.run(StarBackendApplication.class, args);
    }

    @GetMapping("/queryServers")
    public List<ServerEntity> queryServers() {

        List<ServerEntity> serverEntities = serverService.queryServers();
        for (ServerEntity e : serverEntities) {
            e.setPassword(null);
        }

        return serverEntities;
    }

    @GetMapping("/cleanServers")
    public void cleanServers(){

        serverService.cleanServers();
    }

    @PostMapping("/saveServer")
    public void saveServer(@RequestBody ReqDto reqDto) {


        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setId(String.valueOf(UUID.randomUUID()));
        serverEntity.setHost(reqDto.getHost());
        serverEntity.setUsername(reqDto.getUsername());
        serverEntity.setPassword(reqDto.getPassword());

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
    public void checkStar(){


    }


    public void executeSparkSql(){


    }
}
