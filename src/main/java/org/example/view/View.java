package org.example.view;

import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);
    private static long sequence = 0L;

    public static void start() {
        System.out.println("== 명언 앱 ==");
        boolean flag = true;

        while (flag) {
            System.out.print("명령) ");
            flag = register(scanner.nextLine());
        }

    }

    private static boolean register(String input) {
        if (input.equals("등록")) {
            System.out.print("명언 : ");
            scanner.nextLine();
            System.out.print("작가 : ");
            scanner.nextLine();
            System.out.println(++sequence + "번 명언이 등록되었습니다.");
            return true;
        } else if (input.equals("종료")) {
            scanner.close();
            return false;
        }
        return true;
    }
}
