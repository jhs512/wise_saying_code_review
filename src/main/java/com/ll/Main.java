package com.ll;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("명령) ");
            command = scanner.nextLine();
            switch (command) {
                case "등록":
                    System.out.print("명언 : ");
                    String content = scanner.nextLine();
                    System.out.print("작가 : ");
                    String author = scanner.nextLine();
                    break;
                case "종료":
                    return;
            }
        }
    }
}