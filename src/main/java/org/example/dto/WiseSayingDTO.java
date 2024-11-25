package org.example.dto;

import org.example.entity.WiseSaying;

public class WiseSayingDTO {
    private int id;
    private String content;
    private String author;


    public WiseSayingDTO(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }


    public WiseSayingDTO(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public WiseSayingDTO(WiseSaying wiseSaying) {
        this.id = wiseSaying.getId();
        this.content = wiseSaying.getContent();
        this.author = wiseSaying.getAuthor();
    }

    public WiseSayingDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
