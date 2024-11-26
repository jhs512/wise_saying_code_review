package org.example.mvc.repository;

import org.example.entity.WiseSaying;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {

    int getId();

    void setId(int id);

    WiseSaying save(WiseSaying wiseSaying);

    void saveById(int id, WiseSaying wiseSaying);

    Optional<WiseSaying> findById(int id);

    List<WiseSaying> findAll();

    void deleteById(int id);

    void updateById(int id, WiseSaying wiseSaying);

    void storeJson(WiseSaying wiseSaying);

    void storeLastId(int id);

    void build();

    String getStringFromFile(String fileName);

    void initRepository();

    void processFile(Path filePath);

    void createDirectory();

    void deleteJsonById(int id);

    void deleteBuildFile();

    void deleteLastIdFile();
}
