package com.ll.wiseSaing;

import com.ll.wiseSaing.controller.WiseSayingController;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

public class App {

    static final String EXIT = "종료";
    static final String INSERT = "등록";
    static final String LIST = "목록";
    static final String DELETE = "삭제";
    static final String MODIFY = "수정";
    static final String Build = "빌드";
    static final String PATH = "D:\\son\\project\\programmers\\db\\wiseSaying\\";

    static JSONArray arr;
    static int id = 0;

    public void run() throws IOException, ParseException {
        System.out.println("== 명언 앱 ==");

        Scanner sc = new Scanner(System.in);
        WiseSayingController ws = new WiseSayingController();

        String command = "";
        init();

        while (!command.equals(EXIT)) {
            System.out.print("명령) ");
            command = sc.nextLine();
            ;
            if (command.length() > 1) {
                switch (command.substring(0, 2)) {
                    case INSERT -> {
                        id++;
                        ws.insert(id, arr);
                    }
                    case LIST -> {
                        ws.list(arr);
                    }
                    case DELETE -> {
                        int delete_id = Integer.parseInt(command.split("=")[1]);
                        ws.delete(delete_id, arr);
                    }
                    case MODIFY -> {
                        int modify_id = Integer.parseInt(command.split("=")[1]);
                        ws.modify(modify_id, arr);
                    }
                    case Build -> {
                        ws.build(arr);
                    }
                }
            }
        }
        sc.close();

        File file = new File(PATH+"lastId.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
        bw.write(String.valueOf(id));
        bw.close();
    }

    void init() throws IOException, ParseException {
        Reader reader = new FileReader(new File(PATH + "data.json"));
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(reader);
        arr = (JSONArray) obj;

        File file = new File(PATH + "lastId.txt");
        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            id = Integer.parseInt(br.readLine());
        }
    }
}
