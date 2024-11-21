package org.example.object;

import java.io.Serializable;

public class WiseSaying implements Serializable {
    private int id = 0;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author){
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public void setId(int id) { this.id = id;}

    public void setContent(String content){
        this.content = content;
    }

    public int getId(){return id;}

    public String getContent(){
        return content;
    }

    public String getAuthor(){
        return author;
    }
}
