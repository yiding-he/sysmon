package com.hyd.sysmon.manager;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SysStatusManager {

    private Map<String, List<SysStatus>> statusMappings = new ConcurrentHashMap<>();

    public void addSysStatus(SysStatus sysStatus) {
        this.statusMappings
                .computeIfAbsent(sysStatus.getHost(), __ -> new Vector<>())
                .add(sysStatus);
    }

    public Map<String, List<SysStatus>> getSysStatuses() {
        return statusMappings;
    }
}
