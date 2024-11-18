package org.example;

class Wise {
    int index;
    String author;
    String content;

    public Wise(int index, String author, String content) {
        this.index = index;
        this.author = author;
        this.content = content;
    }

    @Override
    public String toString() {
        return index + " / " + author + " / " + content;
    }
}