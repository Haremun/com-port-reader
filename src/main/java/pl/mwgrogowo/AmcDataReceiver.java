package pl.mwgrogowo;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class AmcDataReceiver {

    private static final String GET_BUFFERED_DATA = ":??\n";

    private final SerialPort serialPort;
    private final BufferedReader bufferedReader;
    private final Consumer<String> responseHandler;

    public AmcDataReceiver(SerialPort serialPort, BufferedReader bufferedReader, Consumer<String> responseHandler) {
        this.serialPort = serialPort;
        this.bufferedReader = bufferedReader;
        this.responseHandler = responseHandler;
    }

    public static AmcDataReceiver from(SerialPort serialPort, Consumer<String> responseHandler) {
        return new AmcDataReceiver(
                serialPort,
                new BufferedReader(new InputStreamReader(serialPort.getInputStream())),
                responseHandler);
    }

    public void requestData() {
        if (bufferedReader == null) {
            return;
        }
        int i = serialPort.writeBytes(GET_BUFFERED_DATA.getBytes(), GET_BUFFERED_DATA.getBytes().length);
        if (i < 0) {
            System.out.println(serialPort.getLastErrorCode());
            return;
        }
        try {
            StringBuilder response = new StringBuilder(bufferedReader.readLine());
            while (bufferedReader.ready()) {
                response.append(" ");
                response.append(bufferedReader.readLine());
            }
            if (!response.toString().isBlank()) {
                responseHandler.accept(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
