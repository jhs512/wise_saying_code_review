package com.ll.wiseSaying;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingRepository {
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;
    private String DB_PATH = "db/wiseSaying";
//    private String filePath = ""
    public WiseSayingRepository() {
        initDataDir();
    }

    private void initDataDir() {
        File dir = new File(DB_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public WiseSaying save(String content, String author) {
        lastId++;
        WiseSaying wiseSaying = new WiseSaying(lastId, content, author);
        wiseSayings.add(wiseSaying);

        String filePath = String.format("%s/%d.json", DB_PATH,lastId);
        try{
        FileWriter writer = new FileWriter(filePath);
//        String json = String.format{
//                    "{\n" +
//                    "   \"id\": %d, \n" +
//                    "   \"content\": \"%s\",\n" +
//                    "   \"author\": \"%s\"\n" +
//                    "}",
//                    wiseSaying.getId(),
//                    wiseSaying.getContent(),
//                    wiseSaying.getAuthor();
//        }
        String json = """
                    {
                        "id": %d,
                        "content": "%s",
                        "author" : %s
                    }
                    """.formatted(lastId, content, author);
        writer.write(json);
        writer.close();}
        catch (IOException e){
            System.out.println("파일 생성 실패");
        }

        return wiseSaying;
    }

    public List<WiseSaying> findAll() {
        return wiseSayings;
    }

    //keywordType == content or author or id -> keyword로 찾기

    public List<WiseSaying> findByKey(String keywordType, String keyword) {
        return wiseSayings.stream()
                .filter(wiseSaying -> {
                    if (keywordType.equals("content")) {
                        return wiseSaying.getContent().contains(keyword);
                    } else if (keywordType.equals("author")) {
                        return wiseSaying.getAuthor().contains(keyword);
                    } else if (keywordType.equals("id")) {
                        return String.valueOf(wiseSaying.getId()).contains(keyword);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public WiseSaying findById(int id) {
        return wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean remove(int id) {
        boolean isRemoved = wiseSayings.removeIf(w -> w.getId() == id);
        if(isRemoved){
            String filePath = String.format("%s%d.json", DB_PATH,lastId);
            new File(filePath).delete();
        }

        return isRemoved;
    }

    public void modify(int id, String content, String author) {
        WiseSaying wiseSaying = findById(id);
        if (wiseSaying != null) {
            wiseSaying.setContent(content);
            wiseSaying.setAuthor(author);
        }

        String filePath = String.format("%s/%d.json", DB_PATH, id);
        try{
            FileWriter writer = new FileWriter(filePath);
            String json = """
                    {
                        "id": %d,
                        "content": "%s",
                        "author" : %s
                    }
                    """.formatted(id, content, author);
            writer.write(json);
            writer.close();}
        catch (IOException e){
            System.out.println("IO 오류");
        }
    }

    public void build(){
        String filePath = DB_PATH + "/data.json";
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write("[\n");

            for(int i=0; i < wiseSayings.size()-1; i++){
                if( i < wiseSayings.size()-1) {
                    String json = """
                            {
                                "id": %d,
                                "content": "%s",
                                "author" : %s
                            },
                            """.formatted(wiseSayings.get(i).getId(), wiseSayings.get(i).getContent(), wiseSayings.get(i).getAuthor());
                    writer.write(json);
                } else{
                    String json = """
                            {
                                "id": %d,
                                "content": "%s",
                                "author" : %s
                            }
                            """.formatted(wiseSayings.get(i).getId(), wiseSayings.get(i).getContent(), wiseSayings.get(i).getAuthor());
                    writer.write(json);
                }

                writer.write("\n]");
                writer.close();
            }
        } catch (IOException e){
            System.out.println("빌드 중 오류가 발생");
        }
    }
}