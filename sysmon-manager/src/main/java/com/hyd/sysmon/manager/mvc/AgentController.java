package com.hyd.sysmon.manager.mvc;

import com.hyd.sysmon.manager.ManagerApplication;
import com.hyd.sysmon.manager.Result;
import com.hyd.sysmon.manager.registry.NodeRegistry;
import com.hyd.sysmon.manager.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * for agent update
 */
@RestController
public class AgentController {

    public static final String TYPE_NODE = "node";

    public static final String TYPE_SERVICE = "service";

    @Autowired
    private NodeRegistry nodeRegistry;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @PostMapping("/update")
    @ResponseBody
    public Result update(HttpServletRequest request) {
        String host = request.getRemoteHost();
        String type = Objects.toString(request.getParameter("type"), TYPE_NODE);
        Map<String, Object> data = parseToMap(request.getParameterMap(), host);

        if (type.equals(TYPE_NODE)) {
            nodeRegistry.addNode(data);
        } else if (type.equals(TYPE_SERVICE)) {
            serviceRegistry.addService(data);
        }

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
