package com.ll;

import com.ll.domain.wiseSaying.app.WiseSayingApp;
import com.ll.domain.wiseSaying.config.AppConfig;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WiseSayingApp app = new WiseSayingApp(scanner);
        AppConfig.setDevMode();

        app.run();

        scanner.close();
    }
}