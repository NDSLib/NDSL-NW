package com.ndsl.nw.bun133.server;

import com.ndsl.nw.bun133.json.Json;
import com.ndsl.nw.bun133.json.JsonContent;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class JsonPing implements IServerListener{
    @Override
    public void onPacket(DataInputStream in, NetWorkServerBase server) throws IOException {
//        System.out.println("Data:"+in.readUTF());
//        System.out.println("Available:"+in.available());

            Json json=server.getJson();
            if(json==null) return;
            if (json.getContent("\"ping\"")!=null){
//                System.out.println("[Server]Received Json Ping");
                server.send(Json.build("{{\"pong\":\"\"}}"));
            }
//            System.out.println("onPing End");
    }
}
