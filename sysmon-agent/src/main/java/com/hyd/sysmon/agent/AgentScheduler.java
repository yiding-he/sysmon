package com.hyd.sysmon.agent;

import mjson.Json;

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

    /**
     * make request to server and parse result
     *
     * @throws Exception anything unexpected happens
     */
    public void runAgent() throws Exception {
        Map<String, Object> map = Gatherer.gatherFrom(this.agent);
        HttpRequest httpRequest = new HttpRequest(this.managerUrl);
        map.forEach(httpRequest::setParameter);

        String jsonString = httpRequest.requestPost();
        Json json = Json.read(jsonString);

        // code == 0 means success, otherwise failure
        if (json.at("code").asInteger() != 0) {
            System.err.println("Manager return failing: " + json.at("message").asString());
            return;
        }

        long agentETag = json.at("data").at("agent-etag").asLong();
        processAgentETag(agentETag);
    }

    private void processAgentETag(long agentETag) throws Exception {

        // initial value
        if (AgentMain.getAgentETag() == 0 && agentETag > 0) {
            System.out.println("Current Agent ETag: " + agentETag);
            AgentMain.setAgentETag(agentETag);
            return;
        }

        // need update
        if (AgentMain.getAgentETag() < agentETag) {
            System.out.println("New Version Agent ETag: " + agentETag);
            AgentMain.restart();
        }
    }
}
