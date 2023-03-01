package com.isxcode.star.server.function;

import org.apache.spark.sql.api.java.UDF2;
import redis.clients.jedis.Jedis;


public class LookupFunc implements UDF2<String, String, String> {

    @Override
    public String call(String key, String type) throws Exception {

        Jedis jedis = new Jedis("121.89.247.94", 30119);
        String value = jedis.hget(key, type);
        jedis.close();
        return value;
    }

}
