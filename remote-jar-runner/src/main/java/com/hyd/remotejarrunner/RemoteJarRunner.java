package com.hyd.remotejarrunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.stream.Stream;

public class RemoteJarRunner {

    // 参数1: jar 地址列表，分号隔开；
    // 参数2: 启动类的名字
    public static void main(String[] args) throws Exception {
        String mainClassName = System.getProperty("mainClass");

        URL[] jarUrls = Stream
                .of(System.getProperty("jars").split(";"))
                .filter(Util::strNotEmpty)
                .map(RemoteJarRunner::newUrl)
                .filter(Objects::nonNull)
                .toArray(URL[]::new);

        URLClassLoader urlClassLoader = new URLClassLoader(jarUrls);
        Class<?> mainClass = urlClassLoader.loadClass(mainClassName);
        mainClass.getMethod("main", String[].class).invoke(null, (Object) args);
    }

    private static URL newUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
