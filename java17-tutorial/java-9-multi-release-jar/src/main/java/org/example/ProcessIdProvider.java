package org.example;

import java.lang.management.ManagementFactory;

public class ProcessIdProvider {

    public ProcessIdDescriptor getPid() {
        String vmName = ManagementFactory.getRuntimeMXBean().getName();
        long pid = Long.parseLong( vmName.split( "@" )[0] );
        System.out.println("Pre-Java 9 Execution");
        return new ProcessIdDescriptor( pid, "RuntimeMXBean" );
    }
}
