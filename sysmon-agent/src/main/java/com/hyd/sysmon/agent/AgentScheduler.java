package com.hyd.sysmon.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AgentScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(AgentScheduler.class);

    private String managerUrl;

    private Agent agent;

    public AgentScheduler(String managerUrl, Agent agent) {
        this.managerUrl = managerUrl;
        this.agent = agent;
    }

    public void start() {
        Timer timer = new Timer("AGENT-SCHEDULER", false);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    runAgent();
                } catch (Exception e) {
                    LOG.error(e.toString());
                }
            }
        }, 0, 5000);

        LOG.info("Agent scheduler started.");
    }

    public void runAgent() throws Exception {
        Map<String, Object> map;

        try {
            map = Gatherer.gatherFrom(this.agent);
        } catch (Exception e) {
            LOG.error("", e);
            return;
        }

        HttpRequest httpRequest = new HttpRequest(this.managerUrl);
        map.forEach(httpRequest::setParameter);
        httpRequest.requestPost();
    }
}
