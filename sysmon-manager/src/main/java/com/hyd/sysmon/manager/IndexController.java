package com.hyd.sysmon.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private SysStatusManager manager;

    @GetMapping("/data")
    @ResponseBody
    public Result queryData() {
        return Result.success().put("statuses", manager.getSysStatuses());
    }
}
