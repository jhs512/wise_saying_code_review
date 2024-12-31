package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);
        List<WiseSaying> wiseSayings = new ArrayList<>();
        int num = 1;

        while (true) {
            try {
                System.out.print("명령) ");
                Command command = new Command(scanner.nextLine());
                switch (command.command) {
                    case "등록":
                        System.out.print("명언 : ");
                        String content = scanner.nextLine();
                        System.out.print("작가 : ");
                        String author = scanner.nextLine();
                        WiseSaying wiseSaying = new WiseSaying(num, author, content);
                        wiseSayings.add(wiseSaying);
                        System.out.println(num++ + "번 명언이 등록되었습니다.");
                        break;
                    case "목록":
                        System.out.println("번호 / 작가 / 명언");
                        System.out.println("----------------------");
                        wiseSayings.reversed().forEach(ws -> System.out.println(ws.toString()));
                        break;
                    case "삭제":
                        long id = command.getId();

                        boolean isDelete = wiseSayings.removeIf(ws -> ws.getId() == id);
                        if (!isDelete) {
                            System.out.println(id + "번 명언은 존재하지 않습니다.");
                        } else {
                            System.out.println(id + "번 명언이 삭제되었습니다.");
                        }
                        break;
                    case "수정":
                        id = command.getId();
                        Optional<WiseSaying> opWs = wiseSayings.stream().filter(ws -> ws.getId() == id).findFirst();

                        if (opWs.isEmpty()) {
                            System.out.println(id + "번 명언은 존재하지 않습니다.");
                            break;
                        }

                        wiseSaying = opWs.get();
                        System.out.println("명언(기존) : " + wiseSaying.getContent());
                        System.out.print("명언 : ");
                        content = scanner.nextLine();
                        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                        System.out.print("작가 : ");
                        author = scanner.nextLine();
                        wiseSaying.setContent(content);
                        wiseSaying.setAuthor(author);

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