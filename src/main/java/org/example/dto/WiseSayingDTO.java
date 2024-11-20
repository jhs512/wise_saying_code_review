package org.example.dto;

public class WiseSayingDTO {
    private int id;
    private String content;
    private String author;


    public WiseSayingDTO(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public WiseSayingDTO(String content, String author) {
        this.content = content;
        this.author = author;
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
