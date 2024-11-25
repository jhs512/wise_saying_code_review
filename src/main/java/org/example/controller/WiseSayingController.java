package org.example.controller;

import org.example.handler.ErrorHandler;
import org.example.model.PageResult;
import org.example.model.WiseSayingItem;
import org.example.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private final Scanner scanner;

    public WiseSayingController(WiseSayingService wiseSayingService, Scanner scanner) {
        this.wiseSayingService = wiseSayingService;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();
            try {
                if (input.equals("종료")) {
                    break;
                }
                runCommand(input);
            } catch (IllegalArgumentException e) {
                ErrorHandler.handleException(e);
            }
        }
    }
    private void runCommand(String input) {
        if (input.equals("등록")) {
            registerCommand();
        } else if (input.startsWith("목록")) {
            listCommand(input);
        } else if (input.startsWith("삭제?id=")) {
            deleteCommand(input);
        } else if (input.startsWith("수정?id=")) {
            updateCommand(input);
        } else if (input.equals("빌드")) {
            buildCommand();
        }
    }

    private void buildCommand() {
        wiseSayingService.build();
        System.out.println("data 내용이 갱신되었습니다.");
    }

    private void updateCommand(String input) {
        String id = input.substring(6);
        WiseSayingItem item = wiseSayingService.findById(id);

        System.out.println("명언(기존) : " + item.getWiseSaying());
        System.out.print("명언 : ");
        String newWiseSaying = scanner.nextLine();
        wiseSayingService.updateWiseSaying(id, newWiseSaying);
        System.out.println("수정이 완료되었습니다.");

        System.out.println("작가(기존) : " + item.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = scanner.nextLine();
        wiseSayingService.updateAuthor(id, newAuthor);
    }

    private void deleteCommand(String input) {
        String id = input.substring(6);
        String removedId = wiseSayingService.remove(id);
        System.out.println(removedId +  "번 명언이 삭제되었습니다.");
    }

    private void listCommand(String input) {
        if (input.startsWith("목록?keywordType=")) {
            String[] keywords = input.split("keywordType=")[1].split("&keyword=");
            String keywordType = keywords[0];
            String keyword = keywords[1];
            List<String> list = wiseSayingService.findByKeywords(keywordType, keyword);
            list.forEach(System.out::println);
        } else if (input.startsWith("목록?page=")){
            String currentPage = input.substring(8);
            System.out.println("번호 / 작가 / 명언");
            System.out.println("---------------");
            PageResult result = wiseSayingService.search(currentPage);

            result.getItems().forEach(System.out::println);
            System.out.println("---------------");
            System.out.println("페이지 : [" + result.getCurrenPage() + "] / " +result.getTotalPage());
        } else if (input.equals("목록")) {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("---------------");
            PageResult result = wiseSayingService.search("");

            result.getItems().forEach(System.out::println);
            System.out.println("---------------");
            System.out.println("페이지 : [" + result.getCurrenPage() + "] / " +result.getTotalPage());
        }
    }
    private void registerCommand() {
        System.out.print("명언 : ");
        String wiseSaying = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        long id = wiseSayingService.register(wiseSaying, author);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }
}
