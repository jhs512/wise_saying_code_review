package com.ll.wiseSaying;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    public WiseSaying create(String content, String author) {
        return wiseSayingRepository.save(content, author);
    }

    //목록 수정중
    public List<WiseSaying> getList(){
        return wiseSayingRepository.findAll();
    }

    public List<WiseSaying> getListByKey(String keywordType, String keyword) {
        return wiseSayingRepository.findByKey(keywordType, keyword);
    }

    public WiseSaying get(int id) {
        return wiseSayingRepository.findById(id);
    }

    public boolean remove(int id) {
        return wiseSayingRepository.remove(id);
    }

    public boolean modify(int id, String content, String author) {
        WiseSaying wiseSaying = wiseSayingRepository.findById(id);
        if (wiseSaying == null) {
            return false;
        }
        wiseSayingRepository.modify(id, content, author);
        return true;
    }

    public void build(){
        wiseSayingRepository.build();
    }

}