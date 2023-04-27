package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class AntenaCommunicator {

    private static final String MESSAGE = ":?0002247ID$0A";

    private final SerialPort serialPort;

    public AntenaCommunicator(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void requestData() {
        serialPort.writeBytes(MESSAGE.getBytes(), MESSAGE.length());
    }
}
