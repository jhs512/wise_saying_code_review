package org.example.view;

import org.example.converter.Converter;
import org.example.model.WiseSayingItem;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);
    private static long id = 0L;
    private static final Map<Long, WiseSayingItem> store = new HashMap<>();
    private static final Converter converter = new Converter();
    private static final String PATH = "src/main/java/org/example/db/wiseSaying";

    private static final String EXTENSION_JSON = ".json";
    private static final String LAST_ID_FILE_NAME = "lastId.txt";


    public static void start() {
        System.out.println("== 명언 앱 ==");
        boolean flag = true;

        long lastId = loadLastNumber();
        if (lastId != 0) {
            id = lastId;
        }

        while (flag) {
            System.out.print("명령) ");
            flag = register(scanner.nextLine(), id);
        }

        lastId = id; // 종료 시점에 저장된 마지막 id
        saveLastFileNumber(lastId);

        for (WiseSayingItem item : store.values()) {
            String json = converter.singleConvertToJson(item);
            saveFile(json, item.getId());
        }
    }


    //
    private static void saveLastFileNumber(long lastId) {

        File file = new File(PATH, LAST_ID_FILE_NAME);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(String.valueOf(lastId));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void saveFile(String json, long id) {
        String fileName = id + EXTENSION_JSON;
        File file = new File(PATH, fileName);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean register(String input, long lastId) {

        if (input.equals("등록")) {
            System.out.print("명언 : ");
            String wiseSaying = scanner.nextLine();
            System.out.print("작가 : ");
            String author = scanner.nextLine();

            WiseSayingItem item = new WiseSayingItem(++id, author, wiseSaying);
            store.put(item.getId(), item);
            System.out.println(id + "번 명언이 등록되었습니다.");
        } else if (input.equals("종료")) {
            scanner.close();
            return false;
        } else if (input.equals("목록")) {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("---------------");

            for (Map.Entry<Long, WiseSayingItem> entry : store.entrySet()) {
                System.out.println(entry.getKey() + " / "
                        + entry.getValue().getAuthor() + " / "
                        + entry.getValue().getWiseSaying());
            }
        } else if (input.startsWith("삭제?id=")) {
            String id = input.substring(6);
            long parsedId = Long.parseLong(id);

            try {
                if (store.remove(parsedId) == null) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(parsedId + "번 명언은 존재하지 않습니다.");
            }

        } else if (input.startsWith("수정?id=")) {
            String id = input.substring(6);
            WiseSayingItem item = store.get(Long.parseLong(id));
            System.out.println("명언(기존) : " + item.getWiseSaying());
            System.out.print("명언 : ");
            String newWiseSaying = scanner.nextLine();
            item.setWiseSaying(newWiseSaying);

            System.out.println("작가(기존) : " + item.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = scanner.nextLine();
            item.setAuthor(newAuthor);
        }
        return true;
    }

    private static int loadLastNumber() {

        File file = new File(PATH, LAST_ID_FILE_NAME);

        if (!file.exists()) {
            System.out.println("첫 등록과 종료를 완료해주세요.");
            return 0;
        }

        try (Scanner Filescanner = new Scanner(file)) {
            if (Filescanner.hasNextInt()) {
                return Filescanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

}
