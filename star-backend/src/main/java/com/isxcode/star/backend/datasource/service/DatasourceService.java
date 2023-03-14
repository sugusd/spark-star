package com.isxcode.star.backend.datasource.service;

import com.isxcode.star.backend.ReqDto;
import com.isxcode.star.backend.datasource.pojo.DatasourceEntity;
import com.isxcode.star.backend.datasource.repository.DatasourceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.isxcode.star.backend.config.AppConfig.PLUGIN_CLASSLOADER;

@RequiredArgsConstructor
@Service
@Slf4j
public class DatasourceService {

    private final DatasourceRepository datasourceRepository;

    public void addDatasource(ReqDto reqDto) {

        // 判断重复
        if (!Strings.isEmpty(reqDto.getName())) {
            DatasourceEntity datasource = datasourceRepository.findFirstByName(reqDto.getName());
            if (datasource != null) {
                throw new RuntimeException("数据源已存在");
            }
        }

        // 转换对象
        DatasourceEntity datasourceEntity = new DatasourceEntity();
        BeanUtils.copyProperties(reqDto, datasourceEntity);
        datasourceEntity.setId(UUID.randomUUID().toString());

        // 数据持久化
        datasourceRepository.save(datasourceEntity);
    }

    public List<DatasourceEntity> queryDatasource() {

        return datasourceRepository.findAll();
    }

    public void delDatasource(ReqDto reqDto) {

        datasourceRepository.deleteById(reqDto.getId());
    }

    public void delDatasources(ReqDto reqDto) {

        datasourceRepository.deleteAllByIdIn(reqDto.getIds());
    }

    public void updateDatasource(ReqDto reqDto) {

        try {
            DatasourceEntity one = datasourceRepository.getOne(reqDto.getName());
            one.setName(reqDto.getName());
            datasourceRepository.save(one);
        } catch (EntityNotFoundException e) {
        }
    }

    public DatasourceEntity getDatasource(ReqDto reqDto) {

        return datasourceRepository.getOne(reqDto.getId());
    }

    public boolean checkDatasource(ReqDto reqDto) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        DatasourceEntity datasource;
        if (!Strings.isEmpty(reqDto.getId())) {
            datasource = datasourceRepository.getOne(reqDto.getId());
        } else {
            datasource = new DatasourceEntity();
            BeanUtils.copyProperties(reqDto, datasource);
        }

        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURLMethod.setAccessible(true);
        addURLMethod.invoke(classLoader, PLUGIN_CLASSLOADER.get(datasource.getType()).getURLs()[0]);
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 连接测试
        try (Connection conn = DriverManager.getConnection(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
             Statement statement = conn.createStatement();) {
            statement.execute("show tables");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // 返回成功
        return true;
    }
}

