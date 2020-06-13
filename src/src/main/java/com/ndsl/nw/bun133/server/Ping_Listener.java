package com.ndsl.nw.bun133.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Ping_Listener implements IServerListener {
    @Override
    public void onPacket(DataInputStream in, NetWorkServerBase server) throws IOException {
        if(in.readUTF().equals("echo request")){
            server.send("echo reply");
        }
    }
}
