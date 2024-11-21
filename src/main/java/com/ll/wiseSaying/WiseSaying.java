package com.ll.wiseSaying;


/*

  - 명언 데이터 객체
  - 번호, 명언 내용, 작가 정보를 포함


 */
public class WiseSaying {
    private int id; //등록된 번호
    private String content;// 명언
    private String author; // 작가

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}