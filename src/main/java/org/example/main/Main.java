package org.example.main;//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
import org.example.controller.WiseSayingController;

import java.util.Scanner;


public class Main {
    private static final String JSON_FILE_PATH = "src/main/resources/data.json";
    private static final String LAST_ID_FILE_PATH = "src/main/resources/lastId.txt";

    public static void main(String[] args) {
        WiseSayingApp wiseSayingApp = new WiseSayingApp(new WiseSayingController(new Scanner(System.in), JSON_FILE_PATH, LAST_ID_FILE_PATH));
        wiseSayingApp.run();
    }
}