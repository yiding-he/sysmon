package com.hyd.sysmon.agent.os;

import com.hyd.sysmon.agent.Agent;
import com.hyd.sysmon.agent.DiskInfo;
import com.hyd.sysmon.agent.ProcessReader;
import com.hyd.sysmon.agent.Util;
import com.hyd.sysmon.agent.linux.DfParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static com.hyd.sysmon.agent.ProcessReader.readFromCommand;

public class LinuxAgent implements Agent {

    public static final String ROOT = System.getProperty("root", "/");

    private double cpuUsage;

    private long totalMemory;

    private long freeMemory;

    private long totalSwap;

    private long freeSwap;

    private double[] lastStat;

    private DiskInfo diskInfo;

    @Override
    public boolean matchCurrentOS() {
        String osName = System.getProperty("os.name");
        return osName != null &&
                (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
    }

    @Override
    public void refresh() throws Exception {
        refreshCpuUsage();
        refreshMemoryUsage();
        refreshDiskInfo();
    }

    ///////////////////////////////////////////////////////////////

    private void refreshDiskInfo() {
        List<DiskInfo> diskInfoList = DfParser.parse(readFromCommand("df", "-k"));
        Comparator<DiskInfo> comparator = Comparator.comparing(DiskInfo::getAvailable);
        this.diskInfo = diskInfoList.stream().min(comparator).orElse(null);
    }

    private void refreshMemoryUsage() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(ROOT + "proc/meminfo"));

        Map<String, Long> memInfoMap = lines.stream()
                .filter(Util::strNotEmpty)
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(
                        split -> split[0].trim(),
                        split -> parseMemorySize(split[1].trim())
                ));

        this.totalMemory = memInfoMap.get("MemTotal");
        this.freeMemory = memInfoMap.get("MemFree") + memInfoMap.get("Buffers") + memInfoMap.get("Cached");
        this.totalSwap = memInfoMap.get("SwapTotal");
        this.freeSwap = memInfoMap.get("SwapFree");
    }

    // '100 kb'
    private static long parseMemorySize(String s) {
        String[] split = s.split("\\s+");
        String unit = split.length > 1? split[1].toLowerCase(): "";
        long number = Long.parseLong(split[0]);

        switch (unit) {
            case "kb":
                return number;
            case "mb":
                return number * 1024;
            case "gb":
                return number * 1024 * 1024;
            case "tb":
                return number * 1024 * 1024 * 1024;
            default:
                return number;
        }
    }

    ///////////////////////////////////////////////////////////////

    private void refreshCpuUsage() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(ROOT + "proc/stat"));
        if (lastStat == null) {
            lastStat = parseStat(lines);
            cpuUsage = -1;
        } else {
            double[] current = parseStat(lines);
            cpuUsage = 100.0 - ((current[3] - lastStat[3]) / (current[9] - lastStat[9]) * 100);
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

    ///////////////////////////////////////////////////////////////

    @Override
    public double getCpuUsage() {
        return this.cpuUsage;
    }

    @Override
    public long getTotalMemory() {
        return totalMemory;
    }

    @Override
    public long getFreeMemory() {
        return freeMemory;
    }

    @Override
    public long getTotalSwap() {
        return totalSwap;
    }

    @Override
    public long getFreeSwap() {
        return freeSwap;
    }

    @Override
    public DiskInfo getDiskInfo() {
        return diskInfo;
    }
}
