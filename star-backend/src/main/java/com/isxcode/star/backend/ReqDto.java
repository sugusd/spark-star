package com.isxcode.star.backend;

import lombok.Data;

import java.util.List;

@Data
public class ReqDto {

    private String id;

    private String name;

    public String username;

    private String host;

    private String password;

    private String sparkSql;

    private String serverId;

    private String location;

    private String port;

    private String applicationId;

    private String account;

    private String age;

    private List<String> ids;

    private String url;

    private String type;
}
