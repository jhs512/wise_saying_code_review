package com.ll;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String DB_FOLDER = "db"; // db 폴더 경로
    private static final String DATA_JSON_FILE = DB_FOLDER + "/data.json"; // db 폴더 내 data.json 경로
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        // db 폴더 생성
        File folder = new File(DB_FOLDER);
        if (!folder.exists()) folder.mkdirs();

        // 데이터 로드
        List<WiseSaying> wiseSayings = loadFromDataJson();
        int lastId = getLastId(wiseSayings);

        while (true) {
            System.out.print("명령) ");
            String order = scanner.nextLine().trim();

            if (order.equals("등록")) {
                System.out.print("명언 : ");
                String content = scanner.nextLine();

                System.out.print("작가 : ");
                String author = scanner.nextLine();

                lastId++;
                WiseSaying wiseSaying = new WiseSaying(lastId, content, author);
                wiseSayings.add(wiseSaying);

                System.out.println(lastId + "번 명언이 등록되었습니다.");
                System.out.println();
            } else if (order.equals("목록")) {
                if (wiseSayings.isEmpty()) {
                    System.out.println("등록된 명언이 없습니다.");
                } else {
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");
                    for (int i = wiseSayings.size() - 1; i >= 0; i--) {
                        WiseSaying ws = wiseSayings.get(i);
                        System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
                    }
                }
                System.out.println();
            } else if (order.startsWith("수정?id=")) {
                try {
                    int idToEdit = Integer.parseInt(order.split("=")[1]);
                    Optional<WiseSaying> optionalWiseSaying = wiseSayings.stream()
                            .filter(ws -> ws.getId() == idToEdit).findFirst();

                    if (optionalWiseSaying.isPresent()) {
                        WiseSaying wiseSaying = optionalWiseSaying.get();
                        System.out.println("명언(기존) : " + wiseSaying.getContent());
                        System.out.print("명언 : ");
                        String newContent = scanner.nextLine();

                        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                        System.out.print("작가 : ");
                        String newAuthor = scanner.nextLine();

                        wiseSaying.setContent(newContent);
                        wiseSaying.setAuthor(newAuthor);

                        System.out.println(idToEdit + "번 명언이 수정되었습니다.");
                    } else {
                        System.out.println(idToEdit + "번 명언은 존재하지 않습니다.");
                    }
                } catch (Exception e) {
                    System.out.println("올바른 수정 명령을 입력하세요. 예: 수정?id=1");
                }
                System.out.println();
            } else if (order.startsWith("삭제?id=")) {
                try {
                    int idToDelete = Integer.parseInt(order.split("=")[1]);
                    Optional<WiseSaying> optionalWiseSaying = wiseSayings.stream()
                            .filter(ws -> ws.getId() == idToDelete).findFirst();

                    if (optionalWiseSaying.isPresent()) {
                        WiseSaying wiseSaying = optionalWiseSaying.get();
                        wiseSayings.remove(wiseSaying);

                        System.out.println(idToDelete + "번 명언이 삭제되었습니다.");
                    } else {
                        System.out.println(idToDelete + "번 명언은 존재하지 않습니다.");
                    }
                } catch (Exception e) {
                    System.out.println("올바른 삭제 명령을 입력하세요. 예: 삭제?id=1");
                }
                System.out.println();
            } else if (order.equals("빌드")) {
                saveToDataJson(wiseSayings);
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                System.out.println();
            } else if (order.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("알 수 없는 명령입니다.");
                System.out.println();
            }
        }

        scanner.close();
    }

    // 데이터 클래스
    private static class WiseSaying {
        private int id;
        private String content;
        private String author;

        public WiseSaying(int id, String content, String author) {
            this.id = id;
            this.content = content;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

    // data.json 저장
    private static void saveToDataJson(List<WiseSaying> wiseSayings) {
        try (Writer writer = new FileWriter(DATA_JSON_FILE)) {
            gson.toJson(wiseSayings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // data.json 로드
    private static List<WiseSaying> loadFromDataJson() {
        File file = new File(DATA_JSON_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            WiseSaying[] wiseSayingsArray = gson.fromJson(reader, WiseSaying[].class);
            return new ArrayList<>(Arrays.asList(wiseSayingsArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 마지막 ID 가져오기
    private static int getLastId(List<WiseSaying> wiseSayings) {
        return wiseSayings.stream()
                .mapToInt(WiseSaying::getId)
                .max()
                .orElse(0);
    }
}