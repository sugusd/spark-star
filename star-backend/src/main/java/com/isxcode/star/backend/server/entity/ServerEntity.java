package com.isxcode.star.backend.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "server")
public class ServerEntity {

    @Id
    private String id;

    private String host;

    private String username;

    private String password;
}
