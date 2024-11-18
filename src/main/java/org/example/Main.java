package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static class WiseSaying {
        int id;
        String author;
        String content;

        public WiseSaying(int id, String author, String content) {
            this.id = id;
            this.author = author;
            this.content = content;
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("== 명언 앱 ==");
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<WiseSaying> al = new ArrayList<>();
        int Id = 1;

        while (true) {
            // 반복문 돌때마다 명령창 출력되도록 수정
            System.out.print("명령) ");

            if ((cmd = br.readLine()).equals("종료"))
                break;
            else if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                String content = br.readLine();

                System.out.print("작가 : ");
                String author = br.readLine();
                al.add(new WiseSaying(Id, author, content));

                // 등록시 생성된 명언번호 출력
                System.out.println(Id + "번 명언이 등록되었습니다.");

                // 등록할 때 마다 생성되는 명언번호가 증가
                Id++;
            } else if (cmd.equals("목록")) {      // 목록
                System.out.println("번호 / 작가 / 명언");
                System.out.println("--------------------");
                for(WiseSaying ws : al) {
                    System.out.println(ws.id + " / " + ws.author + " / " + ws.content);
                }
            } else {
                System.out.println("올바르지 않은 명령어 입니다.");
            }
        }

    }
}