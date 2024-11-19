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

public class WiseSayingService {

    public static List<WiseSaying> createListofWiseSaying() throws IOException {
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

    public static void createJsonFile(int id, String content, String author) {

        String data = "{\n"
            + "\t\"id\": \"" + id + "\",\n"
            + "\t\"content\": \"" + content + "\",\n"
            + "\t\"author\": \"" + author + "\"\n"
            + "}";

        String path = System.getProperty("user.dir") + "/db/wiseSaying/" + id + ".json";
        WiseSayingRepository.save(data, path);
    }

    public static void createTxtFile(int id) {
        String path = System.getProperty("user.dir") + "/db/wiseSaying/lastId.txt";
        WiseSayingRepository.save(String.valueOf(id), path);
    }




}
