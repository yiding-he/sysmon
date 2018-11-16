package com.hyd.sysmon.agent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Gatherer {

    public static Map<String, Object> gatherFrom(Agent agent) {

        if (agent == null) {
            return Collections.emptyMap();
        }

        try {
            agent.refresh();
        } catch (Exception e) {
            return Collections.emptyMap();
        }

        double memUsage = ((double) agent.getTotalMemory() - agent.getFreeMemory()) / agent.getTotalMemory();
        double swapUsage = ((double) agent.getTotalSwap() - agent.getFreeSwap()) / agent.getTotalSwap();

        Map<String, Object> data = new HashMap<>();
        data.put("host_name", System.getenv("HOST_NAME"));
        data.put("cpu_usage", String.format("%.3f%%", agent.getCpuUsage()));
        data.put("mem_total", String.format("%,d KB", agent.getTotalMemory()));
        data.put("mem_usage", String.format("%.3f%%", memUsage * 100));
        data.put("swap_total", String.format("%,d KB", agent.getTotalSwap()));
        data.put("swap_usage", String.format("%.3f%%", swapUsage * 100));

        data.put("cpu_count", StaticInfo.getInstance().getCpuCount());
        return data;
    }
}
