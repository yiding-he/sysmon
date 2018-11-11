package com.hyd.sysmon.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Gatherer {

    private static final Logger LOG = LoggerFactory.getLogger(Gatherer.class);

    public static Map<String, Object> gatherFrom(Agent agent) {
        if (agent == null) {
            return Collections.emptyMap();
        }

        try {
            agent.refresh();
        } catch (Exception e) {
            LOG.error("", e);
            return Collections.emptyMap();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("cpu_usage", agent.getCpuUsage());

        return data;
    }
}
