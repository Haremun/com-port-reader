package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class AmcPortListener implements SerialPortDataListener {

    private static final String RESPONSE = "$0002247ID";
    private static final String EMPTY = "EMPTY";

    private final AntenaCommunicator antenaCommunicator;

    public AmcPortListener(AntenaCommunicator antenaCommunicator) {
        this.antenaCommunicator = antenaCommunicator;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] receivedData = serialPortEvent.getReceivedData();
        System.out.println(serialPortEvent.getSerialPort().getSystemPortName() + ": " + new String(receivedData));
        antenaCommunicator.requestData();
    }
}
