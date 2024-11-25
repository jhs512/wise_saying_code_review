package org.example.repository;

public class CreateJsonData {

    public static String createJsonData(int id, String content, String author) {
        return "{\n"
            + "\t\"id\": \"" + id + "\",\n"
            + "\t\"content\": \"" + content + "\",\n"
            + "\t\"author\": \"" + author + "\"\n"
            + "}";
    }

}
