package com.ll.domain.wiseSaying.app;

import com.ll.domain.wiseSaying.controller.WiseSayingController;
import com.ll.domain.wiseSaying.dto.Command;
import com.ll.global.exception.GlobalException;
import com.ll.global.exception.InvalidCommandInputException;
import java.util.Scanner;

public class WiseSayingApp {
    private final Scanner scanner;
    private final WiseSayingController wiseSayingController;

    public WiseSayingApp(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingController = new WiseSayingController(scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while(true) {
            try {
                System.out.print("명령) ");
                Command command = new Command(scanner.nextLine());
                switch (command.getCommand()) {
                    case "등록":
                        this.wiseSayingController.register();
                        break;
                    case "목록":
                        this.wiseSayingController.getWiseSayings();
                        break;
                    case "삭제":
                        long id = command.getId();
                        this.wiseSayingController.delete(id);
                        break;
                    case "수정":
                        id = command.getId();
                        this.wiseSayingController.modify(id);
                        break;
                    case "빌드":
                        this.wiseSayingController.build();
                        break;
                    case "종료":
                        return;
                    default:
                        throw new InvalidCommandInputException("명령어를 제대로 입력해주세요.");
                }
            } catch (GlobalException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}