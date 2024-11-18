package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class WiseManager {
    private ArrayList<Wise> wises = new ArrayList<>();
    private int index = 1;
    private final String BASE_PATH = "db/wiseSaying/";

    public void applyWise(String wise, String author) {
        Wise w = new Wise(index, author, wise);
        wises.add(w);
        saveWise(w);
        saveLastId();

        System.out.println(index++ + "번 명언이 등록되었습니다.");
    }

    public void printWises() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------");

        wises.forEach(wise -> System.out.println(wise));
    }

    public boolean deleteWise(int id) {
        return wises.removeIf(wise -> wise.index == id);
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

    public void saveLastId() {
        String path = BASE_PATH + "lastId.txt";
        try (FileOutputStream output = new FileOutputStream(path)) {
            output.write(String.valueOf(index).getBytes());
        } catch (IOException e) {
            System.out.println("파일 출력 에러");
        }
    }

    private String getJsonString(Wise wise) {
        String json = """
                {
                  "id": %d,
                  "content": "%s",
                  "author": "%s"
                }
                """;

        return String.format(json, wise.index, wise.wise, wise.author);
    }
}
