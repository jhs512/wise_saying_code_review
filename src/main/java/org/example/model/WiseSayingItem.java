package org.example.model;

public class WiseSayingItem {

    private long id;
    private String author;
    private String wiseSaying;

    private WiseSayingItem(long id, String author, String wiseSaying) {
        if (author.matches(".*[^a-zA-Z0-9\\s가-힣].*")) {
            throw new IllegalArgumentException("특수문자는 안됩니다.");
        }

        if (wiseSaying.matches(".*[^a-zA-Z0-9\\s가-힣].*")) {
            throw new IllegalArgumentException("특수문자는 안됩니다.");
        }
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
