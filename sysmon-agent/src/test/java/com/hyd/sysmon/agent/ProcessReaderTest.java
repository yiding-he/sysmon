package com.hyd.sysmon.agent;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProcessReaderTest {

    @Test
    public void readFromCommand() {
        ProcessReader.readFromCommand("cmd.exe", "/c", "dir").forEach(System.out::println);
    }
}