package com.ll;

import com.ll.utils.FileManager;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        FileManager fileManager = new FileManager("./src/main/java/com/ll/db/wiseSaying", ".json");
        QuoteStore quoteStore = new QuoteStore(fileManager);
        QuoteService quoteService = new QuoteService(quoteStore);
        QuoteController quoteController = new QuoteController(quoteService);
        QuoteApp quoteApp = new QuoteApp(quoteController, new Scanner(System.in));

        quoteApp.run();
    }
}