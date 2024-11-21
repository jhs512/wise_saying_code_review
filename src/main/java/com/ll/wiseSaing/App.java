package com.ll.wiseSaing;

import com.ll.wiseSaing.controller.WiseSayingController;
import com.ll.wiseSaing.model.WiseSaying;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static final String EXIT = "종료";
    static final String INSERT = "등록";
    static final String LIST = "목록";
    static final String DELETE = "삭제";
    static final String MODIFY = "수정";
    static final String Build = "빌드";

    public void run() throws IOException, ParseException {
        System.out.println("== 명언 앱 ==");

        Scanner sc = new Scanner(System.in);
        WiseSayingController ws = new WiseSayingController();

        String command = "";

        int id = 0;
        List<WiseSaying> list = new ArrayList<>();
        ws.init(id, list);

        while (!command.equals(EXIT)) {
            System.out.print("명령) ");
            command = sc.nextLine();

            if (command.length() > 1) {
                switch (command.substring(0, 2)) {
                    case INSERT -> {
                        id++;
                        ws.insert(id, list);
                    }
                    case LIST -> {
                        ws.selectList(list);
                    }
                    case DELETE -> {
                        if(!command.contains("=")) continue;
                        int delete_id = Integer.parseInt(command.split("=")[1]);
                        ws.delete(delete_id, list);
                    }
                    case MODIFY -> {
                        if(!command.contains("=")) continue;
                        int modify_id = Integer.parseInt(command.split("=")[1]);
                        ws.modify(modify_id, list);
                    }
                    case Build -> {
                        ws.build(list);
                    }
                }
            }
        }
        sc.close();

        ws.end(id);
    }
}
