package org.example.mvc;

import java.util.*;

class Repository {

    private static final HashMap<Integer, WiseSaying> store = new HashMap<>();

    private int id = 0;

    public static HashMap<Integer, WiseSaying> getInstance() {
        return store;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public List<Integer> getKeys() {
        return new ArrayList<>(store.keySet());
    }
    // 서버를 껐다가 킬 때 리포지토리 저장 용도
    public WiseSaying save(int id, WiseSaying wiseSaying) {
        store.put(id, wiseSaying);
        return wiseSaying;
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        wiseSaying.setId(++id);
        store.put(wiseSaying.getId(), wiseSaying);
        return wiseSaying;
    }

    public Optional<WiseSaying> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<WiseSaying> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(int id) {
        store.remove(id);
    }

    public void updateById(int id, WiseSaying wiseSaying) {
        store.put(id, wiseSaying);
    }
}
