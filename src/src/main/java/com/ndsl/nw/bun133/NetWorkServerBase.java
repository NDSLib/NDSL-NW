package com.ndsl.nw.bun133;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class NetWorkServerBase extends Thread{
    public ServerSocket serverSocket=new ServerSocket();
    public NetWorkServerBase(String addres,int port) throws IOException {
        serverSocket.bind(new InetSocketAddress(addres,port));
    }

    public DataOutputStream outputStream;
    public DataInputStream inputStream;

    @Override
    public void run() {
        Socket socket= null;
        try {
            System.out.println("Waiting for Connection...");
            socket = serverSocket.accept();
            System.out.println("Server Connected!");
            assert socket != null;
            this.inputStream=new DataInputStream(socket.getInputStream());
            this.outputStream=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data="";
        //noinspection InfiniteLoopStatement
        while (true){
//            System.out.println("Thread Loop");
            try {
//                System.out.println("Trying");
                if(inputStream.available()>0){
                    System.out.println("Data Available");
                    send("Server Pong!");
                    System.out.println(inputStream.readUTF());
                }
//                System.out.println("Tried!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Object o) throws IOException {
        if(o instanceof String){
            this.outputStream.writeUTF((String)o);
        }else if(o instanceof Integer){
            this.outputStream.writeInt((int)o);
        }else if(o instanceof Boolean){
            this.outputStream.writeBoolean((boolean)o);
        }else if(o instanceof Character){
            this.outputStream.writeChar((char)o);
        }else if(o instanceof Double){
            this.outputStream.writeDouble((double)o);
        }else if(o instanceof Long){
            this.outputStream.writeLong((long)o);
        }else if(o instanceof Float){
            this.outputStream.writeFloat((float)o);
        }
    }
}
