package com.isxcode.star.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AppConfig {

    public static final Map<String, URLClassLoader> PLUGIN_CLASSLOADER = new ConcurrentHashMap();

    @Bean
    public void initDriver() throws MalformedURLException {

        Thread.currentThread().getContextClassLoader();

        String defaultDriverDir = "/Users/ispong/Data/driver";
        File[] jars = new File(defaultDriverDir).listFiles();
        if (jars != null) {
            for (File jar : jars) {
                URLClassLoader urlClassLoader;
                String s = jar.toURI().toString();
                if (s.contains("mysql-connector-java-5.0.2")) {
                    urlClassLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                    PLUGIN_CLASSLOADER.put("mysql5", urlClassLoader);
                } else if (s.contains("mysql-connector-j-8.0.31.jar")) {
                    urlClassLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                    PLUGIN_CLASSLOADER.put("mysql8", urlClassLoader);
                } else if (s.contains("mysql-connector-java-8.0.29.jar")) {
                    urlClassLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                    PLUGIN_CLASSLOADER.put("mysql8.29", urlClassLoader);
                }
            }
        }
    }
}
