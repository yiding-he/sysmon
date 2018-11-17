package com.hyd.sysmon.agent;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AgentScheduler {

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
                } catch (IOException e) {
                    System.err.println(e.toString());
                } catch (Throwable e) {
                    e.printStackTrace();
                    timer.cancel();
                }
            }
        }, 0, 5000);

        System.out.println("Agent scheduler started at " + new Date());
    }

    public void runAgent() throws Exception {
        Map<String, Object> map = Gatherer.gatherFrom(this.agent);
        HttpRequest httpRequest = new HttpRequest(this.managerUrl);
        map.forEach(httpRequest::setParameter);
        httpRequest.requestPost();
    }
}
