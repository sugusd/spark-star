package com.isxcode.star.api.utils;

import com.alibaba.fastjson2.JSON;
import com.isxcode.star.api.pojo.StarRequest;

public class ArgsUtils {

    public static StarRequest parse(String[] args) {

        if (args == null || args.length < 1) {
            return new StarRequest();
        }

        return JSON.parseObject(String.valueOf(args[0]), StarRequest.class);
    }
}
