package com.ll.wiseSaying;


/*

  - 명언 데이터 객체
  - 번호, 명언 내용, 작가 정보를 포함


 */
public class WiseSaying {
    private int id; //등록된 번호
    private String content;// 명언
    private String author; // 작가

    // 추가된 생성자: id, content, author 모두 설정  원래 content, author 만 있음.
    // gson 라이브러리 사용시 content 와 author 만 있었음

    // Gson 라이브러리 사용하지 않을 경우 json 으로 저장하려고  데이터 직접 파싱 필요로 인하여 객체로 생성

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

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