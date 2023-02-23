package com.isxcode.star.backend.server.service;

import com.isxcode.star.backend.server.entity.ServerEntity;
import com.isxcode.star.backend.server.repository.ServerRepository;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

@RequiredArgsConstructor
@Service
public class ServerService {

    private final ServerRepository serverRepository;

    public List<ServerEntity> queryServers() {

        return serverRepository.findAll();
    }

    public void saveServer(ServerEntity server) {

        serverRepository.save(server);
    }

    public void cleanServers() {

        serverRepository.deleteAll();
    }

    public boolean checkServerEnv(String serverId) throws JSchException, IOException, SftpException {

        ServerEntity server = serverRepository.getOne(serverId);

        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setHost(server.getHost());
        serverEntity.setUsername(server.getUsername());
        serverEntity.setPassword(server.getPassword());

        // 将服务器检测脚本推到目标服务器
        scpFile(serverEntity, "/Users/ispong/Isxcode/spark-star/star-dist/src/main/bin/check-env", "/Users/ispong/check-env");

        // 执行远程服务器是否符合要求
        return Boolean.parseBoolean(getCommandLog(serverEntity, "bash /Users/ispong/check-env"));
    }

    public String getCommandLog(ServerEntity server, String command) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(server.getUsername(), server.getHost(), 22);
        session.setPassword(server.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec channel = (ChannelExec)session.openChannel("exec");
        channel.setPty(true);
        channel.setCommand(command);
        channel.setInputStream(null);
        BufferedReader input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
        channel.connect();

        StringBuilder log = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null) {
            log.append(line);
        }

        input.close();
        channel.disconnect();
        session.disconnect();

        return log.toString();
    }

    public void scpFile(ServerEntity server, String srcPath, String dstPath) throws JSchException, SftpException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(server.getUsername(), server.getHost(), 22);
        session.setPassword(server.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect(1000);
        channel.put(srcPath, dstPath);

        channel.disconnect();
        session.disconnect();
    }

    public boolean installStar(String serverId) throws JSchException, IOException, SftpException {

//        ServerEntity server = serverRepository.getOne(serverId);

        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setHost("ispong-mac.local");
        serverEntity.setUsername("ispong");
        serverEntity.setPassword("song151617");

        // 安装脚本
        scpFile(serverEntity, "/Users/ispong/Isxcode/spark-star/star-dist/src/main/bin/star-install", "/Users/ispong/star-install");

        // 将安装包推到服务器
        scpFile(serverEntity, "/Users/ispong/Isxcode/spark-star/star-dist/target/spark-star-1.2.0-bin.tar.gz", "/Users/ispong/spark-star.tar.gz");

        // 执行远程服务器是否符合要求
        System.out.println(getCommandLog(serverEntity, "bash /Users/ispong/star-install"));

        getCommandLog(serverEntity, "nohup java -jar -Xmx2048m /Users/ispong/spark-star-1.2.0/lib/star-server.jar >> /Users/ispong/spark-star-1.2.0/log/star-server.log 2>&1 &");

        return true;
    }
}
