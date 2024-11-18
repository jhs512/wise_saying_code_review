package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");
        String command = scanner.nextLine();

        if (command.equals("종료")) {
            return;
        }
    }
}