package com.hyd.sysmon.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private SysStatusManager manager;

    @GetMapping({"", "/"})
    public String index0() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("index")
                .addObject("statuses", manager.getSysStatuses());
    }
}
