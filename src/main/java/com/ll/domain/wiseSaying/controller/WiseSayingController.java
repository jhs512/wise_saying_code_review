package com.ll.domain.wiseSaying.controller;

import com.ll.domain.wiseSaying.dto.Command;
import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.domain.wiseSaying.service.WiseSayingService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.wiseSayingService = new WiseSayingService();
        this.scanner = scanner;
    }

    public void register() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = this.wiseSayingService.register(content, author);

        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    public void getWiseSayings(Command command) {
        String keywordType = command.getKeywordType();
        String keyword = command.getKeyword();

        boolean hasKeyword = false;
        boolean hasKeywordType = false;

        if (!keyword.isEmpty()) {
            hasKeyword = true;
        }

        if (!keywordType.isEmpty()) {
            hasKeywordType = true;
        }

        List<WiseSaying> list = this.wiseSayingService.getWiseSayings(keywordType, keyword);

        if (hasKeywordType || hasKeyword) {
            System.out.println("----------------------");
            if (hasKeywordType && hasKeyword) {
                System.out.println("검색타입 : " + keywordType);
                System.out.println("검색어 : " + keyword);
            } else if (hasKeywordType) {
                System.out.println("검색타입 : " + keywordType);
            } else {
                System.out.println("검색어 : " + keyword);
            }
            System.out.println("----------------------");
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (WiseSaying ws : list) {
            System.out.println(ws.toString());
        }
    }

    public void modify(long id) {
        Optional<WiseSaying> opWs = this.wiseSayingService.getWiseSaying(id);

        if (opWs.isEmpty()) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        WiseSaying ws = opWs.get();

        System.out.println("명언(기존) : " + ws.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.println("작가(기존) : " + ws.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        this.wiseSayingService.modify(ws, content, author);
    }

    public void delete(long id) {
        boolean isDelete = this.wiseSayingService.delete(id);

        if (!isDelete) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        } else {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        }
    }

    public void build() {
        this.wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
