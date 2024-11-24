package org.example.dto;

public class Pageable {
    private int totalDataCount;
    private int totalPageCount;
    private int page = 1;
    private final int limit = 5;
    private int offset=0;


    public void setTotalDataCount(int totalDataCount) {
        this.totalDataCount = totalDataCount;
        this.totalPageCount = (totalDataCount-1)/5 + 1;
    }

    public void setPage(int page) {
        this.page = page;
        this.offset = (page-1)*limit + offset;
    }


    public Pageable() {
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }


}
