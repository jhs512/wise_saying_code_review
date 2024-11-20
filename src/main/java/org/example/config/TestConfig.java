package org.example.config;


public class TestConfig implements Config {
    @Override
    public String getLastIdPath() {
        return "db/wiseSaying/test/lastId.txt";
    }

    @Override
    public String getJsonPath() {
        return "db/wiseSaying/test/";
    }
}

