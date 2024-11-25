package org.example;


import org.example.constatnt.Constant;

import java.util.Scanner;

import static org.example.constatnt.Constant.DB_DIRECTORY_PATH;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig(DB_DIRECTORY_PATH.getData());
        appConfig.wiseSayingController(new Scanner(System.in)).run();
    }
}