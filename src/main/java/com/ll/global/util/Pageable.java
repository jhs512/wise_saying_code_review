package com.ll.global.util;

import java.util.List;

public class Pageable<T> {
    private final int totalElements;
    private final int totalPages;
    private final int itemsPerPage;
    private final int page;
    private final String keywordType;
    private final String keyword;
    private final List<T> content;

    public Pageable(int totalElements, int itemsPerPage, int page, String keywordType, String keyword,
                    List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / itemsPerPage);
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.keywordType = keywordType;
        this.keyword = keyword;
        this.content = content;
    }

    public Pageable(int totalElements, int itemsPerPage, int page, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / itemsPerPage);
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.keywordType = "";
        this.keyword = "";
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getPage() {
        return page;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public String getKeyword() {
        return keyword;
    }
}
