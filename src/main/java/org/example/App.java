package org.example;

import org.example.config.Config;
import org.example.controller.WiseSayingController;
import org.example.entity.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static List<WiseSaying> wiseSayingList = new ArrayList<>();
    private final Config config;
    public static void showWiseSayingList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i=wiseSayingList.size()-1; i>=0; i--) {
            WiseSaying ws = wiseSayingList.get(i);
            System.out.println(ws.toString());
        }
    }

    private final Scanner scanner = new Scanner(System.in);
    private final RequestDispatcher requestDispatcher = new RequestDispatcher();
    private final WiseSayingController wiseSayingController = new WiseSayingController();

    public App(Config config) {
        this.config = config;
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        wiseSayingController.postConstruct(config);

        while(true) {
            System.out.print("명령) ");
            String request = scanner.nextLine();
            requestDispatcher.dispatch(request);
        }
    }
}