package com.hyd.sysmon.manager.mvc;

import com.hyd.sysmon.manager.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hyd.sysmon.manager.ManagerApplication.setAgentETag;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/issue-agent-update")
    public Result issueAgentUpdate() {
        setAgentETag(System.currentTimeMillis());
        return Result.success("Agent updating sequence initiated.");
    }
}
