package com.ll.domain.wiseSaying.config;

public class AppConfig {
    private static String mode;
    private static final String BASIC_PATH = "db/";

    public static void setDevMode() {
        mode = "dev/";
    }

    public static void setTestMode() {
        mode = "test/";
    }

    public static String getDir() {
        return BASIC_PATH + mode + "wiseSaying/";
    }

    public static String getTestDir() {
        return BASIC_PATH + mode;
    }
}
