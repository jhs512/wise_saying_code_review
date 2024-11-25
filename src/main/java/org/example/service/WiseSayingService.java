package org.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.domain.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.config.CreateBuildData;
import org.example.config.CreateJsonData;
import org.example.config.QueryStringParser;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;
    private final CreateBuildData createBuildData;
    private final QueryStringParser queryStringParser;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository, CreateBuildData createBuildData, QueryStringParser queryStringParser) {
        this.wiseSayingRepository = wiseSayingRepository;
        this.createBuildData = createBuildData;
        this.queryStringParser = queryStringParser;
    }

    public int createJsonFile(int id, String content, String author) throws IOException {
        String jsonData = CreateJsonData.createJsonData(id, content, author);
        return wiseSayingRepository.saveWiseSaying(jsonData, id);
    }

    public int createWiseSaying(String content, String author) throws IOException {
        int id = wiseSayingRepository.findLastId();
        return createJsonFile(id, content, author);
    }

    public boolean createTxtFile(int id) throws IOException {
        return wiseSayingRepository.saveTxtFile(id);
    }

    public List<WiseSaying> getListOfWiseSaying(int page) {
        List<WiseSaying> list = wiseSayingRepository.findAll();
        list.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));

        // 페이징 처리해서 리턴
        int startIdx = ((page - 1) * 5);
        if (list.size() < startIdx + 5) {
            return new ArrayList<>(list.subList(startIdx, list.size()));
        }
        return new ArrayList<>(list.subList(startIdx, startIdx + 5));
    }

    public int getListSizeOfWiseSaying() {
        return wiseSayingRepository.findAll().size();
    }

    public int getSearchListSizeOfWiseSaying(String keyword, String type) {
        return wiseSayingRepository.findByKeyword(keyword, type).size();
    }

    public List<WiseSaying> getListByKeyword(String keyword, String type, int page) {
        List<WiseSaying> byKeyword = wiseSayingRepository.findByKeyword(keyword, type);
        byKeyword.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));

        // 페이징 처리해서 리턴
        int startIdx = ((page - 1) * 5);
        if (byKeyword.size() < startIdx + 5) {
            return new ArrayList<>(byKeyword.subList(startIdx, byKeyword.size()));
        }
        return new ArrayList<>(byKeyword.subList(startIdx, startIdx + 5));
    }

    public String getPageList(int page, String keyword, String type) {
        StringBuilder sb = new StringBuilder();

        int length = getListSizeOfWiseSaying();
        if(!keyword.isEmpty() && !type.isEmpty()) {
            length = getSearchListSizeOfWiseSaying(keyword, type);
        }
        int[] pages = new int[(length + 4) / 5];

        // 명언 파일이 하나도 존재하지 않을 때
        if(length == 0) pages = new int[1];

        for (int i = 1; i <= pages.length; i++) {
            if (i == page) {
                sb.append("[").append(i).append("]").append(" / ");
            } else {
                sb.append(i).append(" / ");
            }
        }
        sb.deleteCharAt(sb.length() - 2);

        return sb.toString();
    }

    public int removeJsonFile(String cmd) {
        int id = queryStringParser.getId(cmd);
        return wiseSayingRepository.delete(id);
    }

    public void updateJsonFile(int id, String content, String author) throws IOException {
        Optional<WiseSaying> wiseSaying = wiseSayingRepository.findById(id);
        if (wiseSaying.isPresent()) {
            createJsonFile(id, content, author);
        }
    }

    public Optional<WiseSaying> getWiseSaying(int id) {
        return wiseSayingRepository.findById(id);
    }

    public boolean createDataJsonFile() throws IOException {
        return wiseSayingRepository.saveBuildFile(createBuildData.createBuildData());
    }

}
