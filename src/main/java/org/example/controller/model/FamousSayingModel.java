package org.example.controller.model;

public class FamousSayingModel {
    private int id;
    private String contents;
    private String author;

    public FamousSayingModel() {
    }

    public FamousSayingModel(int id, String contents, String author) {
        this.id = id;
        this.contents = contents;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"id\":").append(id);
        sb.append(", \"content\": \"").append(contents).append('"');
        sb.append(", \"author\": \"").append(author).append('"');
        sb.append('}');
        return sb.toString();
    }
}
