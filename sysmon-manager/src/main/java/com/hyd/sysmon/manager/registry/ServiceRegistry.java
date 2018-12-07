package com.hyd.sysmon.manager.registry;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceRegistry {

    private Map<String, Map<String, Object>> serviceMappings = new HashMap<>();

    public void addService(Map<String, Object> data) {
        String host = Objects.toString(data.get("host"), "*");
        String name = Objects.toString(data.get("name"), "*");
        String id = host + ":" + name;

        data.put("id", id);
        this.serviceMappings.put(id, data);
    }

    public List<Map<String, Object>> getServiceList() {
        return serviceMappings.values().stream()
                .sorted(Comparator.comparing(map -> String.valueOf(map.get("name"))))
                .peek(map -> map.put("dead", !RegistryHelper.notExpired(map)))
                .collect(Collectors.toList());
    }
}
