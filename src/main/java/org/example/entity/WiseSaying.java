package org.example.entity;

public class WiseSaying {
    private int id;
    private String author;
    private String content;

    public WiseSaying(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + content;
    }


    public String generateJson(String indent) {
        return  indent + "{\n" +
                indent + "  \"id\": " + id + " ,\n" +
                indent + "  \"content\": \"" + content + "\",\n" +
                indent + "  \"author\": \"" + author + "\"\n" +
                indent + "}";
    }
}
