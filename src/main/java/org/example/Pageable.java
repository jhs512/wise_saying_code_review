package org.example;

public class Pageable {
    private final int pageNumber; // 현재 페이지 번호
    private final int pageSize = 5;    // 페이지 크기
    private int maxPageNumber;

    public Pageable(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setMaxPageNumber(int maxPageNumber) {
        this.maxPageNumber = maxPageNumber;
    }

    public int getMaxPageNumber() {
        return maxPageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (pageNumber-1) * pageSize;
    }
}
