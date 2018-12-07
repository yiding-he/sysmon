package com.hyd.sysmon.manager.mvc;

import com.hyd.sysmon.manager.Result;
import com.hyd.sysmon.manager.registry.NodeRegistry;
import com.hyd.sysmon.manager.registry.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * for visitor browser ajax request
 */
@Controller
public class IndexController {

    @Autowired
    private NodeRegistry nodeRegistry;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @GetMapping("/data")
    @ResponseBody
    public Result queryData() {
        return Result.success()
                .put("statuses", nodeRegistry.getNodeList())
                .put("services", serviceRegistry.getServiceList())
                ;
    }
}
