package com.hyd.sysmon.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProcessReader {

    public static class ProcessReaderException extends RuntimeException {

        public ProcessReaderException() {
        }

        public ProcessReaderException(String message) {
            super(message);
        }

        public ProcessReaderException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProcessReaderException(Throwable cause) {
            super(cause);
        }
    }

    ////////////////////////////////////////////////////////////////////////

    public static List<String> readFromCommand(String... command) {
        return readFromCommand(Arrays.asList(command));
    }

    public static List<String> readFromCommand(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            List<String> lines = readInputStream(process.getInputStream());
            int result = process.waitFor();

            if (result != 0) {
                return Collections.emptyList();
            } else {
                return lines;
            }
        } catch (Exception e) {
            throw new ProcessReaderException(e);
        }
    }

    private static List<String> readInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            List<String> result = new ArrayList<>();
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result;
        }

    }
}
