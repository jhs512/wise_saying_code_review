package org.example;

class Wise {
    int index;
    String author;
    String wise;

    public Wise(int index, String author, String wise) {
        this.index = index;
        this.author = author;
        this.wise = wise;
    }

    @Override
    public String toString() {
        return index + " / " + author + " / " + wise;
    }
}