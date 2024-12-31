package com.ll;

public class WiseSaying {
    private long id;
    private String author;
    private String content;

    public WiseSaying(long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String toString() {
        return id + " / " + author + " / " + content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
