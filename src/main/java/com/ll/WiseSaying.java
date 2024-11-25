package com.ll;

class WiseSaying {
    int id;
    String content;
    String author;
    WiseSaying(int id, String content, String author){
        this.id = id;
        this.content = content;
        this.author = author;
    }

    String getContent(){
        return this.content;
    }

    String getAuthor(){
        return this.author;
    }
}
