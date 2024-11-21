package org.example.repository;

import static org.example.util.FileToWiseSaying.parseFileToWiseSaying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.config.ConfigReader;
import org.example.dto.WiseSaying;
import org.example.util.FileToWiseSaying;

public class WiseSayingRepository {

    public static int findLastId() {

        String path = ConfigReader.getTxtFilePath("files.save.path");
        File file = new File(path);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
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
        String path = ConfigReader.getJsonFilePath("files.save.path", id);
        save(data, path);
        return id;
    }

    public static boolean saveTxtFile(int id) throws IOException {
        String path = ConfigReader.getTxtFilePath("files.save.path");
        return save(String.valueOf(id), path);
    }

    public static boolean saveBuildFile(String data) throws IOException {
        String path = ConfigReader.getBuildFilePath("files.save.path");
        return save(data, path);
    }

    public static List<WiseSaying> findAll() {

        String path = ConfigReader.getProperty("files.save.path");
        File jsonFiles = new File(path);

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] files = jsonFiles.listFiles(
                (dir, name) -> name.endsWith(".json") && !name.startsWith("data"));

            if(files != null) {
                List<WiseSaying> list = new ArrayList<>();

                for(File file : files) {
                    list.add(FileToWiseSaying.parseFileToWiseSaying(file).get());
                }
                return list;
            }
        }

        return new ArrayList<>();
    }

    public static List<WiseSaying> findByKeyword(String keyword, String type) {
        List<WiseSaying> list = findAll();
        if(!list.isEmpty()) {
            return list.stream().filter(
                w -> type.equals("content") ? w.getContent().contains(keyword)
                : w.getAuthor().contains(keyword)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static int delete(int id) {

        String path = ConfigReader.getJsonFilePath("files..save.path", id);
        File file = new File(path);

        if (file.exists()) {
            file.delete();
            return id;
        }
        return -1;
    }

    public static Optional<WiseSaying> findById(int id) {
        String path = ConfigReader.getJsonFilePath("files..save.path", id);
        File file = new File(path);

        if (file.exists()) {
            return parseFileToWiseSaying(file);
        }
        return Optional.empty();
    }

}
