package org.example.repository;

import org.example.config.Config;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;
import org.example.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.example.App.wiseSayingList;

public class WiseSayingRepository {
    private static int id = 1;
    private static String LAST_ID_PATH;
    private static String JSON_PATH;

    public int save(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(id, author, content);
        wiseSayingList.add(wiseSaying);

        // Persistence
        String jsonString = wiseSaying.generateJson("");
        String path = JSON_PATH + id + ".json";

        FileUtil.stringToFile(jsonString, Paths.get(path));
        FileUtil.stringToFile(""+id, Paths.get(LAST_ID_PATH));

        return id++;
    }

    public void remove(int idx) {
        WiseSaying removedWiseSaying =  wiseSayingList.remove(idx);

        // Persistence
        String path = JSON_PATH + removedWiseSaying.getId() + ".json";
        FileUtil.deleteFile(Paths.get(path));
    }


    public void build() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        int n = wiseSayingList.size();
        for(int i=0; i<n-1; i++) {
            sb.append(wiseSayingList.get(i).generateJson("  "));
            sb.append(",\n");
        }
        sb.append(wiseSayingList.get(n-1).generateJson("  "));
        sb.append("\n]");

        String jsonString = sb.toString();
        FileUtil.stringToFile(jsonString, Paths.get(JSON_PATH + "data.json"));
    }

    public void load() {
        Path lastIdPath = Paths.get(LAST_ID_PATH);

        if (!Files.exists(lastIdPath)) {
            return;
        }

        try {
            int lastId = Integer.parseInt(Files.readString(lastIdPath));
            for(int i=1; i<=lastId; i++) {
                Path path = Paths.get(JSON_PATH + i + ".json");
                if(Files.exists(path)) {
                    Map<String,String> jsonMap = FileUtil.jsonFileToMap(path);
                    int id = Integer.parseInt(jsonMap.get("id"));
                    String author = jsonMap.get("author");
                    String content = jsonMap.get("content");
                    wiseSayingList.add(new WiseSaying(id, author, content));
                }
            }
            id = lastId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(WiseSayingDTO wiseSayingDTO) {
        String content = wiseSayingDTO.getContent();
        String author = wiseSayingDTO.getAuthor();

        for(WiseSaying wiseSaying : wiseSayingList) {
            if(wiseSaying.getId() == wiseSayingDTO.getId()) {
                // org.example.App
                wiseSaying.setContent(content);
                wiseSaying.setAuthor(author);

                // Persistence
                String jsonString = wiseSaying.generateJson("");
                String path = JSON_PATH + wiseSaying.getId() + ".json";

                FileUtil.stringToFile(jsonString, Paths.get(path));
                return;
            }
        }
    }

    public WiseSayingDTO findById(int id) {
        for(WiseSaying wiseSaying : wiseSayingList) {
            if(wiseSaying.getId() == id) {
                String content = wiseSaying.getContent();
                String author = wiseSaying.getAuthor();
                return new WiseSayingDTO(id, content, author);
            }
        }
        return null;
    }

    public void init(Config config) {
        LAST_ID_PATH = config.getLastIdPath();
        JSON_PATH = config.getJsonPath();
    }
}
