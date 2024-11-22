package org.example;

import org.example.controller.FamousController;

import java.util.*;

public class Main {

    // 명언 컨트롤러 선언
    private static FamousController famousController = new FamousController();

    /**
     *
     * 메인 함수
     *
     * @param args
     */
    public static void main(String[] args) {
        famousController.run();
    }
}