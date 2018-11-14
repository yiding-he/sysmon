package com.hyd.sysmon.manager;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SysStatusManager {

    private Map<String, Map<String, Object>> statusMappings = new HashMap<>();

    public void addSysStatus(String host, Map<String, Object> data) {
        this.statusMappings.put(host, data);
    }

    public Collection<Map<String, Object>> getSysStatuses() {
        return statusMappings.values();
    }
}
