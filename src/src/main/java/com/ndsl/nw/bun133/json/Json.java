package com.ndsl.nw.bun133.json;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final Pattern JsonPatten=Pattern.compile("\\{(\\{\"[^{},]+\":[^{},]+},)*(\\{\"[^{},]+\":[^{},]+})}");
    public static synchronized Json build(String s){
        System.out.println("JsonBuilding");
        Matcher json_matcher = JsonPatten.matcher(s);
        if(json_matcher.find()){
            Json json=new Json();
            String withOut_Json_bracket = s.substring(1, s.length()-1);
            System.out.println("withOut_Json_bracket:"+withOut_Json_bracket);
            String[] contents=withOut_Json_bracket.split(",");
            System.out.println("Contents:"+ Arrays.toString(contents));
            for(String content:contents){
                json.add(JsonContent.build(content));
            }
            return json;
        }else{
            System.out.println("[Json]NotMatched!");
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder s= new StringBuilder();
        for(JsonContent content:jsonContents){
            s.append(content.field_name).append(":").append(content.getAsObject().toString());
        }
        return s.toString();
    }
}
