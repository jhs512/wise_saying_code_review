package org.example.model;

public class WiseSayingItem {

    private long id;
    private String author;
    private String WiseSaying;

    public WiseSayingItem(long id, String author, String wiseSaying) {
        this.id = id;
        this.author = author;
        WiseSaying = wiseSaying;
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

    public String getWiseSaying() {
        return WiseSaying;
    }

    public void setWiseSaying(String wiseSaying) {
        WiseSaying = wiseSaying;
    }
}
