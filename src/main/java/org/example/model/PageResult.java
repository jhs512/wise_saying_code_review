package org.example.model;

import java.util.List;

public class PageResult {

    private List<String> items;
    private int totalPage;
    private String currenPage;

    private PageResult(List<String> items, int totalPage, String currenPage) {
        this.items = items;
        this.totalPage = totalPage;
        this.currenPage = currenPage;
    }

    public static PageResult of(List<String> items, int totalPage, String currenPage) {
        return new PageResult(items, totalPage, currenPage);
    }

    public List<String> getItems() {
        return items;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public String getCurrenPage() {
        return currenPage;
    }
}
