package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    private static final String PORT_NAME = "COM7";


    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort port = getPort(ports);
        System.out.println("Connected to " + PORT_NAME);
        port.openPort();
        System.out.println("Opened port " + port.getDescriptivePortName());
        AntenaCommunicator antenaCommunicator = new AntenaCommunicator(port);
        port.addDataListener(new AmcPortListener(antenaCommunicator));
        antenaCommunicator.requestData();
        while (true) {
        }
    }

    private static SerialPort getPort(SerialPort[] ports) {
        return Arrays.stream(ports)
                .filter(serialPort -> serialPort.getDescriptivePortName().contains(PORT_NAME))
                .findFirst()
                .orElseThrow();
    }
}
