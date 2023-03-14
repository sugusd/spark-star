package com.isxcode.star.backend.datasource.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "datasource")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class DatasourceEntity {

    @Id
    private String id;

    private String name;

    private String comment;

    private String url;

    private String host;

    private String port;

    private String database;

    private String type;

    private String username;

    private String password;
}
