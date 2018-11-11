package com.hyd.sysmon.agent.os;

import com.hyd.sysmon.agent.Agent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class LinuxAgent implements Agent {

    public static final String ROOT = System.getProperty("root", "/");

    private double cpuUsage;

    private double[] lastStat;

    @Override
    public boolean matchCurrentOS() {
        String osName = System.getProperty("os.name");
        return osName != null &&
                (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
    }

    @Override
    public void refresh() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ROOT + "proc/stat"));
        if (lastStat == null) {
            lastStat = parseStat(lines);
            cpuUsage = -1;
        } else {
            double[] current = parseStat(lines);
            cpuUsage = (current[3] - lastStat[3]) / (current[9] - lastStat[9]) * 100;
            lastStat = current;
        }
    }

    private double[] parseStat(List<String> lines) {

        double[] arr = Stream.of(lines.get(0).split("\\s+"))
                .filter(s -> s.matches("^\\d+$"))
                .mapToDouble(Double::parseDouble)
                .toArray();

        double[] result = new double[arr.length + 1];
        System.arraycopy(arr, 0, result, 0, arr.length);
        result[result.length - 1] = DoubleStream.of(arr).sum();

        return result;
    }

    @Override
    public double getCpuUsage() {
        return this.cpuUsage;
    }
}
