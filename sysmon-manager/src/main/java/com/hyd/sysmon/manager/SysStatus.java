package com.hyd.sysmon.manager;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class SysStatus {

    public static SysStatus parse(HttpServletRequest request) {
        SysStatus sysStatus = new SysStatus();
        sysStatus.host = request.getRemoteHost();
        sysStatus.cpuUsage = Double.parseDouble(request.getParameter("cpu_usage"));

        return sysStatus;
    }

    private long timestamp = System.currentTimeMillis();

    private String host;

    private double cpuUsage;

}
