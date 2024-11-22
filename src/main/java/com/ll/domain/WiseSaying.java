package com.ll.domain;

import java.util.Objects;

public class WiseSaying {

    private Long id;
    private String content;
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WiseSaying quote = (WiseSaying) o;
        return Objects.equals(getId(), quote.getId()) && Objects.equals(getContent(), quote.getContent()) && Objects.equals(getAuthor(), quote.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getAuthor());
    }
}
