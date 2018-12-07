package com.hyd.sysmon.manager.registry;

import java.util.Map;

public class RegistryHelper {

    public static boolean notExpired(Map<String, Object> item) {
        long timestamp = (long) item.get("timestamp");
        return System.currentTimeMillis() - timestamp < 30000;
    }
}
