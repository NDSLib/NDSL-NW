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
        if(server.isJson()){
            System.out.println("Json Received!");
            Json json=server.getJson();
            System.out.println("JsonFields:"+json.jsonContents.size());
            for (JsonContent content:json.jsonContents){
                System.out.println("Content"+content.toString());
            }
            if (json.getContent("ping")!=null){
                System.out.println("[Server]Received Json Ping");
            }
        }else{
            System.out.println("isJson:False");
        }
        System.out.println("onPing End");
    }
}
