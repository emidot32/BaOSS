package edu.baoss.resourceservice.dtos;

import edu.baoss.resourceservice.model.Device;
import lombok.Data;

@Data
public class UserDevice {
    String id;
    String name;
    String type;
    double price;
    String throughput;
    int portsNum;
    String portTypes;
    int guarantee;
    String[] standards;
    int memory;
    String macAddress;
    String serialNumber;
    String[] frequencies;
    String[] protocolsAndTechnologies;

    public UserDevice(Device device) {
        this.id = device.getId();
        this.name = device.getName();
        this.type = device.getType();
        this.price = device.getPrice();
        this.throughput = device.getThroughput();
        this.portsNum = device.getPortsNum();
        this.portTypes = device.getPortTypes();
        this.guarantee = device.getGuarantee();
        this.standards = device.getStandards();
        this.memory = device.getMemory();
        this.frequencies = device.getFrequencies();
        this.protocolsAndTechnologies = device.getProtocolsAndTechnologies();
    }
}
