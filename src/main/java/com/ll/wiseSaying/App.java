package com.ll.wiseSaying;


import java.util.Scanner;
/*

  - 사용자 입력을 받아 명령어를 해석하고 컨트롤러에 전달
  - 프로그램 전체 흐름을 관
 */
public class App {
    private final WiseSayingController wiseSayingController;
    private final Scanner scanner;

    public App() {
        this.scanner = new Scanner(System.in);
        WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
        WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
        this.wiseSayingController = new WiseSayingController(wiseSayingService, scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();

            if (command.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            wiseSayingController.handleCommand(command);
        }
    }
}