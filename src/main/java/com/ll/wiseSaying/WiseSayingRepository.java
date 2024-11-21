package com.ll.wiseSaying;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/*

  - 데이터 저장소 관리
  - 파일 기반 데이터베이스로 데이터를 저장/수정/삭제/조회 (.json)

 */
public class WiseSayingRepository {
    private final String DB_FILE = "db/data.json"; // 데이터 파일 경로
    private final Gson gson;
    private List<WiseSaying> wiseSayings;
    private int lastId;

    public WiseSayingRepository() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        wiseSayings = load();
        lastId = wiseSayings.stream().mapToInt(WiseSaying::getId).max().orElse(0);
    }

    // 명언 저장
    public int save(WiseSaying wiseSaying) {
        wiseSaying.setId(++lastId);
        wiseSayings.add(wiseSaying);
        saveToFile();
        return wiseSaying.getId();
    }

    // 모든 명언 조회
    public List<WiseSaying> findAll() {
        return wiseSayings;
    }

    // 명언 삭제 id 를 기준
    public boolean deleteById(int id) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSayings.remove(wiseSaying);
            saveToFile();
            return true;
        }
        return false;
    }

    // 명언 수정 id 를 기준
    public boolean updateById(int id, String content, String author) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSaying.setContent(content);
            wiseSaying.setAuthor(author);
            saveToFile();
            return true;
        }
        return false;
    }

    // 명언 조회  id 를 기준
    private WiseSaying findById(int id) {
        return wiseSayings.stream().filter(w -> w.getId() == id).findFirst().orElse(null);
    }

    // 파일에 명언 데이터 저장
    private void saveToFile() {
        try (Writer writer = new FileWriter(DB_FILE)) {
            gson.toJson(wiseSayings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 명언 데이터 로드
    private List<WiseSaying> load() {
        File file = new File(DB_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            WiseSaying[] array = gson.fromJson(reader, WiseSaying[].class);
            return new ArrayList<>(List.of(array));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
