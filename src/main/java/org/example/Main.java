package org.example;


import org.example.controller.WiseSayingController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WiseSayingController wiseSayingController = new AppConfig().wiseSayingController(new Scanner(System.in));
        wiseSayingController.run();
    }
}