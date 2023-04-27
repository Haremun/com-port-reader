package pl.mwgrogowo;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    private static final String PORT_NAME = "COM7";

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort serialPort = getPort(ports);
        serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 1000);

        System.out.println("Found port " + PORT_NAME);
        if (!serialPort.openPort()) {
            System.out.println("Error code: " + serialPort.getLastErrorCode());
            System.out.println("Port could not be opened " + PORT_NAME);
            return;
        }
        System.out.println("Opened port " + serialPort.getDescriptivePortName());
        System.out.println("Baud rate: " + serialPort.getBaudRate());
        System.out.println("Parity: " + serialPort.getParity());
        System.out.println("Stop bits: " + serialPort.getNumStopBits());
        System.out.println("Data bits: " + serialPort.getNumDataBits());
        System.out.println("Port location: " + serialPort.getPortLocation());
        System.out.println("Port name: " + serialPort.getSystemPortName());
        System.out.println("Is open: " + serialPort.isOpen());

        AmcDataReceiver amcDataReceiver = AmcDataReceiver.from(serialPort, response -> System.out.println("Data received: " + response));
        executorService.scheduleAtFixedRate(amcDataReceiver::requestData, 0, 500, TimeUnit.MILLISECONDS);
    }

    private static SerialPort getPort(SerialPort[] ports) {
        return Arrays.stream(ports)
                .filter(serialPort -> serialPort.getSystemPortName().contains(PORT_NAME))
                .findFirst()
                .orElseThrow();
    }
}
