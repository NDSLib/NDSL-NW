package com.ndsl.nw.bun133;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class NetWorkBase{

    public Socket socket;
    public OutputStream sockOut;
    public InputStream sockIn;
    public NetWorkBase(@NotNull Socket socket) throws IOException {
        this.socket=socket;
        this.sockIn=socket.getInputStream();
        this.sockOut=socket.getOutputStream();
    }

    public void sendData(@NotNull String data) throws IOException {
        sendData(data.getBytes());
    }

    public void sendData(@NotNull byte... bytes) throws IOException {
        sockOut.write(bytes);
    }

    public byte[] getData() throws IOException {
        if (isAvailable()){
            System.out.println("[NetWorkBase]NoData is Available");
            return new byte[0];
        }else{
            return sockIn.readAllBytes();
        }
    }

    public boolean isAvailable() throws IOException {
        return sockIn.available()<=0;
    }
}
