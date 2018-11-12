package com.hyd.sysmon.manager;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SysStatusManager {

    private Map<String, SysStatus> statusMappings = new HashMap<>();

    public void addSysStatus(SysStatus sysStatus) {
        this.statusMappings.put(sysStatus.getHost(), sysStatus);
    }

    public Collection<SysStatus> getSysStatuses() {
        return statusMappings.values();
    }
}
