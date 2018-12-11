package com.hyd.sysmon.agent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class AgentMain {

    private static long agentETag = 0;

    public static long getAgentETag() {
        return agentETag;
    }

    public static void setAgentETag(long agentETag) {
        AgentMain.agentETag = agentETag;
    }

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

    ///////////////////////////////////////////////////////////////////////////////

    private static final String RESTART_SCRIPT = System.getProperty("restart");

    public static void restart() {

        if (Util.strEmpty(RESTART_SCRIPT)) {
            System.err.println("No restart script (-Drestart=/path/to/script) specified.");
            return;
        }

        try {
            String[] command = {"/bin/sh", RESTART_SCRIPT, "&"};
            System.out.println("Restarting agent with command - " + String.join(" ", command));
            new ProcessBuilder().command(command).start();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
