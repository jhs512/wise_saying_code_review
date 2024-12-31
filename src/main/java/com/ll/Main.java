package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);
        String command;
        List<WiseSaying> wiseSayings = new ArrayList<>();
        int num = 1;

        while (true) {
            System.out.print("명령) ");
            command = scanner.nextLine();
            switch (command) {
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
                case "종료":
                    return;
            }
        }
    }
}