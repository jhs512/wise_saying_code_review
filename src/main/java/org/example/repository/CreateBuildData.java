package org.example.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import org.example.config.ConfigReader;

public class CreateBuildData {

    private final ConfigReader configReader;

    public CreateBuildData(ConfigReader configReader) {
        this.configReader = configReader;
    }

    public String createBuildData() {
        String path = configReader.getBasePath();
        File jsonFiles = new File(path);
        StringBuilder jsonContents = new StringBuilder();

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] files = jsonFiles.listFiles(
                (dir, name) -> (name.endsWith(".json") && !name.equals("data.json")));

            if(files != null) {

                Arrays.sort(files, (f1, f2) -> {
                    int num1 = Integer.parseInt(f1.getName().replaceAll("[^0-9]", ""));
                    int num2 = Integer.parseInt(f2.getName().replaceAll("[^0-9]", ""));
                    return Integer.compare(num1, num2); // num1과 num2 비교
                });

                jsonContents.append("[\n");
                for (File f : files) {
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
        return jsonContents.toString();
    }

}
