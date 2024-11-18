package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {

    public static class WiseSaying {

        int id;
        String content;
        String author;

        public WiseSaying(int id, String content, String author) {
            this.id = id;
            this.content = content;
            this.author = author;
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("== 명언 앱 ==");
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, WiseSaying> wiseSayings = new HashMap<>();
        int id = 1;
        String author = "";
        String content = "";


        // 프로그램 메인 로직 전에 마지막으로 저장된 명언 아이디 확인
        // 마지막 아이디가 저장된 파일 경로
        String lastIdPath = System.getProperty("user.dir") + "/db/wiseSaying/lastId.txt";

        // 파일 객체 생성
        File file = new File(lastIdPath);

        // 파일이 존재하면 마지막 생성된 id + 1, 존재하지 않으면 위에서 초기화 한대로 1
        if (file.exists() && file.isFile()) {    // 경로가 file 인지 확인하는 메소드 -> 참이면 true 리턴
            try (BufferedReader reader = new BufferedReader(new FileReader(lastIdPath))) {
                id = Integer.parseInt(reader.readLine()) + 1;
            } catch (IOException e) {
                System.out.println("파일 읽기 에러" + e.getMessage());
            }
        }

        // 저장된 json 파일 탐색, 리팩토링 필요 -> 위에 txt 파일 이랑 한번에 읽어와서 파싱할 수 있을 것 같다.
        String jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying";
        File jsonFiles = new File(jsonFilePath);

        if (jsonFiles.exists() && jsonFiles.isDirectory()) {
            File[] fileArr = jsonFiles.listFiles((dir, name) -> name.endsWith(".json"));

            if(fileArr != null) {
                for (File f : fileArr) {

                    String[] values = new String[3];
                    int cnt = 0;

                    try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.length() > 1) {
                                values[cnt] = line.split(": ")[1].replaceAll("\"", "")
                                    .replaceAll(",", "");
                                cnt++;
                            }
                        }
                        wiseSayings.put(Integer.parseInt(values[0]),
                            new WiseSaying(Integer.parseInt(values[0]), values[1], values[2]));

                    } catch (Exception e) {
                        System.out.println("파일 읽기 에러" + e.getMessage());
                    }
                }
            }
        }

        while (true) {
            System.out.print("명령) ");
            if ((cmd = br.readLine()).equals("종료")) break;  // 종료
            else if (cmd.equals("등록")) {    // 등록
                System.out.print("명언 : ");
                content = br.readLine();

                System.out.print("작가 : ");
                author = br.readLine();
                wiseSayings.put(id, new WiseSaying(id, content, author));

                System.out.println(id + "번 명언이 등록되었습니다.");


                // 파일 저장 로직
                String jsonData = "{\n"
                    + "\t\"id\": \"" + id + "\",\n"
                    + "\t\"content\": \"" + content + "\",\n"
                    + "\t\"author\": \"" + author + "\"\n"
                    + "}";

                jsonFilePath =
                    System.getProperty("user.dir") + "/db/wiseSaying/" + id + ".json";
                file = new File(jsonFilePath);
                file.getParentFile().mkdirs();

                // json 파일 생성
                try(FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(jsonData);
                } catch (IOException e) {
                    System.out.println("파일 저장 에러" + e.getMessage());
                }

                // txt 파일 생성
                file = new File(lastIdPath);
                try(FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(String.valueOf(id));
                } catch (IOException e) {
                    System.out.println("파일 저장 에러" + e.getMessage());
                }

                // 등록할 때 마다 생성되는 명언번호가 증가
                id++;

            } else if (cmd.equals("목록")) {      // 목록
                System.out.println("번호 / 작가 / 명언");
                System.out.println("--------------------");

                ArrayList<Integer> ids = new ArrayList<>(wiseSayings.keySet());
                Collections.sort(ids, Collections.reverseOrder());

                for (int tempId : ids) {
                    System.out.println(
                        wiseSayings.get(tempId).id + " / " + wiseSayings.get(tempId).author + " / "
                            + wiseSayings.get(tempId).content);
                }

            } else if (cmd.substring(0, 2).equals("삭제")) {  // 삭제
                int tempId = cmd.charAt(6) - '0';

                if (wiseSayings.containsKey(tempId)) {
                    // map 에서 삭제
                    wiseSayings.remove(tempId);

                    // 파일 삭제 로직
                    jsonFilePath =
                        System.getProperty("user.dir") + "/db/wiseSaying/" + tempId
                            + ".json";
                    file = new File(jsonFilePath);

                    if (file.exists()) {
                        if (file.delete()) {
                            System.out.println(tempId + "번 명언이 삭제되었습니다.");
                        } else {
                            System.out.println("파일 삭제에 실패 했습니다.");
                        }
                    } else {
                        System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                    }
                } else {
                    System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                }
            } else if (cmd.substring(0, 2).equals("수정")) {  // 수정
                int tempId = cmd.charAt(6) - '0';

                if (wiseSayings.containsKey(tempId)) {
                    WiseSaying wiseSaying = wiseSayings.get(tempId);

                    // 명언 수정
                    System.out.println("명언(기존) : " + wiseSaying.content);
                    System.out.print("명언 : ");
                    content = br.readLine();
                    wiseSaying.content = content;

                    // 작가 수정
                    System.out.println("작가(기존) : " + wiseSaying.author);
                    System.out.print("작가 : ");
                    author = br.readLine();
                    wiseSaying.author = author;

                    // 파일 덮어쓰기(수정)
                    jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying/" + tempId + ".json";

                    String jsonData = "{\n"
                        + "\t\"id\": \"" + tempId + "\",\n"
                        + "\t\"content\": \"" + content + "\",\n"
                        + "\t\"author\": \"" + author + "\"\n"
                        + "}";

                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
                        writer.write(jsonData);
                        System.out.println("파일이 수정 되었습니다.");
                    } catch (IOException e) {
                        System.out.println("파일 수정 에러" + e.getMessage());
                    }

                } else {
                    System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                }

            } else if (cmd.equals("빌드")) {
                jsonFilePath = System.getProperty("user.dir") + "/db/wiseSaying";
                jsonFiles = new File(jsonFilePath);
                StringBuilder jsonContents = new StringBuilder();

                if (jsonFiles.exists() && jsonFiles.isDirectory()) {
                    File[] fileArr = jsonFiles.listFiles(
                        (dir, name) -> (name.endsWith(".json") && !name.equals("data.json")));

                    if(fileArr != null) {
                        jsonContents.append("[\n");
                        for (File f : fileArr) {
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

                try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(jsonFilePath + "/data.json"))) {
                    writer.write(jsonContents.toString());
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                } catch (IOException e) {
                    System.out.println("빌드 에러" + e.getMessage());
                }

            } else {
                System.out.println("올바르지 않은 명령어 입니다.");
            }
        }

    }
}