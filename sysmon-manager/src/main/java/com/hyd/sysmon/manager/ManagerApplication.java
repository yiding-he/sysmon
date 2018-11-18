package com.hyd.sysmon.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Configuration.class)
public class ManagerApplication {

    private static long agentETag = 0;

    public static long getAgentETag() {
        return agentETag;
    }

    public static void setAgentETag(long agentETag) {
        ManagerApplication.agentETag = agentETag;
    }

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
