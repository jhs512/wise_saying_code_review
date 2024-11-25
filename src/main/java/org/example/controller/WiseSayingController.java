package org.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.domain.WiseSaying;
import org.example.service.WiseSayingService;
import org.example.config.QueryStringParser;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private final QueryStringParser queryStringParser;

    public WiseSayingController(WiseSayingService wiseSayingService, QueryStringParser queryStringParser) {
        this.wiseSayingService = wiseSayingService;
        this.queryStringParser = queryStringParser;
    }

    public void createWiseSaying(BufferedReader br) throws IOException {
        System.out.print("명언 : ");
        String content = br.readLine();
        System.out.print("작가 : ");
        String author = br.readLine();

        int wiseSayingId = wiseSayingService.createWiseSaying(content, author);
        boolean txtFile = wiseSayingService.createTxtFile(wiseSayingId);
        if (wiseSayingId != -1 && txtFile) {
            System.out.println(wiseSayingId + "번 명언이 등록되었습니다.");
        }
    }

    public void getAllWiseSaying(String cmd) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------");

        // 명언 출력
        int page = queryStringParser.getPage(cmd);
        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListOfWiseSaying(page);
        listOfWiseSaying.forEach(System.out::println);

        // 페이지 출력
        System.out.print("페이지 : ");
        System.out.println(wiseSayingService.getPageList(page, "", ""));
    }

    public void getListByKeyword(String cmd) {
        String[] keyword = queryStringParser.getKeyword(cmd);
        int page = queryStringParser.getPage(cmd);
        System.out.println("----------------------");
        System.out.println("검색타입 : " + keyword[1]);
        System.out.println("검색어 : " + keyword[0]);
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------");

        List<WiseSaying> listByKeyword = wiseSayingService.getListByKeyword(keyword[0], keyword[1], page);
        listByKeyword.forEach(System.out::println);

        // 페이지 출력
        System.out.print("페이지 : ");
        System.out.println(wiseSayingService.getPageList(page, keyword[0], keyword[1]));
    }

    public void deleteWiseSaying(String cmd) {
        int id = wiseSayingService.removeJsonFile(cmd);
        if (id != -1) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(queryStringParser.getId(cmd) + "번 명언은 존재하지 않습니다.");
        }
    }

    public void updateWiseSaying(String cmd, BufferedReader br) throws IOException {
        Optional<WiseSaying> wiseSaying = wiseSayingService.getWiseSaying(queryStringParser.getId(cmd));

        if (wiseSaying.isPresent()) {
            System.out.println("명언(기존) : " + wiseSaying.get().getContent());
            System.out.print("명언 : ");
            String content = br.readLine();

            System.out.println("작가(기존) : " + wiseSaying.get().getAuthor());
            System.out.print("작가 : ");
            String author = br.readLine();

            wiseSayingService.updateJsonFile(wiseSaying.get().getId(), content, author);
        } else {
            System.out.println(queryStringParser.getId(cmd) + "번 명언은 존재하지 않습니다.");
        }
    }

    public void createBuildFile() throws IOException {
        if (wiseSayingService.createDataJsonFile()) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }

}

