package org.example;

import org.example.config.Config;
import org.example.config.MainConfig;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Config config = new MainConfig();
        App app = new App(config);
        app.run();
    }
}