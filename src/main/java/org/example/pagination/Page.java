package org.example.pagination;

import java.util.List;

public class Page<T> {
    private final int pageNum;
    private final int size;
    private final List<T> page;
    private final int totalPageNum;

    public Page(Pageable pageable, List<T> list){
        this.pageNum = pageable.getPageNum();
        this.size = pageable.getSize();
        int totalElementSize = list.size();
        int startIndex = pageNum * size;

        if(startIndex > totalElementSize){
            throw new IllegalArgumentException();
        }

        int endIndex = Math.min(startIndex + size, totalElementSize);
        page = list.subList(startIndex, endIndex);
        totalPageNum = totalElementSize / size + (totalElementSize% size == 0 ? 0 : 1);
    }

    public int getPageNum(){
        return pageNum + 1;
    }

    public int getSize(){
        return  size;
    }

    public int getTotalPageNum(){
        return totalPageNum;
    }

    public List<T> getPage(){
        return page;
    }
}
