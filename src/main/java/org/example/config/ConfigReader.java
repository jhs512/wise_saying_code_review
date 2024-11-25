package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;

    public ConfigReader(String propertyFilePath) {
        properties = new Properties();

        try (InputStream inputStream = new FileInputStream(propertyFilePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("파일 로드 실패", e);
        }
    }

    // 설정 파일에서 값을 읽어오는 메서드
    public String getBasePath() {
        String key = System.getProperty("config.path.key", "base.save.path");
        return properties.getProperty(key);
    }

    public String getJsonFilePath(int id) {
        return getBasePath() + id + ".json";
    }

    public String getTxtFilePath() {
        return getBasePath() + "lastId.txt";
    }

    public String getBuildFilePath() {
        return getBasePath() + "data.json";
    }
}
