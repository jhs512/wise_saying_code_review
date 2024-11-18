package com.ll;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령 )");
            String order = scanner.nextLine();

            if(order.equals("등록")){
                System.out.println("명언 : ");
                String wiseSaying = scanner.nextLine();

                System.out.println("작가 : ");
                String writer = scanner.nextLine();

                System.out.println("명언 : " + wiseSaying);
                System.out.println("작가 : " + writer);
                System.out.println();
            } else if (order.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break; // 반복문 종료
            } else {
                System.out.println("알 수 없는 명령입니다.");
                System.out.println();
            }
        }

        scanner.close();

    }
}