package com.ll;


import com.ll.utils.FileManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuoteApp {

    boolean running;
    Scanner sc;
    QuoteController quoteController;

    public QuoteApp(QuoteController quoteController, Scanner sc) {
        running = true;
        this.sc = sc;
        this.quoteController = quoteController;
    }


    public void run() {

        System.out.println("== 명언 앱 ==");

        while (running) {
            System.out.print("명령) ");


            //parsing query to hashmap
            String[] commandArr = sc.nextLine().strip().split("[?]");
            String command = commandArr[0];
            String[] queries = commandArr.length > 1 ? commandArr[1].split("&") : new String[0];

            HashMap<String, String> queryMap = new HashMap<>();
            Arrays.stream(queries).forEach(value -> {
                String[] pair = value.split("=");
                queryMap.put(pair[0], pair[1]);
            });


            switch (command) {
                case "종료":
                    running = false;
                    break;

                case "등록":
                    quoteController.saveQuote(sc);
                    break;

                case "목록":
                    quoteController.quoteList(queryMap);
                    break;

                case "삭제":
                    quoteController.deleteQuote(Integer.parseInt(queryMap.get("id")));
                    break;

                case "수정":
                    quoteController.updateQuote(sc, Integer.parseInt(queryMap.get("id")));
                    break;

                case "빌드":
                    quoteController.buildQuotes();
                    break;
            }
        }

        sc.close();
    }
}