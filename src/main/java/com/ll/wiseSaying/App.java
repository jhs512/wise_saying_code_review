package com.ll.wiseSaying;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingController wiseSayingController;

    public App() {
        scanner = new Scanner(System.in);
        wiseSayingController = new WiseSayingController(scanner);
    }
    //AppTest 에서 인자로 scanner를 받기때문에 method Overloading
    public App(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingController = new WiseSayingController(scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();

            if (command.equals("종료")) {
                break;
            }

            if(command.contains("?")) {
                //삭제 <- query[0] ? id=n <- query[1]
                String[] query = command.split("\\?");
                String commandName = query[0];
                String commandSpecific = query[1];
                switch(commandName) {
                    case "목록":
                        wiseSayingController.list(commandSpecific);
                        break;
                    case "삭제":
                        wiseSayingController.remove(commandSpecific);
                        break;
                    case "수정":
                        wiseSayingController.modify(commandSpecific);
                        break;
                }
                continue;  // 중요: query 처리 후 아래 switch문 실행 방지
            }

            switch (command) {
                case "등록":
                    wiseSayingController.write();
                    break;
                case "목록":
                    wiseSayingController.list(null);
                    break;
                case "빌드":
                    wiseSayingController.build();
                    break;
            }
        }
    }
}