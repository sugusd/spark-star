package com.isxcode.star.backend;

import lombok.Data;

@Data
public class ReqDto {

    public String username;

    private String host;

    private String password;

    private String sparkSql;

    private String serverId;
}
