package com.isxcode.star.api.utils;

import com.alibaba.fastjson2.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArgsUtils {

    public static StarRequest parse(String[] args) {

        if (args == null || args.length < 1) {
            return new StarRequest();
        }

        log.info("args: {}", args[0]);
        return JSON.parseObject(String.valueOf(args[0]), StarRequest.class);
    }
}
