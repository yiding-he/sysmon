package com.hyd.sysmon.agent.linux;

import com.hyd.sysmon.agent.Util;

import java.util.*;

public class TopParser {

    public static List<Map<String, String>> parseTopOutput(List<String> topOutputs) {
        int i = 0;
        while (i < topOutputs.size() && !Util.strEmpty(topOutputs.get(i))) {
            i++;
        }
        if (i >= topOutputs.size()) {
            return Collections.emptyList();
        }
        i++;

        String[] headers = topOutputs.get(i).trim().split("\\s+");
        List<Map<String, String>> maps = new ArrayList<>();
        i++;

        while (i < topOutputs.size()) {
            Map<String, String> map = new HashMap<>();
            String[] split = topOutputs.get(i).trim().split("\\s+");
            for (int j = 0; j < split.length; j++) {
                map.put(headers[j], split[j]);
            }
            maps.add(map);
            i++;
        }

        return maps;
    }
}
