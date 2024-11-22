package org.example.repository;

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

    private final ConfigReader configReader;
    private final FileToWiseSaying fileToWiseSaying;

    public WiseSayingRepository(ConfigReader configReader, FileToWiseSaying fileToWiseSaying) {
        this.configReader = configReader;
        this.fileToWiseSaying = fileToWiseSaying;
    }

    public int findLastId() {

        String path = configReader.getTxtFilePath("base.save.path");
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

    public boolean save(String data, String path) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(data);
            return true;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public int saveWiseSaying(String data, int id) throws IOException {
        String path = configReader.getJsonFilePath("base.save.path", id);
        save(data, path);
        return id;
    }

    public boolean saveTxtFile(int id) throws IOException {
        String path = configReader.getTxtFilePath("base.save.path");
        return save(String.valueOf(id), path);
    }

    public boolean saveBuildFile(String data) throws IOException {
        String path = configReader.getBuildFilePath("base.save.path");
        return save(data, path);
    }

    public List<WiseSaying> findAll() {

        String path = configReader.getProperty("base.save.path");
        File jsonFiles = new File(path);

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] files = jsonFiles.listFiles(
                (dir, name) -> name.endsWith(".json") && !name.startsWith("data"));

            if(files != null) {
                List<WiseSaying> list = new ArrayList<>();

                for(File file : files) {
                    list.add(fileToWiseSaying.parseFileToWiseSaying(file).get());
                }
                return list;
            }
        }

        return new ArrayList<>();
    }

    public List<WiseSaying> findByKeyword(String keyword, String type) {
        List<WiseSaying> list = findAll();
        if(!list.isEmpty()) {
            return list.stream().filter(
                w -> type.equals("content") ? w.getContent().contains(keyword)
                : w.getAuthor().contains(keyword)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public int delete(int id) {

        String path = configReader.getJsonFilePath("base.save.path", id);
        File file = new File(path);

        if (file.exists()) {
            file.delete();
            return id;
        }
        return -1;
    }

    public Optional<WiseSaying> findById(int id) {
        String path = configReader.getJsonFilePath("base.save.path", id);
        File file = new File(path);

        if (file.exists()) {
            return fileToWiseSaying.parseFileToWiseSaying(file);
        }
        return Optional.empty();
    }

}
