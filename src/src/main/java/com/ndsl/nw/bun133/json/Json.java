package com.ndsl.nw.bun133.json;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class Json {
    public List<JsonContent> jsonContents=new LinkedList<>();
    public Json(){
    }

    public Json add(JsonContent content){
        jsonContents.add(content);
        return this;
    }

    public void setJsonContents(List<JsonContent> jsonContents) {
        this.jsonContents = jsonContents;
    }

    public String serialize(){
        StringBuilder data= new StringBuilder("{");
        for (int i = 0; i <jsonContents.size(); i++) {
            data.append(jsonContents.get(i).serialize(this));
            if(!(jsonContents.size()-1==i)){
                data.append(",");
            }
        }
        return data.append("}").toString();
    }

    public String serializeAsContent(){
        StringBuilder data= new StringBuilder();
        for (int i = 0; i <jsonContents.size(); i++) {
            data.append(jsonContents.get(i).serialize(this));
            if(!(jsonContents.size()-1==i)){
                data.append(",");
            }
        }
        return data.toString();
    }

    public JsonContent toContent(String field_name){
        return new JsonContent(field_name,this);
    }

    @Nullable
    public JsonContent getContent(String field_name){
        for(JsonContent content:jsonContents){
            if(content.field_name.equals(field_name)){
                return content;
            }
        }
        return null;
    }
}
