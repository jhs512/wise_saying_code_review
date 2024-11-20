package org.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.dto.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.util.CreateJsonData;

public class WiseSayingService {


    public static int createJsonFile(int id, String content, String author) throws IOException {
        String jsonData = CreateJsonData.createJsonData(id, content, author);
        return WiseSayingRepository.saveWiseSaying(jsonData, id);
    }

    public static int createWiseSaying(String content, String author) throws IOException {
        int id = WiseSayingRepository.findLastId();
        return createJsonFile(id, content, author);
    }

    public static boolean createTxtFile(int id) throws IOException {
        return WiseSayingRepository.saveTxtFile(id);
    }

    public static List<WiseSaying> createListOfWiseSaying() {
        List<WiseSaying> list = new ArrayList<>();
        Optional<File[]> files = WiseSayingRepository.findAll();

        if (files.isPresent()) {

            for(File file : files.get()) {
                String[] values = new String[3];
                int cnt = 0;

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.length() > 1) {
                            values[cnt] = line.split(": ")[1].replaceAll("\"", "").replaceAll(",", "");
                            cnt++;
                        }
                    }

                    list.add(new WiseSaying(Integer.parseInt(values[0]), values[1], values[2]));
                } catch (IOException e) {
                    System.out.println("파일 읽기 에러: " + e.getMessage());
                }
            }
        }

        return list;
    }

    public static int removeJsonFile(String cmd) throws IOException {
        int id = cmd.charAt(6) - '0';
        return WiseSayingRepository.delete(id);
    }

    public static void updateJsonFile(int id, String content, String author) throws IOException {
        Optional<File> byId = WiseSayingRepository.findById(id);
        if (byId.isPresent()) {
            createJsonFile(id, content, author);
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public static Optional<WiseSaying> getWiseSaying(int id) {
        Optional<File> file = WiseSayingRepository.findById(id);

        if (file.isPresent()) {
            String[] values = new String[3];
            int cnt = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(file.get()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1) {
                        values[cnt] = line.split(": ")[1].replaceAll("\"", "").replaceAll(",", "");
                        cnt++;
                    }
                }

                return Optional.of(new WiseSaying(Integer.parseInt(values[0]), values[1], values[2]));
            } catch (IOException e) {
                System.out.println("파일 읽기 에러: " + e.getMessage());
            }
        }

        return Optional.empty();
    }

    public static boolean createDataJsonFile() throws IOException {
        String jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying/";
        File jsonFiles = new File(jsonFilePath);
        StringBuilder jsonContents = new StringBuilder();

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] fileArr = jsonFiles.listFiles(
                (dir, name) -> (name.endsWith(".json") && !name.equals("data.json")));

            if(fileArr != null) {
                jsonContents.append("[\n");
                for (File f : fileArr) {
                    try(BufferedReader reader = new BufferedReader(new FileReader(f))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.equals("}")) {
                                jsonContents.append("\t").append(line).append(",\n");
                            } else jsonContents.append("\t").append(line).append("\n");
                        }

                    } catch (Exception e) {
                        System.out.println("파일 읽기 에러" + e.getMessage());
                    }
                }
                jsonContents.delete(jsonContents.length() - 2, jsonContents.length() - 1);
                jsonContents.append("]");
            }
        }

        return WiseSayingRepository.save(jsonContents.toString(), jsonFilePath + "data.json");
    }

}
