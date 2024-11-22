package org.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import org.example.dto.WiseSaying;

public class FileToWiseSaying {

    public Optional<WiseSaying> parseFileToWiseSaying(File file) {
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
            return Optional.of(new WiseSaying(Integer.parseInt(values[0]), values[1], values[2]));
        } catch (IOException e) {
            System.out.println("파일 읽기 에러: " + e.getMessage());
        }

        return Optional.empty();
    }

}
