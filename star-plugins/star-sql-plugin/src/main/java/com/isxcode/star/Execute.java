package com.isxcode.star;

import org.apache.spark.sql.SparkSession;

public class Execute {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder()
            .config("spark.driver.memory", "1g")
            .config("spark.executor.memory", "2g")
            .getOrCreate();

        // 执行spark
        System.out.println("开始执行");

        // 停止session
        sparkSession.stop();
    }
}
