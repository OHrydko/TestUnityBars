package com.example.test.model;

public class JsonModel {
    private String name;
    private String type;
    private String content;


    public JsonModel(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }


    public String getContent() {
        return content;
    }

}
