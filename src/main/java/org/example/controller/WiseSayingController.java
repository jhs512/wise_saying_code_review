package org.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import org.example.dto.WiseSaying;
import org.example.service.WiseSayingService;

public class WiseSayingController {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void createWiseSaying() throws IOException {
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

    public static void getAllWiseSaying() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("--------------------");
        List<WiseSaying> listOfWiseSaying = WiseSayingService.createListOfWiseSaying();
        listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
            for (WiseSaying wiseSaying : listOfWiseSaying) {
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
    }

    public static void deleteWiseSaying(String cmd) throws IOException {
        int id = WiseSayingService.removeJsonFile(cmd);
        if (id != -1) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(cmd.charAt(6) - '0' + "번 명언은 존재하지 않습니다.");
        }
    }

    public static void updateWiseSaying(String cmd) throws IOException {
        Optional<WiseSaying> wiseSaying = WiseSayingService.getWiseSaying(cmd.charAt(6) - '0');

        if (wiseSaying.isPresent()) {
            System.out.println("명언(기존) : " + wiseSaying.get().getContent());
            System.out.print("명언 : ");
            String content = br.readLine();

            System.out.println("작가(기존) : " + wiseSaying.get().getAuthor());
            System.out.print("작가 : ");
            String author = br.readLine();

            WiseSayingService.updateJsonFile(wiseSaying.get().getId(), content, author);
        } else {
            System.out.println(cmd.charAt(6) - '0' + "번 명언은 존재하지 않습니다.");
        }
    }

    public static void createBuildFile() throws IOException {
        if (WiseSayingService.createDataJsonFile()) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }

}

