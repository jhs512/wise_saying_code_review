package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("== 명언 앱 ==");
        System.out.print("명령) ");
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if ((cmd = br.readLine()).equals("종료"))
                break;
            else {
                System.out.println("올바르지 않은 명령어 입니다.");
            }
        }
    }
}