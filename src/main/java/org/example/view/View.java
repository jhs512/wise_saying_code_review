package org.example.view;

import java.util.Scanner;

public class View {

    public static void start() {
        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.next().equals("종료")) {
            scanner.close();
        }
    }
}
