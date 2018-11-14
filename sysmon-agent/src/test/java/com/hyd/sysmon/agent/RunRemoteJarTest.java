package com.hyd.sysmon.agent;

import java.net.URL;
import java.net.URLClassLoader;

public class RunRemoteJarTest {

    public static final String JAR_URL = "https://heyiding.com:666/sysmon-agent.jar";

    public static void main(String[] args) throws Exception {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(JAR_URL)});
        Class<?> mainClass = urlClassLoader.loadClass("com.hyd.sysmon.agent.AgentMain");
        mainClass.getMethod("main", String[].class).invoke(null, (Object) args);
    }
}
