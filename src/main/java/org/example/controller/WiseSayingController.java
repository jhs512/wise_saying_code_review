package org.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.dto.WiseSaying;
import org.example.service.WiseSayingService;
import org.example.util.QueryStringParser;

public class WiseSayingController {

    public static void createWiseSaying(BufferedReader br) throws IOException {
        System.out.print("명언 : ");
        String content = br.readLine();
        System.out.print("작가 : ");
        String author = br.readLine();

        int wiseSayingId = WiseSayingService.createWiseSaying(content, author);
        boolean txtFile = WiseSayingService.createTxtFile(wiseSayingId);
        if (wiseSayingId != -1 && txtFile) {
            System.out.println(wiseSayingId + "번 명언이 등록되었습니다.");
        }
    }

    public static void getAllWiseSaying(String cmd) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------");
        List<WiseSaying> listOfWiseSaying = WiseSayingService.getListOfWiseSaying();
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }
    }

    public static void getListByKeyword(String cmd) {
        String[] keyword = QueryStringParser.getKeyword(cmd);
        System.out.println("----------------------");
        System.out.println("검색타입 : " + keyword[1]);
        System.out.println("검색어 : " + keyword[0]);
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------");

        List<WiseSaying> listByKeyword = WiseSayingService.getListByKeyword(keyword[0], keyword[1]);
        for (WiseSaying wiseSaying : listByKeyword) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }
    }

    public static void deleteWiseSaying(String cmd) {
        int id = WiseSayingService.removeJsonFile(cmd);
        if (id != -1) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(QueryStringParser.getId(cmd) + "번 명언은 존재하지 않습니다.");
        }
    }

    public static void updateWiseSaying(String cmd, BufferedReader br) throws IOException {
        Optional<WiseSaying> wiseSaying = WiseSayingService.getWiseSaying(QueryStringParser.getId(cmd));

        if (wiseSaying.isPresent()) {
            System.out.println("명언(기존) : " + wiseSaying.get().getContent());
            System.out.print("명언 : ");
            String content = br.readLine();

            System.out.println("작가(기존) : " + wiseSaying.get().getAuthor());
            System.out.print("작가 : ");
            String author = br.readLine();

            WiseSayingService.updateJsonFile(wiseSaying.get().getId(), content, author);
        } else {
            System.out.println(QueryStringParser.getId(cmd) + "번 명언은 존재하지 않습니다.");
        }
    }

    public static void createBuildFile() throws IOException {
        if (WiseSayingService.createDataJsonFile()) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }

}

