package org.example.model;

public class WiseSayingItem {

    private long id;
    private String author;
    private String wiseSaying;

    private WiseSayingItem(long id, String author, String wiseSaying) {
        this.id = id;
        this.author = author;
        this.wiseSaying = wiseSaying;
    }

    public static WiseSayingItem of(long id, String author, String wiseSaying) {
        return new WiseSayingItem(id, author, wiseSaying);
    }

    public void changeAuthor(String author) {
        this.author = author;
    }

    public void changeWiseSaying(String wiseSaying) {
        this.wiseSaying = wiseSaying;
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getWiseSaying() {
        return wiseSaying;
    }

}
