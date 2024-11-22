package org.example.entity;

public class Pageable {
    private final int pageNum;
    private final int size;
    private final int DEFAULT_SIZE = 5;

    public Pageable(Integer pageNum, Integer size){
        this.pageNum = pageNum == null ? 0 : pageNum - 1;
        this.size = size == null ? DEFAULT_SIZE : size;
    }

    public Pageable(int pageNum){
        this.pageNum = pageNum - 1;
        this.size = DEFAULT_SIZE;
    }

    public int getPageNum(){
        return pageNum;
    }
    public int getSize(){
        return size;
    }
}
