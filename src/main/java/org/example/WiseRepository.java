package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class WiseRepository {
    private List<Wise> wises = new ArrayList<>();
    private int index = 1;
    private int pageOffset = 5;
    private String BASE_PATH;

    public WiseRepository() {
        BASE_PATH = "db/wiseSaying/";
        try {
            loadWises();
            loadLastId();
        } catch (IOException e) {
        }
    }

    public WiseRepository(String basePath) {
        BASE_PATH = basePath;
        try {
            loadWises();
            loadLastId();
        } catch (IOException e) {
        }
    }

    public int applyWise(String content, String author) throws IOException {
        Wise wise = new Wise(index, author, content);
        wises.add(wise);
        saveWise(wise);
        saveLastId();

        return index++;
    }

    public int getWiseSize() {
        return (int) ceil(wises.size() / (double) pageOffset);
    }

    public int getWiseSize(String keywordType, String keyword) {
        return (int) ceil(searchWises(keywordType, keyword).size() / (double) pageOffset);
    }

    public List<Wise> getWises(int page) {
        try {
            return wises.reversed()
                    .subList((page - 1) * pageOffset, min(page * pageOffset, wises.size()));
        } catch (IndexOutOfBoundsException e) {
            return List.of();
        }
    }

    public List<Wise> getWises(String keywordType, String keyword, int page) {
        try {
            List<Wise> result = searchWises(keywordType, keyword);
            return result.subList(min(result.size() - 1, (page - 1) * pageOffset), min(page * pageOffset, result.size()));
        } catch (IndexOutOfBoundsException e) {
            return List.of();
        }
    }

    public void editWise(int id, String newContent, String newAuthor) throws IOException {
        String path = BASE_PATH + id + ".json";
        String data = new String(Files.readAllBytes(Paths.get(path)));

        data = editJson(data, "content", newContent);
        data = editJson(data, "author", newAuthor);

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(data.getBytes());
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

    public void deleteAll() {
        File dir = new File(BASE_PATH);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    private List<Wise> searchWises(String keywordType, String keyword) {
        return wises.stream().filter((wise) -> {
                    if (keywordType.equals("author")) {
                        return wise.author.contains(keyword);
                    } else {
                        return wise.content.contains(keyword);
                    }
                }).collect(Collectors.toList())
                .reversed();
    }

    private void saveWise(Wise wise) throws IOException {
        String path = BASE_PATH + index + ".json";

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(getJsonString(wise).getBytes());
        }
    }

    private void saveLastId() throws IOException {
        String path = BASE_PATH + "lastId.txt";

        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(String.valueOf(index).getBytes());
        }
    }

    private void loadWises() throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(BASE_PATH))) {
            stream.filter(file -> {
                        String name = file.getFileName().toString();
                        return !name.startsWith("data") && name.endsWith("json");
                    })
                    .forEach(file -> {
                        Wise w = jsonToWise(file);
                        if(w != null) {
                            wises.add(w);
                        }
                    });
            wises.sort(Comparator.comparingInt(wise -> wise.index));
        }
    }

    private void loadLastId() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_PATH + "lastId.txt"))) {
            int lastId = Integer.parseInt(reader.readLine());
            index = lastId + 1;
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
            return null;
        }
    }

    private String editJson(String data, String key, String value) {
        Pattern pattern = Pattern.compile("(\"" + key + "\":[^,}]*)");
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            data = matcher.replaceFirst(getKeyValueString(key, value));
        }

        return data;
    }

    private String getKeyValueString(String key, String value) {
        return String.format("\"%s\":" + "\"%s\"", key, value);
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