package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);
        List<WiseSaying> wiseSayings = new ArrayList<>();
        int num = 1;

        while (true) {
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
                    if (id == 0) {
                        System.out.println("삭제?id= 형식으로 id 값을 입력해주세요.");
                        break;
                    }

                    boolean isDelete = wiseSayings.removeIf(ws -> ws.getId() == id);
                    if (isDelete) {
                        System.out.println(id + "번 명언이 삭제되었습니다.");
                    } else {
                        System.out.println(id + "번 명언은 존재하지 않습니다.");
                    }
                    break;
                case "종료":
                    return;
            }
        }
    }
}