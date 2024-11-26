package org.example.mvc.service;

import org.example.entity.WiseSaying;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WiseSayingService {

    void initRepository();

    WiseSaying register(String content, String author) throws IOException;

    boolean delete(int id);

    boolean deleteAll();

    void update(int id, String content, String author);

    void build();

    Optional<WiseSaying> findWiseSayingById(int id);

    List<WiseSaying> search(String command);
}
