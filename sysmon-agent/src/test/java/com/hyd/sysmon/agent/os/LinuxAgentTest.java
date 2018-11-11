package com.hyd.sysmon.agent.os;

import org.junit.Test;

import static org.junit.Assert.*;

public class LinuxAgentTest {

    @Test
    public void testRefresh() throws Exception {
        new LinuxAgent().refresh();
    }
}