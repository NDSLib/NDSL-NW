package com.ndsl.nw.bun133;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ndsl.nw.bun133.api.mildom.GiftSet;
import com.ndsl.nw.bun133.api.mildom.MildomAPI;
import com.ndsl.nw.bun133.api.mildom.jsons.Gifts;
import com.ndsl.nw.bun133.client.HttpClient;
import com.ndsl.nw.bun133.client.NetWorkBase;
import com.ndsl.nw.bun133.json.Json;
import com.ndsl.nw.bun133.json.JsonContent;
import com.ndsl.nw.bun133.json.JsonException;
import com.ndsl.nw.bun133.serializer.Field;
import com.ndsl.nw.bun133.server.JsonPing;
import com.ndsl.nw.bun133.server.NetWorkServerBase;
import com.ndsl.nw.bun133.server.Ping_Listener;
import com.ndsl.nw.bun133.websocket.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class NetWorking {
    public static final float Ver = 1.0f;


    @Field
    public static Json json = new Json();
    public static Json json_2 = new Json();
    public static JsonContent content = new JsonContent("test_field", "test_string");
    public static JsonContent content_1 = new JsonContent("test_field_1", 1);

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException, JsonException {
//        new NetWorking().run();
//        new NetWorking().json_test();
//        new NetWorking().httpTest();
//        new NetWorking().json_phase();
//        new NetWorking().p2p_test();
//        new NetWorking().webSocketTest();
//        new NetWorking().mildomAPI();
        new NetWorking().mildomGiftTest();
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

    public void httpTest() throws URISyntaxException, IOException, InterruptedException, JsonException {
        HttpClient client=new HttpClient("https://api.p2pquake.net/v1/human-readable");
        System.out.println("body:"+client.sendReq(client.Request.GET.genGetReq()).body());
        System.out.println("StatusCode:"+client.sendReq(client.Request.GET.genGetReq()).statusCode());
//        client.sendReqAndGetJson(client.Request.GET.genGetReq());
        String s=client.sendReq(client.Request.GET.genGetReq()).body();
        System.out.println("Json:"+s.substring(1,s.length()-1));
//        Json.buildThrowError(s);
    }

    public void json_test(){
        Json de__json=Json.build("{{\"ping\":[\"AAA_Value\",\"B_Value\"]}}");
        System.out.println("Serialize:"+de__json.serialize());
    }

    public void json_phase() {
        //noinspection SpellCheckingInspection
        String data="{\n" +
                "    \"time\": \"2014/09/27 01:41:13.992\", \n" +
                "    \"code\": 5610, \n" +
                "    \"count\": 9, \n" +
                "    \"areas\": {\n" +
                "        \"South Tochigi\": 1, \n" +
                "        \"East Kanagawa\": 1, \n" +
                "        \"Chenter Fukushima\": 2, \n" +
                "        \"North Ibaragi\": 4, \n" +
                "        \"South Ibaragi\": 1\n" +
                "    }, \n" +
                "    \"prefs\": {\n" +
                "        \"Tochigi\": 1, \n" +
                "        \"Kanagawa\": 1, \n" +
                "        \"Fukushima\": 2, \n" +
                "        \"Ibaragi\": 5\n" +
                "    }, \n" +
                "    \"regions\": {\n" +
                "        \"Tohoku\": 2, \n" +
                "        \"Kantou\": 7\n" +
                "    }\n" +
                "}";
        Gson gson=new Gson();
        JsonObject res = gson.fromJson(data, JsonObject.class);
        System.out.println("Code:"+res.get("code").getAsInt());
        for (Map.Entry<String, JsonElement> a:res.get("areas").getAsJsonObject().entrySet()){
            System.out.println("Name:"+a.getKey()+" Data:"+a.getValue().getAsString());
        }
    }

    public void p2p_test() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client=new HttpClient("http://api.p2pquake.net/v1/human-readable");
        Gson gson=new GsonBuilder().setLenient().create();
        byte[] r=client.sendByteReq(client.Request.GET.genGetReq()).body();
        String res_s = new String(r, StandardCharsets.UTF_8);
        String s=res_s.substring(1,res_s.length()-1);
        s = s.replace("\\","");
        System.out.println("S::"+res_s);
        JsonObject res = gson.fromJson(s, JsonObject.class);
        System.out.println("Code:"+res.get("code").getAsInt());
        for (Map.Entry<String, JsonElement> a:res.get("areas").getAsJsonObject().entrySet()){
            System.out.println("Name:"+a.getKey()+" Data:"+a.getValue().getAsString());
        }
    }

    public void webSocketTest() throws URISyntaxException, InterruptedException {
        WebSocketClient client = new WebSocketClient(new URI("wss://jp-room1.mildom.com/?roomId=10105254"));
        client.connect();
        while(!client.isConnecting()){
            System.out.println("waiting...");
        }
        client.send("{\n" +
                "    \"userId\": 15486324,\n" +
                "    \"level\": 1,\n" +
                "    \"userName\": \"Bun133_Dev\",\n" +
                "    \"guestId\": \"guest578951\",\n" +
                "    \"nonopara\": \"\",\n" +
                "    \"roomId\": 10105254,\n" +
                "    \"cmd\": \"enterRoom\",\n" +
                "    \"reConnect\": 0,\n" +
                "    \"nobleLevel\": 0,\n" +
                "    \"avatarDecortaion\": 0,\n" +
                "    \"enterroomEffect\": 0,\n" +
                "    \"nobleClose\": 0,\n" +
                "    \"nobleSeatClose\": 0,\n" +
                "    \"reqId\": 1\n" +
                "}");
        System.out.println("send");
        while(true){
            Thread.sleep(10);
            client.getMessages().forEach(System.out::println);
            client.getMessages().clear();
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void mildomAPI() throws InterruptedException {
        MildomAPI api=new MildomAPI(10105254);
        api.connect();
        while(true){
            Thread.sleep(10);
            api.getMessages().forEach(System.out::println);
            api.getMessages().clear();
        }
    }

    public void mildomGiftTest(){
        GiftSet set = new GiftSet();
        for(Gifts.Gift gift : set.getGifts().body.models){
            System.out.println("Gift:Id:"+gift.gift_id+" GiftName:"+gift.name);
        }
    }
}