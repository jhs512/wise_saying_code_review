package org.example.entity;

import java.util.List;

public class Page<T> {
    private final int pageNum;
    private final int size;
    private final List<T> page;

    public Page(Pageable pageable, List<T> list){
        this.pageNum = pageable.getPageNum();
        this.size = pageable.getSize();
        int totalElementSize = list.size();
        int startIndex = pageNum * size;

        if(startIndex >= totalElementSize){
            throw new IllegalArgumentException();
        }

        int endIndex = Math.min(startIndex + size, totalElementSize);
        page = list.subList(startIndex, endIndex);
    }

    public int getPageNum(){
        return pageNum;
    }

    public int getSize(){
        return  size;
    }

    public List<T> getPage(){
        return page;
    }
}
