package com.hyd.sysmon.agent;

import com.hyd.sysmon.agent.os.LinuxAgent;

import java.util.Arrays;
import java.util.List;

public class AgentFactory {

    private static List<Agent> agents = Arrays.asList(
            new LinuxAgent()
    );

    public static Agent getAgent() {
        return agents.stream().filter(Agent::matchCurrentOS).findFirst().orElse(null);
    }
}
