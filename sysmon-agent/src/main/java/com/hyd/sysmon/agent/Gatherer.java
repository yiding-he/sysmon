package com.hyd.sysmon.agent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Gatherer {

    public static Map<String, Object> gatherFrom(Agent agent) throws Exception {

        if (agent == null) {
            return Collections.emptyMap();
        }

        agent.refresh();

        double memUsage = ((double) agent.getTotalMemory() - agent.getFreeMemory()) / agent.getTotalMemory();
        double swapUsage = ((double) agent.getTotalSwap() - agent.getFreeSwap()) / agent.getTotalSwap();

        Map<String, Object> data = new HashMap<>();
        data.put("host_name", System.getenv("HOST_NAME"));
        data.put("cpu_count", StaticInfo.getInstance().getCpuCount());
        data.put("cpu_usage", String.format("%.3f%%", agent.getCpuUsage()));
        data.put("mem_total", String.format("%,d KB", agent.getTotalMemory()));
        data.put("mem_usage", String.format("%.3f%%", memUsage * 100));
        data.put("swap_total", String.format("%,d KB", agent.getTotalSwap()));
        data.put("swap_usage", String.format("%.3f%%", swapUsage * 100));

        DiskInfo diskInfo = agent.getDiskInfo();
        if (diskInfo != null) {
            data.put("min_disk_path", diskInfo.getFileSystem() + " -> " +  diskInfo.getMountedOn());
            data.put("min_disk_available", String.format("%,d KB", diskInfo.getAvailable()));
        }

        return data;
    }
}
