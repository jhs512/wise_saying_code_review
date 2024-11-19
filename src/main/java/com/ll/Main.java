package com.ll;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    static final String EXIT = "종료";
    static final String INSERT = "등록";
    static final String LIST = "목록";
    static final String DELETE = "삭제";
    static final String MODIFY = "수정";
    static final String Build = "빌드";
    static final String PATH = "D:\\son\\project\\programmers\\db\\wiseSaying\\";

    static JSONArray arr;
    static int id = 0;

    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("== 명언 앱 ==");

        Scanner sc = new Scanner(System.in);
        Command cm = new Command();

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
                        cm.insert(id, arr);
                    }
                    case LIST -> {
                        cm.list(arr);
                    }
                    case DELETE -> {
                        int delete_id = Integer.parseInt(command.split("=")[1]);
                        cm.delete(delete_id, arr);
                    }
                    case MODIFY -> {
                        int modify_id = Integer.parseInt(command.split("=")[1]);
                        cm.modify(modify_id, arr);
                    }
                    case Build -> {
                        cm.build(arr);
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

    static void init() throws IOException, ParseException {
        Reader reader = new FileReader(new File(PATH+"data.json"));
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(reader);
        arr = (JSONArray) obj;

        File file = new File(PATH+"lastId.txt");
        if (file.exists()) {
            BufferedReader br =  new BufferedReader(new FileReader(file));
            id = Integer.parseInt(br.readLine());
        }
    }
}