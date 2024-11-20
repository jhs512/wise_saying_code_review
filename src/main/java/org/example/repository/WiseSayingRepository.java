package org.example.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import org.example.config.ConfigReader;

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

    public static boolean save(String data, String path) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(data);
            return true;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static int saveWiseSaying(String data, int id) throws IOException {
        String path = ConfigReader.getJsonFilePath("file.save.path", id);
        save(data, path);
        return id;
    }

    public static boolean saveTxtFile(int id) throws IOException {
        String path = ConfigReader.getTxtFilePath("file.save.path");
        return save(String.valueOf(id), path);
    }

    public static Optional<File[]> findAll() {

        String jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying";
        File jsonFiles = new File(jsonFilePath);

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] files = jsonFiles.listFiles(
                (dir, name) -> name.endsWith(".json") && !name.startsWith("data"));
            return Optional.ofNullable(files);
        }

        return Optional.empty();
    }

    public static int delete(int id) throws IOException {

        String path = System.getProperty("user.dir") + "/db/wiseSaying/" + id + ".json";
        File file = new File(path);

        if (file.exists()) {
            file.delete();
            return id;
        }
        return -1;
    }

    public static Optional<File> findById(int id) {

        String jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying/" + id + ".json";
        File jsonFile = new File(jsonFilePath);

        if (jsonFile.exists()) {
            return Optional.of(jsonFile);
        }
        return Optional.empty();
    }

}
