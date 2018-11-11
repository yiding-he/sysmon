package com.hyd.sysmon.agent;

public interface Agent {

    boolean matchCurrentOS();

    void refresh() throws Exception;

    double getCpuUsage();
}
