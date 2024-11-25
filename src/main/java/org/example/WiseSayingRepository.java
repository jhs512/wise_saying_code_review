package org.example;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingRepository {
    private static final String STORE_DIR = "./db/wiseSaying/";
    private static final String LAST_ID_FILE = STORE_DIR + "lastId.txt";

    public WiseSayingRepository() {
        try {
            Files.createDirectories(Paths.get(STORE_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int save(WiseSaying wiseSaying) {
        JSONObject obj = new JSONObject();
        obj.put("id", wiseSaying.getId());
        obj.put("명언", wiseSaying.getSaying());
        obj.put("작가", wiseSaying.getWriter());
        try (FileWriter file = new FileWriter(STORE_DIR + wiseSaying.getId() + ".json")) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveLastId(wiseSaying.getId());
        return wiseSaying.getId();
    }

    public boolean deleteById(int id) {
        try {
            return Files.deleteIfExists(Paths.get(STORE_DIR + id + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveLastId(int lastId) {
        try (FileWriter writer = new FileWriter(LAST_ID_FILE)) {
            writer.write(String.valueOf(lastId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateById(int id, String saying, String writer) {
        WiseSaying wiseSaying = new WiseSaying(id, saying, writer);
        save(wiseSaying);
        return true;
    }

    public boolean buildDataJson() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORE_DIR + "data.json"))) {
            List<WiseSaying> wiseSayings = loadAllData();
            writer.write("[\n");
            for (int i = 0; i < wiseSayings.size(); i++) {
                WiseSaying ws = wiseSayings.get(i);
                JSONObject obj = new JSONObject();
                obj.put("id", ws.getId());
                obj.put("명언", ws.getSaying());
                obj.put("작가", ws.getWriter());
                writer.write(obj.toJSONString());

                if (i < wiseSayings.size() - 1)
                    writer.write(",\n");
            }
            writer.write("\n]");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<WiseSaying> findAll() {
        List<WiseSaying> allData = loadAllData(); // 기존 데이터를 로드하는 메서드 호출
        return allData.stream()
                .sorted((a, b) -> Integer.compare(b.getId(), a.getId())) // 최신 데이터가 먼저 오도록 정렬
                .collect(Collectors.toList());
    }

    private static final int ITEMS_PER_PAGE = 5;

    public List<WiseSaying> findAllPaged(int page){
        List<WiseSaying> sortedData = findAll();
        int totalItems = sortedData.size();
        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start+ITEMS_PER_PAGE,totalItems);

        if (start >= totalItems || start<0){
            return List.of();
        }

        return sortedData.subList(start, end);
    }
    public int getTotalPages() {
        int totalItems = findAll().size();
        return (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
    }

    private List<WiseSaying> loadAllData() {
        List<WiseSaying> list = new ArrayList<>();
        try {
            Files.list(Paths.get(STORE_DIR))
                    .filter(path -> path.toString().endsWith(".json"))
                    .filter(path -> !path.getFileName().toString().equals("data.json"))
                    .forEach(path -> {
                        try (Reader reader = new FileReader(path.toString())) {
                            JSONObject obj = (JSONObject) org.json.simple.JSONValue.parse(reader);
                            if (obj == null) {
                                System.err.println("Failed to parse JSON file: " + path);
                                return;
                            }
                            list.add(new WiseSaying(
                                    ((Long) obj.get("id")).intValue(),
                                    (String) obj.get("명언"),
                                    (String) obj.get("작가")
                            ));
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + path);
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error listing files in directory: " + STORE_DIR);
            e.printStackTrace();
        }
        return list; // 예시로 findAll 호출
    }
}
