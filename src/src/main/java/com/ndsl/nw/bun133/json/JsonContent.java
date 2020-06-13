package com.ndsl.nw.bun133.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonContent {
    public JsonContentType type;
    public String field_name;

    public String data;
    public JsonContent(String field_name,String data){
        type=JsonContentType.Type_String;
        this.field_name=field_name;
        this.data=data;
    }

    public int i_data;
    public JsonContent(String field_name,int data){
        type=JsonContentType.Type_int;
        this.field_name=field_name;
        this.i_data=data;
    }

    public Json j_data;
    public JsonContent(String field_name,Json content){
        type=JsonContentType.Type_JsonContent;
        this.field_name=field_name;
        this.j_data=content;
    }

    public String serialize(Json json){

        switch (type){
            case Type_int:
                return "{"+field_name+":"+i_data+"}";
            case Type_String:
                return "{"+field_name+":"+data+"}";
            case Type_JsonContent:
                if(json==j_data) {
                    System.out.println("[JsonContent]DO NOT Json.add(Json.toContent())");
                    return null;
                }
                return "{"+field_name+":"+j_data.serializeAsContent()+"}";
        }
        return null;
    }
    public static final Pattern JsonPatten =Pattern.compile("^\\{.+:.+}$");

    /**
     * @param {"Key":"Value"}
     * @return JsonContent(Key,Value);
     */
    public static JsonContent build(String s){
        Matcher JsonMatcher=JsonPatten.matcher(s);
        if (JsonMatcher.find()){
            System.out.println("[JsonContent]Matched!");
            switch (getType(getData(s))){
                case Type_JsonContent:
                    switch (getValueType(getValue(s))){
                        case Type_int:
                            return new JsonContent(getData(s),Integer.parseInt(getValue(s)));
                        case Type_String:
                            return new JsonContent(getData(s),getValue(s));
                        case Type_JsonContent:
                            return new JsonContent(getData(s),getAsJson(getValue(s)));
                    }
                case Type_String:
                    switch (getValueType(getValue(s))){
                        case Type_int:
                            return new JsonContent(getData(s),Integer.parseInt(getValue(s)));
                        case Type_String:
                            return new JsonContent(getData(s),getValue(s));
                        case Type_JsonContent:
                            return new JsonContent(getData(s),getAsJson(getValue(s)));
                    }
                case Type_int:
                    switch (getValueType(getValue(s))){
                        case Type_int:
                            return new JsonContent(getData(s),Integer.parseInt(getValue(s)));
                        case Type_String:
                            return new JsonContent(getData(s),getValue(s));
                        case Type_JsonContent:
                            return new JsonContent(getData(s),getAsJson(getValue(s)));
                    }
            }
        }else{
            System.out.println("[JsonContent]Not Matched!");
        }
        return null;
    }

    private static String getData(String s) {
        if(s.contains(":")){
            String substring = s.substring(s.indexOf("{") + 1, s.indexOf(":"));
//            System.out.println("Data:"+ substring);
            return substring;
        }else{
            System.out.println("[JsonContent]getData:NotString Contain \":\"");
            return null;
        }
    }

    private static String getValue(@NotNull String s){
        if(s.contains(":")){
            String substring = s.substring(s.indexOf(":") + 1, s.lastIndexOf("}"));
//            System.out.println("Value:"+ substring);;
            return substring;
        }else{
            System.out.println("[JsonContent]getData:NotString Contain \":\"");
            return null;
        }
    }

    public static final Pattern Type_String_Patten=Pattern.compile("^\".+\"$");
    public static final Pattern Type_Int_Patten=Pattern.compile("[0-9]+");
    public static final Pattern Type_Json_Patten=JsonPatten;


    @Nullable
    private static JsonContentType getType(String s) {
//        System.out.println("GetType:"+s);
        Matcher String_Matcher = Type_String_Patten.matcher(s);
        Matcher Int_Matcher = Type_Int_Patten.matcher(s);
        Matcher Json_Matcher = Type_Json_Patten.matcher(s);
        if(String_Matcher.find()) return JsonContentType.Type_String;
        if(Int_Matcher.find()) return JsonContentType.Type_int;
        if(Json_Matcher.find()) return JsonContentType.Type_JsonContent;
        System.out.println("[ERROR][JsonContent]NotMatched!");
        return null;
    }

    public static final Pattern Type_String_Value_Patten=Pattern.compile("^\"\"$");

    private static JsonContentType getValueType(String s){
        Matcher string_value_matcher = Type_String_Value_Patten.matcher(s);
        if(string_value_matcher.find()) return JsonContentType.Type_String;
        return getType(s);
    }

    @Nullable
    private static Json getAsJson(@Nullable String s){
        Matcher matcher=Type_Json_Patten.matcher(s);
        if(matcher.find()){
            Json json=new Json();
            json.add(JsonContent.build(s));
            return json;
        }else{
            System.out.println("[JsonContent]getAsJson:Is not Json");
            System.out.println("Data:"+s);
        }
        return null;
    }

    public Object getAsObject(){
        switch (this.type){
            case Type_JsonContent:
                return j_data;
            case Type_String:
                return data;
            case Type_int:
                return i_data;
        }
        return null;
    }

    @Override
    public String toString() {
        return "{"+this.field_name+":"+getAsObject().toString()+"}";
    }
}
