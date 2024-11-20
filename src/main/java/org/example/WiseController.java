package org.example;

import java.io.IOException;
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

        whileLoop:
        while (true) {
            System.out.print("명령) ");
            String input = scanner.nextLine();
            Command command = Command.findByCommand(input);

            switch (command) {
                case Command.APPLY:
                    applyWise();
                    break;
                case Command.LIST:
                    printWise();
                    break;
                case Command.DELETE:
                    try {
                        int id = command.getId(input);
                        deleteWise(id);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("id 값을 입력해주세요");
                    }
                    break;
                case Command.EDIT:
                    try {
                        int id = command.getId(input);
                        editWise(id);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("id 값을 입력해주세요");
                    }
                    break;
                case Command.BUILD:
                    buildWise();
                    break;
                case Command.QUIT:
                    break whileLoop;
                case Command.UNKNOWN:
                    System.out.println("알 수 없는 명령어입니다.");
                    break;
            }
        }
    }

    public void applyWise() {
        System.out.print("명언 : ");
        String wise = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        try {
            int index = service.applyWise(wise, author);
            System.out.println(index + "번 명언이 등록되었습니다.");
        } catch (IOException e) {
            System.out.println("명언을 등록하지 못했습니다.");
        }
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

            try {
                service.editWise(id, newContent, newAuthor);
                previous.content = newContent;
                previous.author = newAuthor;
            } catch (IOException e) {
                System.out.println("명언을 수정하지 못했습니다.");
            }
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
