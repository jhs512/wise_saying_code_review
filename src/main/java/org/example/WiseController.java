package org.example;

import java.util.Scanner;

public class WiseController {
    private WiseService service;
    Scanner scanner = new Scanner(System.in);

    public WiseController(WiseService service) {
        this.service = service;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                applyWise();
            } else if (command.equals("목록")) {
                printWise();
            } else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                deleteWise(id);
            } else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                editWise(id);
            } else if (command.equals("빌드")) {
                buildWise();
            }
        }
    }

    public void applyWise() {
        System.out.print("명언 : ");
        String wise = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        int index = service.applyWise(wise, author);
        System.out.println(index + "번 명언이 등록되었습니다.");
    }

    public void printWise() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------");

        service.getWises().forEach(wise -> System.out.println(wise));
    }

    public void editWise(int id) {
        Wise previous = service.getWise(id);

        if (previous != null) {
            System.out.println("명언(기존) : " + previous.content);
            System.out.print("명언 : ");
            String newContent = scanner.nextLine();

            System.out.println("작가(기존) : " + previous.author);
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine();

            service.editWise(id, newContent, newAuthor);
            previous.content = newContent;
            previous.author = newAuthor;
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void deleteWise(int id) {
        boolean exist = service.deleteWise(id);

        if (exist) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void buildWise() {
        boolean result = service.buildWise();

        if (result) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }
    }
}
