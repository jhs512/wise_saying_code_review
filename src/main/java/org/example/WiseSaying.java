package org.example;

public class WiseSaying {
    private final int Id;
    private String Saying;
    private String Writter;
    public WiseSaying(int id, String wiseSaying, String writter) {
        this.Id = id;
        this.Saying = wiseSaying;
        this.Writter = writter;
    }

    public int getId() {
        return Id;
    }

    public String getSaying() {
        return Saying;
    }

    public void setSaying(String saying) {
        Saying = saying;
    }

    public String getWritter() {
        return Writter;
    }

    public void setWritter(String writter) {
        Writter = writter;
    }
}
