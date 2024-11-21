package org.example;


import org.example.controller.WiseSayingController;

public class Main {
    public static void main(String[] args) {
        WiseSayingController wiseSayingController = new AppConfig().wiseSayingController();
        wiseSayingController.run();
    }
}