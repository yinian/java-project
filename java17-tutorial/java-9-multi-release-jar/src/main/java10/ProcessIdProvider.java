package org.example;

import java.lang.management.ManagementFactory;

public class ProcessIdProvider {

    public ProcessIdDescriptor getPid() {
        var pid = ProcessHandle.current().pid();
        System.out.println("Java 10 Execution");
        return new ProcessIdDescriptor(pid, "ProcessHandle");
    }

}
