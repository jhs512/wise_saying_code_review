package com.ll;

import java.lang.reflect.Method;



public class Quote {
    int id;
    String content;
    String author;


    public Quote() {}

    //getter
    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public Object getByName(String keywordType) {

        String getterName = "get" + keywordType.substring(0,1).toUpperCase() + keywordType.substring(1);
        Object res;
        try {
            Method method = Quote.class.getMethod(getterName);
            res = method.invoke(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    //setter
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
