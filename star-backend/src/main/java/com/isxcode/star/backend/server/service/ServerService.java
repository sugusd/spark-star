package com.isxcode.star.backend.server.service;

import com.isxcode.star.api.pojo.StarResponse;
import com.isxcode.star.api.pojo.dto.StarData;
import com.isxcode.star.backend.ReqDto;
import com.isxcode.star.backend.server.entity.ServerEntity;
import com.isxcode.star.backend.server.repository.ServerRepository;
import com.isxcode.star.client.template.StarTemplate;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ServerService {

    private final StarTemplate starTemplate;

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
        scpFile(serverEntity, "/star-package/check-env", server.getPath() + "/check-env");

        // 执行远程服务器是否符合要求
        return Boolean.parseBoolean(getCommandLog(serverEntity, "bash /Users/ispong/check-env", true));
    }

    public String getCommandLog(ServerEntity server, String command, boolean pty) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(server.getUsername(), server.getHost(), 22);
        session.setPassword(server.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setPty(pty);
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

    public void executeCommand(ServerEntity server, String command, boolean pty) throws JSchException, IOException {

        JSch jsch = new JSch();
        Session session = jsch.getSession(server.getUsername(), server.getHost(), 22);
        session.setPassword(server.getPassword());
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setPty(pty);
        channel.setCommand(command);
        channel.connect();

        channel.disconnect();
        session.disconnect();
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

        ServerEntity server = serverRepository.getOne(serverId);

        ServerEntity serverEntity = new ServerEntity();
        serverEntity.setHost(server.getHost());
        serverEntity.setUsername(server.getUsername());
        serverEntity.setPassword(server.getPassword());

        // 安装脚本
        scpFile(serverEntity, "/star-package/star-install", server.getPath());

        // 将安装包推到服务器
        scpFile(serverEntity, "/star-package/spark-star-1.2.0-bin.tar.gz", server.getPath());

        // 执行远程服务器是否符合要求
        executeCommand(serverEntity, "bash " + server.getPath() + "/star-install " + server.getPath(), false);

        server.setStatus("运行中");
        serverRepository.save(server);

        return true;
    }

    public void deleteServer(String serverId) {

        serverRepository.deleteById(serverId);
    }

    public void checkStar(String serverId) {

        ServerEntity server = serverRepository.getOne(serverId);
        server.setStatus("运行中");
        serverRepository.save(server);
    }

    public StarData executeSparkSql(ReqDto reqDto) {

        ServerEntity server = serverRepository.getOne(reqDto.getServerId());

        StarResponse starResponse = starTemplate.build(server.getHost(), Integer.parseInt(server.getPort()), "star-key").starHome(server.getPath() + "/spark-star-1.2.0").sql(reqDto.getSparkSql()).execute();

        return starResponse.getData();
    }

    public StarData getJobStatus(ReqDto reqDto) {

        ServerEntity server = serverRepository.getOne(reqDto.getServerId());

        StarResponse starResponse = starTemplate.build(server.getHost(), Integer.parseInt(server.getPort()), "star-key").starHome(server.getPath() + "/spark-star-1.2.0").applicationId(reqDto.getApplicationId()).getStatus();

        return starResponse.getData();
    }

    public StarData stopJob(ReqDto reqDto) {

        ServerEntity server = serverRepository.getOne(reqDto.getServerId());

        StarResponse starResponse = starTemplate.build(server.getHost(), Integer.parseInt(server.getPort()), "star-key").starHome(server.getPath() + "/spark-star-1.2.0").applicationId(reqDto.getApplicationId()).stopJob();

        return starResponse.getData();
    }

    public StarData getJobLog(ReqDto reqDto) {

        ServerEntity server = serverRepository.getOne(reqDto.getServerId());

        StarResponse starResponse = starTemplate.build(server.getHost(), Integer.parseInt(server.getPort()), "star-key").starHome(server.getPath() + "/spark-star-1.2.0").applicationId(reqDto.getApplicationId()).getLog();

        return starResponse.getData();
    }

    public StarData getData(ReqDto reqDto) {

        ServerEntity server = serverRepository.getOne(reqDto.getServerId());

        StarResponse starResponse = starTemplate.build(server.getHost(), Integer.parseInt(server.getPort()), "star-key").starHome(server.getPath() + "/spark-star-1.2.0").applicationId(reqDto.getApplicationId()).getData();

        return starResponse.getData();
    }

}

