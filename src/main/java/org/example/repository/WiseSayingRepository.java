package org.example.repository;

import org.example.entity.WiseSaying;
import org.example.mapper.MyJsonMapper;

import java.io.*;
import java.util.*;

public class WiseSayingRepository {
    private static final MyJsonMapper MAPPER = new MyJsonMapper();

    private final Map<Integer, WiseSaying> wiseSayingMap;
    private int lastId;
    private final String jsonFilePath;
    private final String lastIdFilePath;


    public WiseSayingRepository(String jsonFilePath, String lastIdFilePath){
        this.jsonFilePath = jsonFilePath;
        this.lastIdFilePath = lastIdFilePath;
        try{
            File jsonFile = new File(jsonFilePath);
            if(jsonFile.createNewFile()){
                wiseSayingMap = new TreeMap<>();
            }else {
                wiseSayingMap  = MAPPER.parseJsonFromFile(jsonFilePath);
            }
            File lastIdFile = new File(lastIdFilePath);

            if(lastIdFile.createNewFile()){
                lastId = 0;
            }else {
                BufferedReader reader = new BufferedReader(new FileReader(lastIdFile));
                try {
                    lastId = Integer.parseInt(reader.readLine());
                }catch (NumberFormatException e){
                    BufferedWriter writer = new BufferedWriter(new FileWriter(lastIdFile));
                    writer.write(String.valueOf(0));
                }
            }
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    public Map<Integer, WiseSaying> getWiseSayingMap(){
        return wiseSayingMap;
    }

    public Optional<WiseSaying> getWiseSayingById(int id){
        return Optional.ofNullable(wiseSayingMap.get(id));
    }

    public int registerWiseSaying(WiseSaying wiseSaying){
        wiseSaying.setId(++lastId);
        wiseSayingMap.put(lastId, wiseSaying);
        return lastId;
    }

    public boolean deleteWiseSaying(int id){
        if(!wiseSayingMap.containsKey(id)){
            return false;
        }else{
            wiseSayingMap.remove(id);
            return true;
        }
    }

    public void modifyWiseSaying(WiseSaying wiseSaying, String newContent){
        wiseSaying.setContent(newContent);
    }

    public void flush(){
        try {
            File jsonFile = new File(jsonFilePath);
            String json = MAPPER.makeJsonFromWiseSaying(wiseSayingMap);
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(jsonFile, false));
            jsonWriter.write(json);
            jsonWriter.close();

            File lastIdFile = new File(lastIdFilePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(lastIdFile, false));
            writer.write(String.valueOf(lastId));
            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
