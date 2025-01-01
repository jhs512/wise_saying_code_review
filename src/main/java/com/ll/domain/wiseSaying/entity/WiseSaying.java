package com.ll.domain.wiseSaying.entity;

import com.ll.global.util.Util;
import java.util.LinkedHashMap;
import java.util.Map;

public class WiseSaying {
    private long id;
    private String author;
    private String content;

    public WiseSaying(long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public WiseSaying(Map<String, Object> map) {
        this.id = (long) map.get("id");
        this.author = String.valueOf(map.get("author"));
        this.content = String.valueOf(map.get("content"));
    }

    public WiseSaying(String json) {
        this(Util.Json.toMap(json));
    }

    public boolean isNew() {
        return id == 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("id", id);
        map.put("content", content);
        map.put("author", author);

        return map;
    }

    public String toJson() {
        return Util.Json.toJson(toMap());
    }

    public String toString() {
        return id + " / " + author + " / " + content;
    }

    public long getId() {
        return id;
    }

    public long setId(long id) {
        return this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
