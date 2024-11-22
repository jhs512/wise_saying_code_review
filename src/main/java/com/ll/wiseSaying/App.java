package com.ll.wiseSaying;


import java.util.Scanner;
/*

  - 사용자 입력을 받아 명령어를 해석하고 컨트롤러에 전달
  - 프로그램 전체 흐름을 관
 */
public class App {
    private final WiseSayingController wiseSayingController;
    private final Scanner scanner;

    // 기본 생성자
    public App() {
        this(new Scanner(System.in)); // System.in을 기본 입력으로 사용하는 생성자 호출
    }

    // Scanner를 매개변수로 받는 생성자 (테스트에서 사용)
    public App(Scanner scanner) {
        this.scanner = scanner;
        WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
        WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
        this.wiseSayingController = new WiseSayingController(wiseSayingService, scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            if (!scanner.hasNextLine()) { // 입력 스트림이 비어 있으면 종료
                System.err.println("입력이 더 이상 존재하지 않습니다.");
                break;
            }
            String command = scanner.nextLine().trim();

            if (command.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            wiseSayingController.handleCommand(command);
        }
    }
}