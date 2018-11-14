package com.hyd.sysmon.manager;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SysStatusManager {

    private Map<String, Map<String, Object>> statusMappings = new HashMap<>();

    public void addSysStatus(String host, Map<String, Object> data) {
        this.statusMappings.put(host, data);
    }

    public List<Map<String, Object>> getSysStatuses() {
        return statusMappings.values().stream()
                .sorted(Comparator.comparing(map -> String.valueOf(map.get("host"))))
                .filter(SysStatusManager::notExpired)
                .collect(Collectors.toList());
    }

    private static boolean notExpired(Map<String, Object> status) {
        long timestamp = (long) status.get("timestamp");
        return System.currentTimeMillis() - timestamp < 30000;
    }
}
