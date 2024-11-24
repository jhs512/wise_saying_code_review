package org.example.repository;

import org.example.config.Config;
import org.example.dto.Pageable;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;
import org.example.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WiseSayingRepository {
    public static List<WiseSaying> wiseSayingList = new ArrayList<>();

    private static int id = 1;
    private static String LAST_ID_PATH;
    private static String JSON_PATH;

    private int findIdxById(int id) {
        for(int i=0;i<wiseSayingList.size();i++) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            if(wiseSaying.getId()==id) {
                return i;
            }
        }
        return -1;
    }

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

    public WiseSayingDTO remove(int removedId) {
        int idx = findIdxById(removedId);
        if(idx==-1) {
            return null;
        }
        //App
        WiseSaying removedWiseSaying = wiseSayingList.remove(idx);

        // Persistence
        String path = JSON_PATH + removedWiseSaying.getId() + ".json";
        FileUtil.deleteFile(Paths.get(path));

        return new WiseSayingDTO(removedWiseSaying);
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
            id=1;
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
                return new WiseSayingDTO(wiseSaying);
            }
        }
        return null;
    }

    public List<WiseSayingDTO> findByContent(String content, Pageable pageable) {
        int totalDataCount = wiseSayingList.size();
        pageable.setTotalDataCount(totalDataCount);

        int cnt = 0;
        List<WiseSayingDTO> result = new ArrayList<>();
        for(int i=0; i<totalDataCount; i++) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            if(wiseSaying.getContent().contains(content)) {
                cnt++;
                if(cnt >= pageable.getOffset()) {
                    result.add(new WiseSayingDTO(wiseSaying));
                }
                if(cnt > pageable.getOffset() + pageable.getLimit()) {
                    break;
                }
            }
        }
        return result;
    }

    public List<WiseSayingDTO> findByAuthor(String author, Pageable pageable) {
        int totalDataCount = wiseSayingList.size();
        pageable.setTotalDataCount(totalDataCount);

        int cnt = 0;
        List<WiseSayingDTO> result = new ArrayList<>();
        for(int i=0; i<totalDataCount; i++) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            if(wiseSaying.getAuthor().contains(author)) {
                cnt++;
                if(cnt >= pageable.getOffset()) {
                    result.add(new WiseSayingDTO(wiseSaying));
                }
                if(cnt > pageable.getOffset() + pageable.getLimit()) {
                    break;
                }
            }
        }
        return result;
    }

    public void setConfig(Config config) {
        LAST_ID_PATH = config.getLastIdPath();
        JSON_PATH = config.getJsonPath();
    }

    public List<WiseSayingDTO> findAll(Pageable pageable) {
        int totalDataCount = wiseSayingList.size();
        pageable.setTotalDataCount(totalDataCount);

        int cnt = 0;
        List<WiseSayingDTO> result = new ArrayList<>();
        for(WiseSaying wiseSaying : wiseSayingList) {
            if(cnt >= pageable.getOffset()) {
                result.add(new WiseSayingDTO(wiseSaying));
            }
            if(cnt > pageable.getOffset() + pageable.getLimit()) {
                break;
            }
        }
        return result;
    }


    public void removeAll() {
        for(int id_=1; id_<id; id_++) {
            remove(id_);
        }
        FileUtil.deleteFile(Paths.get(LAST_ID_PATH));
        return;
    }
}
