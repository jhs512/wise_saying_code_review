package com.ll.wiseSaying;

import java.util.Scanner;
/*
  사용자 명령어를 받아 처리
  - 서비스 계층(WiseSayingService) 호출을 통해 명령 수행
  - 사용자 입력과 출력 담당

 */
public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final Scanner scanner;

    public WiseSayingController(WiseSayingService wiseSayingService, Scanner scanner) {
        this.wiseSayingService = wiseSayingService;
        this.scanner = scanner;
    }

    public void handleCommand(String command) {
        if (command.equals("등록")) {
            System.out.print("명언 : ");
            String content = scanner.nextLine();

            System.out.print("작가 : ");
            String author = scanner.nextLine();

            int id = wiseSayingService.create(content, author);
            System.out.println(id + "번 명언이 등록되었습니다.");
        } else if (command.equals("목록")) {
            wiseSayingService.findAll().forEach(wiseSaying -> {
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            });
        } else if (command.startsWith("삭제?id=")) {
            int id = Integer.parseInt(command.split("=")[1]);
            boolean deleted = wiseSayingService.delete(id);

            if (deleted) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } else if (command.startsWith("수정?id=")) {
            int id = Integer.parseInt(command.split("=")[1]);

            System.out.print("명언 : ");
            String content = scanner.nextLine();

            System.out.print("작가 : ");
            String author = scanner.nextLine();

            boolean updated = wiseSayingService.update(id, content, author);
            if (updated) {
                System.out.println(id + "번 명언이 수정되었습니다.");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } else {
            System.out.println("알 수 없는 명령입니다.");
        }
    }
}