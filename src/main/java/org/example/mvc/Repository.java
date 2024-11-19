package org.example.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

class Repository {

    private static final TreeMap<Integer, WiseSaying> store = new TreeMap<>();
    private static int id = 1;

    public static TreeMap<Integer, WiseSaying> getInstance() {
        return store;
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        wiseSaying.setId(id++);
        store.put(wiseSaying.getId(), wiseSaying);
        return wiseSaying;
    }

    public Optional<WiseSaying> findById(int id) {
        return Optional.of(store.get(id));
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
