package org.example.service;

import org.example.model.WiseSayingItem;
import org.example.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public long register (String wiseSaying, String author) {
        return wiseSayingRepository.save(wiseSaying, author);
    }


    public List<String> search() {
        return wiseSayingRepository.findAll();
    }


    public String remove(String id) {
        return wiseSayingRepository.remove(id);
    }

    public WiseSayingItem findById(String id) {
        return wiseSayingRepository.findById(id);
    }

    public void updateAuthor(String id, String author) {
        wiseSayingRepository.updateAuthor(id, author);
    }

    public void updateWiseSaying(String id, String wiseSaying) {
        wiseSayingRepository.updateWiseSaying(id, wiseSaying);
    }

    // 모든 명언을 모아 data 파일 생성
    public void build() {
        wiseSayingRepository.saveAllWiseSaying();
    }
}
