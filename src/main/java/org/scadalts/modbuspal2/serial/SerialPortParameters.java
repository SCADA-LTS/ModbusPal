package org.scadalts.modbuspal2.serial;


public interface SerialPortParameters {

    static SerialPortParameters newParameters(String portOwnerName, String commPortId, int baudRate, int flowControlIn,
                                              int flowControlOut, int dataBits, int stopBits, int parity, int timeout) {
        SerialPortParametersImpl params = new SerialPortParametersImpl();
        params.setCommPortId(commPortId);
        params.setPortOwnerName(portOwnerName);
        params.setBaudRate(baudRate);
        params.setFlowControlIn(flowControlIn);
        params.setFlowControlOut(flowControlOut);
        params.setDataBits(dataBits);
        params.setStopBits(stopBits);
        params.setParity(parity);
        params.setTimeout(timeout);
        return params;
    }

    int getBaudRate();

    String getCommPortId();

    int getDataBits();

    int getFlowControlIn();

    int getFlowControlOut();

    int getParity();

    int getStopBits();

    String getPortOwnerName();

    int getTimeout();
}
