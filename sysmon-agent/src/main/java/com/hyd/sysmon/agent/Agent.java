package com.hyd.sysmon.agent;

public interface Agent {

    boolean matchCurrentOS();

    void refresh() throws Exception;

    double getCpuUsage();

    // KB
    long getTotalMemory();

    // KB
    long getFreeMemory();

    // KB
    long getTotalSwap();

    // KB
    long getFreeSwap();

    DiskInfo getDiskInfo();
}
