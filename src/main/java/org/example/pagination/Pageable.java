package org.example.pagination;

public class Pageable {
    private final int pageNum;
    private final int size;
    private static final int DEFAULT_SIZE = 5;


    public Pageable(Integer pageNum, Integer size){
        if (pageNum != null && pageNum <= 0) {
            throw new IllegalArgumentException("pageNum must be greater than 0 or null.");
        }
        if (size != null && size <= 0) {
            throw new IllegalArgumentException("size must be greater than 0 or null.");
        }
        this.pageNum = pageNum == null ? 0 : pageNum - 1;
        this.size = size == null ? DEFAULT_SIZE : size;
    }

    public Pageable(Integer pageNum){
        this(pageNum, DEFAULT_SIZE);
    }

    public int getPageNum(){
        return pageNum;
    }
    public int getSize(){
        return size;
    }
}
