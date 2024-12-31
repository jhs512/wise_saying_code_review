package com.ll;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        switch (command) {
            case "종료":
                return;
        }
    }
}