package com.hyd.sysmon.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ManagerController {

    @Autowired
    private SysStatusManager sysStatusManager;

    @PostMapping("/update")
    public void update(HttpServletRequest request) {
        sysStatusManager.addSysStatus(SysStatus.parse(request));
    }
}
