package com.ll.repository;

import com.ll.domain.WiseSaying;

import java.util.*;

public class MemorySayingRepository implements WiseSayingRepository {

    private final Map<Long, WiseSaying> wiseSayingMap = new HashMap<>();
    private long uniqueNum = 1;

    @Override
    public long addWiseSaying(WiseSaying wiseSaying) {
        wiseSaying.setId(uniqueNum);
        wiseSayingMap.put(uniqueNum, wiseSaying);

        return uniqueNum++;
    }

    @Override
    public Optional<WiseSaying> searchById(long id) {
        return Optional.ofNullable(wiseSayingMap.get(id));
    }

    @Override
    public List<WiseSaying> findAll() {
        return new ArrayList<>(wiseSayingMap.values());
    }

    @Override
    public boolean removeWiseSaying(long wiseSayingId) {
        if(wiseSayingMap.get(wiseSayingId) != null){
            wiseSayingMap.remove(wiseSayingId);
            return true;
        }
        return false;
    }

    @Override
    public void updateWiseSaying(WiseSaying wiseSaying) {
        if(wiseSayingMap.get(wiseSaying.getId()) == null)
            return;

        wiseSayingMap.put(wiseSaying.getId(), wiseSaying);
    }

    @Override
    public List<WiseSaying> searchByAuthor(String author) {
        return findAll().stream()
                .filter(ws -> ws.getAuthor().contains(author))
                .toList();
    }

    @Override
    public void clear() {
        wiseSayingMap.clear();
    }

    @Override
    public List<WiseSaying> searchByContent(String content) {
        return findAll().stream()
                .filter(ws -> ws.getContent().contains(content))
                .toList();
    }
}
