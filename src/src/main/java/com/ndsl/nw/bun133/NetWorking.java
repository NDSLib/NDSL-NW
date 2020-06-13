package com.ndsl.nw.bun133;

import com.ndsl.nw.bun133.client.NetWorkBase;
import com.ndsl.nw.bun133.json.Json;
import com.ndsl.nw.bun133.json.JsonContent;
import com.ndsl.nw.bun133.serializer.Field;
import com.ndsl.nw.bun133.server.JsonPing;
import com.ndsl.nw.bun133.server.NetWorkServerBase;
import com.ndsl.nw.bun133.server.Ping_Listener;

import java.io.IOException;
import java.util.Scanner;

public class NetWorking {
    public static final float Ver = 1.0f;


    @Field
    public static Json json = new Json();
    public static Json json_2 = new Json();
    public static JsonContent content = new JsonContent("test_field", "test_string");
    public static JsonContent content_1 = new JsonContent("test_field_1", 1);

    public static void main(String[] args) throws InterruptedException, IOException {
        new NetWorking().run();

    }

    public void run() throws InterruptedException, IOException {
        Json de_json=Json.build("{{\"ping\":\"\"}}");
        System.out.println("BuiltJson:"+de_json.toString());

        /*Serializer<NetWorking> serializer = new Serializer<>(this);
        JsonContent content1 = JsonContent.build("{1:{\"a\":\"a\"}}");

        serializer.serialize();
        json.add(content);
        json.add(content_1);
        json_2.add(content1);
        json.add(json_2.toContent("test_json"));
        System.out.println(json.serialize());
        System.out.println("Json2:" + json_2.serialize());*/


        /*
         * Up is JsonTest
         * Down is NetWork Test
         */
//        System.out.println("RequestTest");
//        System.out.println(HttpRequest.Companion.genReqHeader("GET", "google.com", "/index.html", 80));

        System.out.println("Server Thread Start");
        NetWorkServerBase server_thread=new NetWorkServerBase("127.0.0.1",8080);
        server_thread.start();
        server_thread.register.add(new Ping_Listener());
        server_thread.register.add(new JsonPing());

        System.out.println("Client is Start");
        NetWorkBase Connecting = new NetWorkBase("127.0.0.1",8080);
        if (Connecting.socket.isConnected()) {
            System.out.println("Client Connected!");
        }

        System.out.println("PingNanoMills:"+Connecting.ping()+"ms");

        System.out.println("Json Pinging...");
        System.out.println("PingWithJsonNanoMills:"+Connecting.pingWithJson()+"ms");

        Scanner scanner=new Scanner(System.in);
        Connecting.send(scanner.nextLine());
        /*while (true) {
            Thread.sleep(1);
            Connecting.send("Client Ping!");
            if (Connecting.isAvailable()) {
                System.out.println("Data is Available");
                System.out.println(Connecting.sockIn.readUTF());
            }
        }*/
    }
}