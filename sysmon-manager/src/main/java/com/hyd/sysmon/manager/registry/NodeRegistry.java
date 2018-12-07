package com.hyd.sysmon.manager.registry;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NodeRegistry {

    private Map<String, Map<String, Object>> nodeMappings = new HashMap<>();

    public void addNode(Map<String, Object> data) {
        String host = Objects.toString(data.get("host"), "*");
        this.nodeMappings.put(host, data);
    }

    public List<Map<String, Object>> getNodeList() {
        return nodeMappings.values().stream()
                .sorted(Comparator.comparing(map -> String.valueOf(map.get("host_name"))))
                .filter(RegistryHelper::notExpired)
                .collect(Collectors.toList());
    }

}
