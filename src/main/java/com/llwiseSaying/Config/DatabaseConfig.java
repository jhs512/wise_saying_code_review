package com.llwiseSaying.Config;

public class DatabaseConfig implements Config{

    public final static String DB_PATH="db/wiseSaying";
    public final static String LAST_ID_FILE="lastId.txt";
    public final static String DB_EXTENSION=".json";
    public final static String LAST_ID_EXTENSION=".txt";

    public String getDBPath() {
        return DB_PATH;
    }
    public String getLastIdFile() {
        return LAST_ID_FILE;
    }
    public String getDBExtension() {
        return DB_EXTENSION;
    }
    public String getLastIdExtension(){
        return LAST_ID_EXTENSION;
    }

}
