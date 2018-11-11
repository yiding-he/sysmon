package com.hyd.sysmon.agent;

import java.net.MalformedURLException;
import java.net.URL;

public class AgentMain {

    public static void main(String[] args) {
        Agent agent = AgentFactory.getAgent();

        if (agent == null) {
            System.err.println("No agent available for current operating system");
            return;
        }

        if (args.length == 0) {
            System.err.println("Usage: java -jar agent.jar [manager_url]");
            return;
        }

        String managerUrl;

        try {
            new URL(args[0]);
            managerUrl = args[0];
        } catch (MalformedURLException e) {
            System.err.println("Argument is not a valid url.");
            return;
        }

        AgentScheduler agentScheduler = new AgentScheduler(managerUrl, agent);
        agentScheduler.start();
    }
}
