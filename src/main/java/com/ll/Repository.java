package com.ll;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Repository {
    static String filePath = "src/db/wiseSaying/";
    // static String filePath = "src/db/testDB/";
    static String DataJson = "data.json";

    void saveFile(String fileName, String content){
        try{
            FileWriter writer = new FileWriter((filePath + fileName));
            writer.write(content);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    void updateFile(String fileName, String content){
        saveFile(fileName,content);
    }

    boolean isFile(String fileName){
        File file = new File(filePath+fileName);
        if (file.exists()){
            return true;
        }else return false;
    }

    void deleteFile(String fileName){
        File file = new File(filePath+fileName);
        file.delete();
    }

    WiseSaying parseWiseSaying(String string) {
        try {
            // id 추출
            int id = Integer.parseInt(string.split("\"id\":")[1].split(",")[0].trim());
            // content 추출
            String content = string.split("\"content\": \"")[1].split("\",")[0].trim();
            // author 추출
            String author = string.split("\"author\": \"")[1].split("\"")[0].trim();
            return new WiseSaying(id, content, author);
        } catch (Exception e) {
            System.out.println("JSON 파싱 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    String jsonToString(String fileName){
        try{
            String string = new String(Files.readAllBytes(Paths.get(filePath+fileName)));
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] jsonsInDirectory(){
        File dir = new File(filePath);
        FilenameFilter filter = new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        };
        String[] filenames = dir.list(filter);
        return filenames;
    }

    int lastNumber(){
        int i = 0;
        // 기존 파일 있을 경우 (.txt)
        File tfile = new File(filePath + "lastId.txt");
        if(tfile.exists()){
            try{
                FileReader reader = new FileReader(filePath + "lastId.txt");
                i = reader.read() - '0';
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    String makeJsonName(int id){
        String fileName = id + ".json";
        return fileName;
    }
}
