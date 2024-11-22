package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    // 설정 파일에서 값을 읽어오는 메서드
    public String getProperty(String key) {
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

    public String getJsonFilePath(String key, int id) {
        return getProperty(key) + id + ".json";
    }

    public String getTxtFilePath(String key) {
        return getProperty(key) + "lastId.txt";
    }

    public String getBuildFilePath(String key) {
        return getProperty(key) + "data.json";
    }
}
