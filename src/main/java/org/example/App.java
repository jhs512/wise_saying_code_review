package org.example;

import org.example.config.Config;
import org.example.controller.WiseSayingController;
import java.util.Scanner;

public class App {
    public final Config config;

    private final RequestDispatcher requestDispatcher = new RequestDispatcher();
    private final WiseSayingController wiseSayingController = new WiseSayingController();

    public App(Config config) {
        this.config = config;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        wiseSayingController.bindingConfig(config);
        wiseSayingController.loadData();
        wiseSayingController.setScanner(scanner);
        System.out.println("== 명언 앱 ==");
        String request="";
        while(!request.equals("종료")) {
            System.out.print("명령) ");
            request = scanner.nextLine();
            requestDispatcher.dispatch(request);
        }
//        wiseSayingController.deleteAllWiseSayings();
    }


    public void clear() {
        wiseSayingController.bindingConfig(config);
        wiseSayingController.deleteAllWiseSayings();
    }
}