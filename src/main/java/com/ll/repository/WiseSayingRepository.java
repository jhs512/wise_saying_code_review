package com.ll.repository;

import com.ll.domain.WiseSaying;

import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {
    long addWiseSaying(WiseSaying wiseSaying);

    boolean removeWiseSaying(long wiseSayingId);
    void updateWiseSaying(WiseSaying quote);

    Optional<WiseSaying> searchById(long id);
    List<WiseSaying> findAll();
    List<WiseSaying> searchByAuthor(String author);
    List<WiseSaying> searchByContent(String content);

    void clear();
}
