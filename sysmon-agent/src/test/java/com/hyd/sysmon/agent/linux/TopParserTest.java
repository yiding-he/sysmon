package com.hyd.sysmon.agent.linux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TopParserTest {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/test/resources/command/top"));
        TopParser.parseTopOutput(lines).forEach(System.out::println);
    }
}