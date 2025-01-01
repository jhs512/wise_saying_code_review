package com.ll.domain.wiseSaying.service;

import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.domain.wiseSaying.repository.WiseSayingFileRepository;
import java.util.List;
import java.util.Optional;

public class WiseSayingService {
    private final WiseSayingFileRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingFileRepository();
    }

    public WiseSaying register(String content, String author) {
        return this.wiseSayingRepository.saveFile(new WiseSaying(0, content, author));
    }

    public List<WiseSaying> getWiseSayings() {
        return this.wiseSayingRepository.findAll();
    }

    public WiseSaying modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        return this.wiseSayingRepository.saveFile(wiseSaying);
    }

    public boolean delete(long id) {
        return this.wiseSayingRepository.delete(id);
    }

    public void build() {
        this.wiseSayingRepository.build();
    }

    public Optional<WiseSaying> getWiseSaying(long id) {
        return this.wiseSayingRepository.findById(id);
    }
}
