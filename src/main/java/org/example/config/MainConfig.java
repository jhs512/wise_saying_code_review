package org.example.config;

public class MainConfig implements Config{
    @Override
    public String getLastIdPath() {
        return "db/wiseSaying/lastId.txt";
    }

    @Override
    public String getJsonPath() {
        return "db/wiseSaying/";
    }
}

