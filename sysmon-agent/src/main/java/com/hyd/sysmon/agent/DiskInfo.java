package com.hyd.sysmon.agent;

import lombok.Data;

@Data
public class DiskInfo {

    private String fileSystem;

    private long total;

    private long used;

    private long available;

    private String mountedOn;
}
