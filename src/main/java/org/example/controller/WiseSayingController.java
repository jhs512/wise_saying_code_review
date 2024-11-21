package org.example.controller;

import org.example.handler.ErrorHandler;
import org.example.model.WiseSayingItem;
import org.example.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;
    private final Scanner scanner = new Scanner(System.in);

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();

            try {
                if (input.equals("등록")) {
                    System.out.print("명언 : ");
                    String wiseSaying = scanner.nextLine();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();
                    long id = wiseSayingService.register(wiseSaying, author);
                    System.out.println(id + "번 명언이 등록되었습니다.");

                } else if (input.equals("종료")) {
                    scanner.close();
                    break;

                } else if (input.equals("목록")) {
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("---------------");
                    List<String> list = wiseSayingService.search();
                    list.forEach(System.out::println);

                } else if (input.startsWith("삭제?id=")) {
                    // 6은 추후 상수화
                    String id = input.substring(6);
                    wiseSayingService.remove(id);
                    System.out.println(id +  "번 명언이 삭제되었습니다.");

                } else if (input.startsWith("수정?id=")) {
                    String id = input.substring(6);
                    WiseSayingItem item = wiseSayingService.findOne(id);

                    System.out.println("명언(기존) : " + item.getWiseSaying());
                    System.out.print("명언 : ");
                    String newWiseSaying = scanner.nextLine();
                    wiseSayingService.updateWiseSaying(id, newWiseSaying);

                    System.out.println("작가(기존) : " + item.getAuthor());
                    System.out.print("작가 : ");
                    String newAuthor = scanner.nextLine();
                    wiseSayingService.updateAuthor(id, newAuthor);
                } else if (input.equals("빌드")) {
                    wiseSayingService.build();
                    System.out.println("data 내용이 갱신되었습니다.");
                }
            } catch (IllegalArgumentException e) {
                ErrorHandler.handleException(e);
            }

        }


    }
}
