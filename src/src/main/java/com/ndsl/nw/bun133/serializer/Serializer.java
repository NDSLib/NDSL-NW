package com.ndsl.nw.bun133.serializer;

import com.ndsl.nw.bun133.json.Json;

import java.lang.annotation.Annotation;

@Deprecated
public class Serializer<T> {
    public T instance;
    public Serializer(T t){
        this.instance=t;
    }

    public Json serialize(){
        Field annotation = instance.getClass().getAnnotation(Field.class);
        java.lang.reflect.Field[] fields = instance.getClass().getFields();
        System.out.println("FieldLength:"+fields.length);
        for(java.lang.reflect.Field field:fields){
            System.out.println("FieldName:"+field.getName());
            for(Annotation a:field.getAnnotations()){
                if (a.equals(annotation)) System.out.println("Anotaed.");
                System.out.println("FieldAnnotations:"+a.toString());
            }
        }
        return null;
    }
}
