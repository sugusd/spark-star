package com.isxcode.star.api.utils;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class ArgsUtils {

    public static StarRequest parse(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("args is empty");
        }
        return JSON.parseObject(Base64.getDecoder().decode(args[0]), StarRequest.class);
    }
}
