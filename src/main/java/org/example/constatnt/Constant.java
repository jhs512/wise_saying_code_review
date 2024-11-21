package org.example.constatnt;

public enum Constant {

    DB_DIRECTORY_PATH("src/main/java/org/example/db/wiseSaying"),
    LAST_ID_FILE_NAME("lastId"),
    EXTENSION_TXT(".txt"),
    EXTENSION_JSON(".json"),
    ALL_DATA_FILE_NAME("data");

    private final String data;

    Constant(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }

}
