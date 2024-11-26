
package org.example.mvc.controller;

import org.example.utils.Parser;
import org.example.utils.PrintUtils;
import org.example.entity.WiseSaying;
import org.example.mvc.service.WiseSayingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private final BufferedReader bufferedReader;

    public WiseSayingController(WiseSayingService wiseSayingService, BufferedReader br) {
        wiseSayingService.initRepository();
        this.wiseSayingService = wiseSayingService;
        this.bufferedReader = br;
    }

    public void registerWiseSaying() throws IOException {
        System.out.print("명언 : ");
        String content = bufferedReader.readLine();
        System.out.print("작가 : ");
        String author = bufferedReader.readLine();

        WiseSaying wiseSaying = wiseSayingService.register(content, author);

        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void deleteWiseSaying(String command) {
        int id = Parser.extractIdFromCommand(command);

        if (wiseSayingService.delete(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void deleteAllWiseSaying() {
        wiseSayingService.deleteAll();
        System.out.println("전체 삭제 완료");
    }


    public void updateWiseSaying(String command) throws IOException {
        int id = Parser.extractIdFromCommand(command);
        Optional<WiseSaying> optional = wiseSayingService.findWiseSayingById(id);

        if (optional.isPresent()) {
            WiseSaying wiseSaying = optional.get();

            System.out.println("명언(기존) : " + wiseSaying.getContent());
            System.out.print("명언 : ");
            String content = bufferedReader.readLine();

            System.out.println("작가(기존) : " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            String author = bufferedReader.readLine();

            wiseSayingService.update(id, content, author);
            System.out.println(id + "번 명언 수정 완료");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void search(String command) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-------------------");
        List<WiseSaying> resultList = wiseSayingService.search(command);

        int size = resultList.size(); // 검색 결과 명언 수
        int page_num = (size + 4) / 5; // 검색 결과 총 페이지 수

        // 현재 페이지 정보 command 파싱해서 획득
        int page = Parser.extractPageFromCommand(command);
        int start = (page - 1) * 5;

        // 페이지에 맞게 5개의 wiseSaying 출력
        for (int i = start; i < start + 5; i++) {
            if (i >= size) break;
            WiseSaying wiseSaying = resultList.get(i);
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        PrintUtils.printPages(page_num, page);
    }

    public void build() {
        wiseSayingService.build();
        System.out.println("빌드 완료");
    }

}
