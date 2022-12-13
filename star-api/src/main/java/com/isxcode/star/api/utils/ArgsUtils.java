package com.isxcode.star.api.utils;

import com.alibaba.fastjson.JSON;
import com.isxcode.star.api.pojo.StarRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Base64;

@Slf4j
public class ArgsUtils {

    public static StarRequest parse(String[] args) {

        Options options = new Options();
        options.addOption("star", true, "desc for star");

        DefaultParser parser = new DefaultParser();
        CommandLine cl;
        try {
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        log.info("star: {}", cl.getOptionValue("star"));

        return JSON.parseObject(Base64.getDecoder().decode(cl.getOptionValue("star")), StarRequest.class);
    }
}
