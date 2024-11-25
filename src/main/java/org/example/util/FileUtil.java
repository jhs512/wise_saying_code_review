package org.example.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
    public static Map<String, String> jsonFileToMap(Path path) {
        Map<String, String> jsonMap = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for(int i=1; i<lines.size()-1; i++) {
                String[] kv = lines.get(i).split(":");
                String key = kv[0].replace("\"","").trim();
                String value = kv[1].replace("\"","").replace(",","").trim();

                jsonMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    public static void stringToFile(String string, Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(Path path) {
        if(path.toFile().exists()) {
            path.toFile().delete();
        } else {
//            System.out.println("파일이 존재하지 않습니다.");
        }
    }
}
