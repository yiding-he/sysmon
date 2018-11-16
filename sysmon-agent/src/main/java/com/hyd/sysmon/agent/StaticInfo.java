package com.hyd.sysmon.agent;

import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class StaticInfo {

    private static final StaticInfo instance = new StaticInfo();

    public static StaticInfo getInstance() {
        return instance;
    }

    public StaticInfo() {
        this.cpuCount = cpuCount();
    }

    private int cpuCount;

    private int cpuCount() {
        try {
            return (int) Files.list(Paths.get("/dev/cpu")).count();
        } catch (IOException e) {
            return 0;
        }
    }
}
