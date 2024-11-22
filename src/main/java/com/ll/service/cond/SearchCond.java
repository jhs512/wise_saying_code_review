package com.ll.service.cond;

public class SearchCond {
    private String type;
    private String keyword;

    public SearchCond(String type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }
}
