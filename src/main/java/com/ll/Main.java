package com.ll;

import java.util.*;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    static final String EXIT = "종료";
    static final String INSERT = "등록";
    static final String LIST = "목록";

    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);

        Map<Integer,Info> map = new HashMap<>();

        String command = "";
        String saying = "";
        String writer = "";
        int id = 0;

        while (!command.equals(EXIT)) {
            System.out.print("명령) ");
            command = sc.nextLine();;
            if (command.equals(INSERT)) {
                Info info = new Info();
                System.out.print("명언 : ");
                saying = sc.nextLine();
                System.out.print("작가 : ");
                writer = sc.nextLine();
                info.setId(++id);
                info.setSaying(saying);
                info.setWriter(writer);
                map.put(id, info);
                System.out.println(id+"번 명언이 등록되었습니다.");
            }
            else if (command.equals(LIST)) {

            }
        }
    }
}