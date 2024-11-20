package org.example.mvc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

class Service {
    Repository repository;
    final String pathPrefix = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying";

    Service(Repository repository) {
        this.repository = repository;
    }

    // 명언 등록
    public int register(String content, String author) throws IOException {

        // 레포지토리 저장
        WiseSaying wiseSaying = repository.save(new WiseSaying(content, author));

        // json 파일 저장
        storeJson(wiseSaying);

        // 최종 등록한 id 저장 파일 수정
        int register_id = wiseSaying.getId();
        storeLastId(register_id);

        return register_id;
    }

    // 명언 삭제
    public void delete(int id) {
        // repository에서 삭제
        repository.deleteById(id);

        // json 파일 삭제
        String path = pathPrefix + "\\" +id + ".json";
        File file = new File(path);
        file.delete();
    }

    // 명언 조회
    public Optional<WiseSaying> getWiseSayingById(int id) {
        return repository.findById(id);
    }

    // 명언 수정
    public void update(int id, String content, String author) {
            WiseSaying updatedWiseSaying = new WiseSaying(id, content, author);

            // 레포지토리에 수정
            repository.updateById(id, updatedWiseSaying);
            // 수정된 json 파일 저장
            storeJson(updatedWiseSaying);
    }

    // 명언 목록 조회
    public void getList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------");

        List<WiseSaying> wiseSayingList = repository.findAll();

        // 모든 wiseSaying 출력
        for(WiseSaying wiseSaying : wiseSayingList) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }
        System.out.println("명언 목록 출력 완료");
    }

    // 명언 목록 검색
    public void searchWiseSayingList(String command) {
        String[] query = command.split("\\?");
        String[] params = query[1].split("&");

        String keyword = "";
        String keywordType = "";

        for(int i=0; i<params.length; i++) {
            String[] param = params[i].split("=");

            if(param[0].equals("keyword")) {
                keyword = param[1];
            } else if (param[0].equals("keywordType")) {
                keywordType = param[1];
            } else {
                System.out.println("잘못된 검색 명령입니다.");
                return;
            }
        }

        List<WiseSaying> wiseSayingList = repository.findAll();
        if(keywordType.equals("content")) {
            for(WiseSaying wiseSaying : wiseSayingList) {
                if(wiseSaying.getContent().contains(keyword)) {
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                }
            }
        } else if (keywordType.equals("author")) {
            for(WiseSaying wiseSaying : wiseSayingList) {
                if(wiseSaying.getAuthor().contains(keyword)) {
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                }
            }
        }
    }

    // 모든 wiseSaying json 문자열 합쳐서 data.json 파일 생성
    public void build() {
        List<WiseSaying> wiseSayingList = repository.findAll();

        StringBuilder sb = new StringBuilder();
        int size = wiseSayingList.size();

        // 모든 wiseSaying json 문자열 합쳐서 출력하기
        sb.append("[").append("\n");
        for (int i=0; i < size; i++) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            String tmp = convertWiseSayingToString(wiseSaying, true);
            if(i < size-1) {
                sb.append(tmp).append(" ,").append("\n");
            } else {
                sb.append(tmp).append("\n");
            }
        }
        sb.append("]").append("\n");

        String buildResult = sb.toString();

        String path = pathPrefix + "\\data.json";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(buildResult);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();;
        }
    }

    // 명언 객체를 json 파일로 저장
    void storeJson(WiseSaying wiseSaying) {
        String StringOfWiseSaying = convertWiseSayingToString(wiseSaying, false);

        String path = pathPrefix + "\\" + wiseSaying.getId() + ".json";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(StringOfWiseSaying);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();;
        }
    }

    // 최종 등록 명언 id lastId.txt 에 저장
    void storeLastId(int id) {
        // 마지막 이름 lastId.txt 로 저장
        String path = pathPrefix + "\\lastId.txt";
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(String.valueOf(id));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 명언 객체를 문자열로 파싱하기
    String convertWiseSayingToString(WiseSaying wiseSaying, boolean build_flag) {

        String prefix;
        if(build_flag) {
            prefix = "\t";
        } else {
            prefix = "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("{").append("\n");
        sb.append(prefix).append("\t\"id\"").append(" : ").append(wiseSaying.getId()).append(",\n");
        sb.append(prefix).append("\t\"content\"").append(" : \"").append(wiseSaying.getContent()).append("\",\n");
        sb.append(prefix).append("\t\"author\"").append(" : \"").append(wiseSaying.getAuthor()).append("\"\n");
        sb.append(prefix).append("}");

        return sb.toString();
    }

    // 처음 서버가 띄워지면 기존 데이터 불러오기
    void initRepository() {
        File folder = new File(pathPrefix);

        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for(File file : files) {
                    if(file.isFile()) {
                        String fileName = file.getName();

                        if(fileName.equals("data.json")) continue;

                        if(fileName.endsWith("lastId.txt")) {
                            int lastId = Integer.parseInt(getStringFromFile(fileName));
                            repository.setId(lastId);
                        } else if(fileName.endsWith(".json")) {
                            String wiseSayingString = getStringFromFile(fileName);
                            WiseSaying wiseSaying = convertStringToWiseSaying(wiseSayingString);
                            repository.save(wiseSaying.getId(), wiseSaying);
                        }
                    }
                }
            }
        }

        System.out.println("레포지토리 로딩 완료");

        if(repository.getId() > 0) {
            System.out.println("가장 최근 추가된 파일 : " + repository.getId() + ".json");
            System.out.println("------------------------------------");
        }

    }

    // 파일 내용을 문자열로 읽어오기
    String getStringFromFile(String fileName) {
        Path filePath = Paths.get(pathPrefix+"\\"+fileName);

        String content = "";
        try {
            content = Files.readString(filePath);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // 문자열 파싱해서 WiseSaying 객체 만들기
    WiseSaying convertStringToWiseSaying (String wiseSayingString) {
        wiseSayingString = wiseSayingString.replace("{", "")
                .replace("}", "")
                .replace("\n", "")
                .replace("\t", "")
                .replace("\"", "");

        String[] tokens = wiseSayingString.split(",");

        int id = Integer.parseInt(tokens[0].split("\\s*:\\s*")[1].trim());
        String content = tokens[1].split("\\s*:\\s*")[1].trim();
        String author = tokens[2].split("\\s*:\\s*")[1].trim();

        return new WiseSaying(id, content, author);
    }
}
