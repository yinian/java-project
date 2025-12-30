package org.example;

public class ProcessIdDescriptor {

    private final long pid;
    private final String providerName;

    public ProcessIdDescriptor(long pid, String providerName) {
        super();
        this.pid = pid;
        this.providerName = providerName;
    }

    public long getPid() {
        return pid;
    }

    public String getProviderName() {
        return providerName;
    }
}
