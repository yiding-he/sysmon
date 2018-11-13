package com.hyd.sysmon.manager;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class SysStatus {

    public static SysStatus parse(HttpServletRequest request) {
        SysStatus sysStatus = new SysStatus();
        sysStatus.host = request.getRemoteHost();
        sysStatus.cpuUsage = request.getParameter("cpu_usage");
        sysStatus.totalMemory = request.getParameter("mem_total");

        return sysStatus;
    }

    private long timestamp = System.currentTimeMillis();

    private String host;

    private String cpuUsage;

    private String totalMemory;

    private String memoryUsage;

    private String totalSwap;

    private String swapUsage;
}
