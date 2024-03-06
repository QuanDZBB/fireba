package com.quandhph31964.myapp123;

import java.util.HashMap;

public class ToDo {
    private String id,title,content;

    public ToDo(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //ham convert dữ liệu sang dang hashmap: de luu dữ liệu vào fire base
    public HashMap<String,Object>convertToHashMap(){
        HashMap<String,Object>h=new HashMap<>();
        h.put("id",id);
        h.put("title",title);
        h.put("content",content);
        return h;
    }

}
