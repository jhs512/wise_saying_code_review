package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WiseRepository {
    private ArrayList<Wise> wises = new ArrayList<>();
    private int index = 1;
    private final String BASE_PATH = "db/wiseSaying/";

    public WiseRepository() {
        loadWises();
        loadLastId();
    }

    public int applyWise(String content, String author) {
        Wise wise = new Wise(index, author, content);
        wises.add(wise);
        saveWise(wise);
        saveLastId();

        return index++;
    }

    public ArrayList<Wise> getWises() {
        return wises;
    }

    public void editWise(int id, String newWise, String newAuthor) {
        String path = BASE_PATH + id + ".json";

        try {
            String data = new String(Files.readAllBytes(Paths.get(path)));

            data = editJson(data, "content", newWise, false);
            data = editJson(data, "author", newAuthor, true);

            try (FileOutputStream output = new FileOutputStream(path)) {
                output.write(data.getBytes());
            }
        } catch (IOException e) {
            System.out.println("파일 출력 에러");
        }
    }

    public boolean deleteWise(int id) {
        Wise wise = findWise(id);

        if (wise != null) {
            wises.remove(wise);

            File file = new File(BASE_PATH + id + ".json");
            if (file.exists()) {
                file.delete();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveWises() {
        String jsonArrayString = getJsonArrayString();
        String path = BASE_PATH + "data.json";

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(jsonArrayString.getBytes());
            return true;
        } catch (IOException e) {
            System.out.println("파일 출력 에러");
            return false;
        }
    }

    public Wise findWise(int id) {
        Optional<Wise> result = wises.stream().filter(wise -> wise.index == id).findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    private void saveWise(Wise wise) {
        String path = BASE_PATH + index + ".json";

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(getJsonString(wise).getBytes());
        } catch (IOException e) {
            System.out.println("파일 출력 에러");
        }
    }

    private void saveLastId() {
        String path = BASE_PATH + "lastId.txt";

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(String.valueOf(index).getBytes());
        } catch (IOException e) {
            System.out.println("파일 출력 에러");
        }
    }

    private void loadWises() {
        try (Stream<Path> stream = Files.walk(Paths.get(BASE_PATH))) {
            stream.filter(file -> {
                        String name = file.getFileName().toString();
                        return !name.startsWith("data") && name.endsWith("json");
                    })
                    .forEach(file -> {
                        wises.add(jsonToWise(file));
                    });
        } catch (IOException e) {
            System.out.println("파일 입력 에러");
        }
    }

    private void loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_PATH + "lastId.txt"))) {
            int lastId = Integer.parseInt(reader.readLine());
            index = lastId + 1;
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            System.out.println("파일 입력 에러");
        }
    }

    private Wise jsonToWise(Path file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toString()))) {
            HashMap<String, String> jsonMap = new HashMap<>();
            String line = reader.readLine();

            String[] data = line.replaceAll("[{}\"]", "")
                    .split(",");

            for (String d : data) {
                String[] temp = d.split(":");
                String key = temp[0];
                String value = temp[1];

                jsonMap.put(key, value);
            }

            String id = jsonMap.get("id");
            String author = jsonMap.get("author");
            String content = jsonMap.get("content");

            return new Wise(Integer.parseInt(id), author, content);
        } catch (IOException e) {
            System.out.println("파일 입력 에러");
            return null;
        }
    }

    private String editJson(String data, String key, String value, boolean last) {
        Pattern pattern = Pattern.compile("(\"" + key + "\":[^,}]*)");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            data = matcher.replaceFirst("\"" + key + "\":" + "\"" + value + "\"");
        }

        return data;
    }

    private String getJsonString(Wise wise) {
        String json = "{\"id\":%d,\"content\":\"%s\",\"author\":\"%s\"}";

        return String.format(json, wise.index, wise.content, wise.author);
    }

    private String getJsonArrayString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(wises.stream()
                .map(wise -> getJsonString(wise))
                .collect(Collectors.joining(","))
        );
        builder.append("]");

        return builder.toString();
    }
}