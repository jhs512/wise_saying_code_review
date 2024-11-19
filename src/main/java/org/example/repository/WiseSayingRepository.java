package org.example.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class WiseSayingRepository {

    public static int findLastId() {

        String lastIdPath = System.getProperty("user.dir") + "/db/wiseSaying/lastId.txt";
        File file = new File(lastIdPath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(lastIdPath))) {
                return Integer.parseInt(reader.readLine()) + 1;
            } catch (IOException e) {
                System.out.println("파일 읽기 에러" + e.getMessage());
            }
        }

        return 1;
    }

    public static Optional<File[]> findAll() {

        String jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying";
        File jsonFiles = new File(jsonFilePath);

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] files = jsonFiles.listFiles((dir, name) -> name.endsWith(".json"));
            return Optional.ofNullable(files);
        }

        return Optional.empty();
    }

}
