package com.hyd.sysmon.manager.mvc;

import com.hyd.sysmon.manager.ManagerApplication;
import com.hyd.sysmon.manager.Result;
import com.hyd.sysmon.manager.SysStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * for agent update
 */
@RestController
public class AgentController {

    @Autowired
    private SysStatusManager sysStatusManager;

    @PostMapping("/update")
    @ResponseBody
    public Result update(HttpServletRequest request) {
        String host = request.getRemoteHost();

        sysStatusManager.addSysStatus(
                host, parseToMap(request.getParameterMap(), host)
        );

        return Result.success().put("agent-etag", ManagerApplication.getAgentETag());
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
