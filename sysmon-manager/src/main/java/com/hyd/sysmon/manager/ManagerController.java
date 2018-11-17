package com.hyd.sysmon.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ManagerController {

    private int agentVersion = 0;

    @Autowired
    private SysStatusManager sysStatusManager;

    @PostMapping("/update")
    @ResponseBody
    public String update(HttpServletRequest request) {
        String host = request.getRemoteHost();

        sysStatusManager.addSysStatus(
                host, parseToMap(request.getParameterMap(), host)
        );

        return String.valueOf(agentVersion);
    }

    @PostMapping("/set-agent-version")
    @ResponseBody
    public String setAgentVersion(int version) {
        this.agentVersion = version;
        return "Agent version updated to " + this.agentVersion + ".";
    }

    private Map<String, Object> parseToMap(Map<String, String[]> map, String host) {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", System.currentTimeMillis());
        result.put("host", host);

        map.forEach((key, value) -> {
            if (value.length > 0) {
                result.put(key, value[0]);
            }
        });

        return result;
    }
}
