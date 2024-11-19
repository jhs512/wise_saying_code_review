package org.example.mvc;

import java.io.*;
import java.util.List;
import java.util.Optional;

class Service {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Repository repository;
    final String pathPrefix = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\src\\main\\resources";

    Service(Repository repository) {
        this.repository = repository;
    }

    // 명언 등록
    public void register() throws IOException {
        System.out.print("명언 : ");
        String content = br.readLine();
        System.out.print("작가 : ");
        String author = br.readLine();

        // 레포지토리 저장
        WiseSaying wiseSaying = repository.save(new WiseSaying(content, author));

        // json 파일 저장
        storeJson(wiseSaying);

        // 최종 등록한 id 저장 파일 수정
        storeLastId(wiseSaying.getId());
    }

    // 명언 삭제
    public void delete(int id) {
        // 존재하는 id 라면 레포지토리, json 파일 모두 삭제
        Optional<WiseSaying> optional = repository.findById(id);
        if(optional.isPresent()) {
            WiseSaying wiseSaying = optional.get();

            // repository에서 삭제
            repository.deleteById(id);

            // json 파일 삭제
            String path = pathPrefix + "\\" +id + ".json";
            File file = new File(path);
            file.delete();
        }
        // 존재하지 않는 id 예외 처리
        else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void update(int id) throws IOException {
        Optional<WiseSaying> optional = repository.findById(id);
        // 존재하는 id 라면 레포지토리, json 파일 모두 수정
        if(optional.isPresent()) {
            WiseSaying wiseSaying = optional.get();
            System.out.println("명언(기존): " + wiseSaying.getContent());
            System.out.print("명언 : ");
            String temp_content = br.readLine();

            System.out.println("작가(기존): " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            String temp_author = br.readLine();

            WiseSaying updatedWiseSaying = new WiseSaying(id, temp_content, temp_author);

            // 레포지토리에 수정
            repository.updateById(id, updatedWiseSaying);

            // 수정된 json 파일 저장
            storeJson(updatedWiseSaying);
        }

        // 존재하지 않는 id 예외 처리
        else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void getList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------");

        List<WiseSaying> wiseSayingList = repository.findAll();

        // 모든 wiseSaying 출력
        for(WiseSaying wiseSaying : wiseSayingList) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
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
        sb.append(prefix).append("\t\"id\"").append(" : ").append(wiseSaying.getId()).append("\n");
        sb.append(prefix).append("\t\"content\"").append(" : ").append(wiseSaying.getContent()).append("\n");
        sb.append(prefix).append("\t\"author\"").append(" : ").append(wiseSaying.getAuthor()).append("\n");
        sb.append(prefix).append("}");

        return sb.toString();
    }
}
