package com.hyd.sysmon.agent.linux;

import com.hyd.sysmon.agent.DiskInfo;

import java.util.List;
import java.util.stream.Collectors;

public class DfParser {

    public static List<DiskInfo> parse(List<String> dfResult) {
        return dfResult.stream()
                .filter(line -> line.startsWith("/dev/"))
                .filter(line -> !line.endsWith("/boot"))
                .map(DfParser::parseLine)
                .collect(Collectors.toList());
    }

    private static DiskInfo parseLine(String line) {
        String[] split = line.split("\\s+");
        DiskInfo diskInfo = new DiskInfo();
        diskInfo.setFileSystem(split[0]);
        diskInfo.setTotal(Long.parseLong(split[1]));
        diskInfo.setUsed(Long.parseLong(split[2]));
        diskInfo.setAvailable(Long.parseLong(split[3]));
        diskInfo.setMountedOn(split[5]);
        return diskInfo;
    }
}
