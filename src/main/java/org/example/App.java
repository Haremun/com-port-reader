package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println(Arrays.toString(ports));

        for (SerialPort port : ports) {
            port.openPort();
            port.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    byte[] receivedData = serialPortEvent.getReceivedData();
                    System.out.println(serialPortEvent.getSerialPort().getSystemPortName() + ": " + new String(receivedData));
                }
            });
            port.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_WRITTEN;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    byte[] receivedData = serialPortEvent.getReceivedData();
                    System.out.println(serialPortEvent.getSerialPort().getSystemPortName() + ": " + new String(receivedData));
                }
            });
        }
        while (true) {
        }
    }
}
