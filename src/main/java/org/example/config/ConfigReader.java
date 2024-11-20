package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // 설정 파일에서 값을 읽어오는 메서드
    public static String getProperty(String key) {
        Properties properties = new Properties();

        // config.properties 파일 읽기
        try (InputStream inputStream = new FileInputStream(
            "src/main/resources/config.properties")) {
            properties.load(inputStream); // properties 파일 로드
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties.getProperty(key);
    }

    public static String getJsonFilePath(String key, int id) {
        return getProperty(key) + id + ".json";
    }

    public static String getTxtFilePath(String key) {
        return getProperty(key) + "lastId.txt";
    }

    public static String getBuildFilePath(String key) {
        return getProperty(key) + "data.json";
    }

    public static void main(String[] args) {
        // config.properties 에서 file.save.path 값을 가져옴
        String path = getProperty("file.save.path");

        // 파일 경로 출력
        System.out.println("file save path: " + path);
    }
}
