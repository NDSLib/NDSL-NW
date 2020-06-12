package com.ndsl.nw.bun133;

import com.ndsl.nw.bun133.json.Json;
import com.ndsl.nw.bun133.json.JsonContent;
import com.ndsl.nw.bun133.serializer.Field;
import com.ndsl.nw.bun133.serializer.Serializer;

public class NetWorking {
    @Field
    public static Json json=new Json();
    public static Json json_2=new Json();
    public static JsonContent content=new JsonContent("test_field","test_string");
    public static JsonContent content_1=new JsonContent("test_field_1",1);
    public static void main(String[] args) {
        new NetWorking().run();

    }

    public void run(){
        Serializer<NetWorking> serializer=new Serializer<>(this);
        JsonContent content1 = JsonContent.build("{1:{\"a\":\"a\"}}");

        serializer.serialize();
        json.add(content);
        json.add(content_1);
        json_2.add(content1);
        json.add(json_2.toContent("test_json"));
        System.out.println(json.serialize());
        System.out.println("Json2:"+json_2.serialize());


        System.exit(0);
    }
}
