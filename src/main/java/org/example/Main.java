package org.example;

import org.example.config.Config;
import org.example.config.MainConfig;

public class Main {
    public static void main(String[] args) {
        Config config = new MainConfig();
        App program = new App(config);
        program.run();
    }
}