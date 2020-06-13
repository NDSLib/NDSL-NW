package com.ndsl.nw.bun133;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

public class NetWorkBase{

    public Socket socket;
    public DataOutputStream sockOut;
    public DataInputStream sockIn;
    public static final int Default_Socket_Port=80;

    public NetWorkBase(String link,int port) throws IOException {
        this(new Socket(link,port));
    }

    public NetWorkBase(String link) throws IOException {
        this(new Socket(link,Default_Socket_Port));
    }

    public NetWorkBase(@NotNull Socket socket) throws IOException {
        this.socket=socket;
        this.sockIn=new DataInputStream(socket.getInputStream());
        this.sockOut=new DataOutputStream(socket.getOutputStream());
    }

    public void sendData(@NotNull String data) throws IOException {
        sendData(data.getBytes());
    }

    public void sendData(@NotNull byte... bytes) throws IOException {
        sockOut.write(bytes);
    }

    public byte[] getData() throws IOException {
        if (isAvailable()){
            return sockIn.readAllBytes();
        }else{
            System.out.println("[NetWorkBase]NoData is Available");
            return new byte[0];
        }
    }

    public boolean isAvailable() throws IOException {
        return sockIn.available()>0;
    }

    public void send(Object o) throws IOException {
        if(o instanceof String){
            this.sockOut.writeUTF((String)o);
        }else if(o instanceof Integer){
            this.sockOut.writeInt((int)o);
        }else if(o instanceof Boolean){
            this.sockOut.writeBoolean((boolean)o);
        }else if(o instanceof Character){
            this.sockOut.writeChar((char)o);
        }else if(o instanceof Double){
            this.sockOut.writeDouble((double)o);
        }else if(o instanceof Long){
            this.sockOut.writeLong((long)o);
        }else if(o instanceof Float){
            this.sockOut.writeFloat((float)o);
        }
    }
}
