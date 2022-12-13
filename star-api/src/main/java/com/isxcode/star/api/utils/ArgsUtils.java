package com.isxcode.star.api.utils;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@Slf4j
public class ArgsUtils {

    public static StarRequest parse(String[] args) {

        if (args == null || args.length < 1) {
            return new StarRequest();
        }

        log.info("args {}", args[0]);
        log.info("request {}", args[1]);

        Options options = new Options();
        options.addOption("star", true, "desc for star");

        DefaultParser parser = new DefaultParser();
        CommandLine cl;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String value = cl.getOptionValue("star");

        log.info("request info:{}", value);

        return JSON.parseObject(value, StarRequest.class);
    }

}
