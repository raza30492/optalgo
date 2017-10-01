package com.jazasoft.cloudbalance.model;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Computer {
    private int id;
    private int cpuPower;
    private int memory;
    private int networkBandwidth;
    private int cost;

    public Computer() {
    }

    public Computer(int id, int cpuPower, int memory, int networkBandwidth, int cost) {
        this.id = id;
        this.cpuPower = cpuPower;
        this.memory = memory;
        this.networkBandwidth = networkBandwidth;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCpuPower() {
        return cpuPower;
    }

    public void setCpuPower(int cpuPower) {
        this.cpuPower = cpuPower;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getNetworkBandwidth() {
        return networkBandwidth;
    }

    public void setNetworkBandwidth(int networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLabel() {
        return "Computer " + id;
    }
}
