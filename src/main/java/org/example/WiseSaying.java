package org.example;

public class WiseSaying {
    public final int id;
    public String saying;
    public String writer;

    public WiseSaying(int id, String saying, String writer) {
        this.id = id;
        this.saying = saying;
        this.writer = writer;
    }
    public WiseSaying(String saying, String writer) {
        this.id = generateId();
        this.saying = saying;
        this.writer = writer;
    }

    public int getId() {
        return id;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    private static int generateId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}
