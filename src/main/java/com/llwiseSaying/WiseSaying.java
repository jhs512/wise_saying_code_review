package com.llwiseSaying;

public class WiseSaying {

    final int id;
    String content;
    String writer;
    public WiseSaying(int id,String line,String writer) {
        this.content=line;
        this.writer=writer;
        this.id=id;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public void setWriter(String writer) {
        this.writer=writer;
    }

    public int getId(){
        return this.id;
    }

    public String getContent(){
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String toString() {
        String printWiseSaying=id + " / " + writer + " / " + content;
        return printWiseSaying;
    }
}
