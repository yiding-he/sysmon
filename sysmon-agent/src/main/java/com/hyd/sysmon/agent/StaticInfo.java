package com.hyd.sysmon.agent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticInfo {

    private static final StaticInfo instance = new StaticInfo();

    public static StaticInfo getInstance() {
        return instance;
    }

    public StaticInfo() {
        this.cpuCount = cpuCount();
    }

    private int cpuCount;

    public int getCpuCount() {
        return cpuCount;
    }

    public void setCpuCount(int cpuCount) {
        this.cpuCount = cpuCount;
    }

    private int cpuCount() {
        try {
            return (int) Files.list(Paths.get("/dev/cpu"))
                    .filter(Files::isDirectory)
                    .count();
        } catch (IOException e) {
            return 0;
        }
    }
}
