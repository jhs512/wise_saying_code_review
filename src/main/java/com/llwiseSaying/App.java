package com.llwiseSaying;

import com.llwiseSaying.Controller.WiseSayingController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    String UserInput;
    State state; //exit or process
    public static BufferedReader br;

    WiseSayingController wiseSayingController;

    public void run() throws IOException {
        br=new BufferedReader(new InputStreamReader(System.in));
        wiseSayingController=new WiseSayingController();

        System.out.println("== 명언 앱 ==");
        System.out.println("명령어 -> 종료, 등록, 목록, 삭제, 수정");
        System.out.println("명령어 -> 초기화, 샘플만들기");
        System.out.println("삭제와 수정 명령어 형식 ( 삭제?id=1 )");
        System.out.println(("검색과 페이징 명령어 형식 ( 목록?keywordType=content&keyword=내용?page=2"));
        System.out.println("------------------------------------");

        while(true) {

            System.out.print("명령) ");
            UserInput=br.readLine();
            state=wiseSayingController.run(UserInput);

            if(state.equals(State.EXIT)) { break; }
        }
    }
}
