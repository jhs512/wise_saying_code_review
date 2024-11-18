package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
//        ArrayList<WiseSaying> wiseSayings = new ArrayList<>();
        HashMap<Integer, WiseSaying> wiseSayings = new HashMap<>();
//        ArrayList<Integer> removeIds = new ArrayList<>();
        int id = 1;
        String author = "";
        String content = "";

        while (true) {
            // 반복문 돌때마다 명령창 출력되도록 수정
            System.out.print("명령) ");

            if ((cmd = br.readLine()).equals("종료")) // 종료
                break;
            else if (cmd.equals("등록")) {    // 등록
                System.out.print("명언 : ");
                content = br.readLine();

                System.out.print("작가 : ");
                author = br.readLine();
                wiseSayings.put(id, new WiseSaying(id, author, content));

                // 등록시 생성된 명언번호 출력
                System.out.println(id + "번 명언이 등록되었습니다.");

                // 등록할 때 마다 생성되는 명언번호가 증가
                id++;
            } else if (cmd.equals("목록")) {      // 목록
                System.out.println("번호 / 작가 / 명언");
                System.out.println("--------------------");

                ArrayList<Integer> ids = new ArrayList<>(wiseSayings.keySet());
                Collections.sort(ids);

                for(int tempId : ids) {
                    System.out.println(
                        wiseSayings.get(tempId).id + " / " + wiseSayings.get(tempId).author + " / "
                            + wiseSayings.get(tempId).content);
                }
            } else if (cmd.substring(0, 2).equals("삭제")) {  // 삭제
                int tempId = cmd.charAt(6) - '0';

                if (wiseSayings.containsKey(tempId)) {
                    System.out.println(tempId + "번 명언이 삭제되었습니다.");
                    wiseSayings.remove(tempId);
                } else {
                    System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                }
            } else if (cmd.substring(0, 2).equals("수정")) {  // 수정
                int tempId = cmd.charAt(6) - '0';

                if (wiseSayings.containsKey(tempId)) {
                    WiseSaying wiseSaying = wiseSayings.get(tempId);

                    // 명언 수정
                    System.out.println("명언(기존) : " + wiseSaying.content);
                    System.out.print("명언 : ");
                    content = br.readLine();
                    wiseSaying.content = content;

                    // 작가 수정
                    System.out.println("작가(기존) : " + wiseSaying.author);
                    System.out.print("작가 : ");
                    author = br.readLine();
                    wiseSaying.author = author;
                } else {
                    System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                }

            } else {
                System.out.println("올바르지 않은 명령어 입니다.");
            }
        }

    }
}