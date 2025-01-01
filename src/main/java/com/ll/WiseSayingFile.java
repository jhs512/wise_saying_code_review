package com.ll;

import com.ll.global.util.Util;
import com.ll.global.util.Util.File;
import com.ll.global.util.Util.Json;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFile {
    private static final String DIR_PATH = "db/wiseSaying/";

    public static void saveFile(WiseSaying wiseSaying) {
        boolean isNew = wiseSaying.isNew();

        if (isNew) {
            wiseSaying.setId(getLastId() + 1);
        }

        String json = wiseSaying.toJson();
        Util.File.set(getFilePath(wiseSaying.getId()), json);

        if (isNew) {
            setLastId(wiseSaying.getId());
        }
    }

    public static boolean delete(long id) {
        return Util.File.delete(getFilePath(id));
    }

    public static List<WiseSaying> findAll() {
        return Util.File.fileRegStream(getDirPath(), "\\d+\\.json")
                .map(path -> Util.File.get(path.toString()))
                .map(Json::toMap)
                .map(WiseSaying::new)
                .sorted(Comparator.comparingLong(WiseSaying::getId).reversed())
                .toList();
    }

    public static Optional<WiseSaying> findById(long id) {
        String path = getFilePath(id);

        if (!Util.File.exists(path)) {
            return Optional.empty();
        }

        String json = File.get(path);

        return Optional.of(new WiseSaying(json));
    }

    public static void build() {
        List<Map<String, Object>> list = findAll().reversed().stream()
                .map(WiseSaying::toMap)
                .map(Json::toJson)
                .map(Json::toMap)
                .toList();

        String json = Util.Json.build(list);
        Util.File.set(getBuildPath(), json);
    }

    public static void setLastId(long id) {
        Util.File.set(getLastIdPath(), String.valueOf(id));
    }

    public static long getLastId() {
        String content = Util.File.get(getLastIdPath());
        return content.isEmpty() ? 0 : Long.parseLong(content);
    }

    public static String getDirPath() {
        return DIR_PATH;
    }

    public static String getFilePath(long i) {
        return getDirPath() + i + ".json";
    }

    public static String getLastIdPath() {
        return getDirPath() + "lastId.txt";
    }

    public static String getBuildPath() {
        return getDirPath() + "data.json";
    }
}
