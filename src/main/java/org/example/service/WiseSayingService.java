package org.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.dto.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.util.CreateBuildData;
import org.example.util.CreateJsonData;
import org.example.util.QueryStringParser;

public class WiseSayingService {


    public static int createJsonFile(int id, String content, String author) throws IOException {
        String jsonData = CreateJsonData.createJsonData(id, content, author);
        return WiseSayingRepository.saveWiseSaying(jsonData, id);
    }

    public static int createWiseSaying(String content, String author) throws IOException {
        int id = WiseSayingRepository.findLastId();
        return createJsonFile(id, content, author);
    }

    public static boolean createTxtFile(int id) throws IOException {
        return WiseSayingRepository.saveTxtFile(id);
    }

    public static List<WiseSaying> getListOfWiseSaying() {
        List<WiseSaying> list = WiseSayingRepository.findAll();
        list.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
        return list;
    }

    public static List<WiseSaying> getListByKeyword(String keyword, String type) {
        List<WiseSaying> byKeyword = WiseSayingRepository.findByKeyword(keyword, type);
        byKeyword.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
        return byKeyword;
    }

    public static int removeJsonFile(String cmd) {
        int id = QueryStringParser.getId(cmd);
        return WiseSayingRepository.delete(id);
    }

    public static void updateJsonFile(int id, String content, String author) throws IOException {
        Optional<WiseSaying> wiseSaying = WiseSayingRepository.findById(id);
        if (wiseSaying.isPresent()) {
            createJsonFile(id, content, author);
        }
    }

    public static Optional<WiseSaying> getWiseSaying(int id) {
        return WiseSayingRepository.findById(id);
    }

    public static boolean createDataJsonFile() throws IOException {
        return WiseSayingRepository.saveBuildFile(CreateBuildData.createBuildData());
    }

}
