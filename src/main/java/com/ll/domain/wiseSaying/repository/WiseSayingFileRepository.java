package com.ll.domain.wiseSaying.repository;

import com.ll.domain.wiseSaying.config.AppConfig;
import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.global.util.Util;
import com.ll.global.util.Util.File;
import com.ll.global.util.Util.Json;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository {
    private static final String DIR_PATH = "db/wiseSaying/";

    public WiseSaying saveFile(WiseSaying wiseSaying) {
        boolean isNew = wiseSaying.isNew();

        if (isNew) {
            wiseSaying.setId(getLastId() + 1);
        }

        String json = wiseSaying.toJson();
        Util.File.set(getFilePath(wiseSaying.getId()), json);

        if (isNew) {
            setLastId(wiseSaying.getId());
        }

        return wiseSaying;
    }

    public boolean delete(long id) {
        return Util.File.delete(getFilePath(id));
    }

    public List<WiseSaying> findAll() {
        return Util.File.fileRegStream(getDirPath(), "\\d+\\.json")
                .map(path -> Util.File.get(path.toString()))
                .map(Json::toMap)
                .map(WiseSaying::new)
                .sorted(Comparator.comparingLong(WiseSaying::getId).reversed())
                .toList();
    }

    public Optional<WiseSaying> findById(long id) {
        String path = getFilePath(id);

        if (!Util.File.exists(path)) {
            return Optional.empty();
        }

        String json = File.get(path);

        return Optional.of(new WiseSaying(json));
    }

    public List<WiseSaying> findBySearch(String keywordType, String keyword) {
        return findAll().stream()
                .filter(ws -> {
                    if (keywordType.equals("author")) {
                        return ws.getAuthor().contains(keyword);
                    } else if (keywordType.equals("content")) {
                        return ws.getContent().contains(keyword);
                    } else if (!keywordType.isEmpty()) {
                        return false;
                    }

                    return ws.getAuthor().contains(keyword) || ws.getContent().contains(keyword);
                }).toList();
    }

    public void build() {
        List<Map<String, Object>> list = findAll().reversed().stream()
                .map(WiseSaying::toMap)
                .map(Json::toJson)
                .map(Json::toMap)
                .toList();

        String json = Util.Json.build(list);
        Util.File.set(getBuildPath(), json);
    }

    public void setLastId(long id) {
        Util.File.set(getLastIdPath(), String.valueOf(id));
    }

    public long getLastId() {
        String content = Util.File.get(getLastIdPath());
        return content.isEmpty() ? 0 : Long.parseLong(content);
    }

    public String getDirPath() {
        return AppConfig.getDir();
    }

    public String getFilePath(long i) {
        return getDirPath() + i + ".json";
    }

    public String getLastIdPath() {
        return getDirPath() + "lastId.txt";
    }

    public String getBuildPath() {
        return getDirPath() + "data.json";
    }
}
