package org.example.repository;

import org.example.WiseSaying;
import org.example.mapper.MyJsonMapper;

import java.io.*;
import java.util.*;

public class WiseSayingRepository {
    private static final MyJsonMapper MAPPER = new MyJsonMapper();

    private final Map<Integer, WiseSaying> wiseSayingMap = new HashMap<>();
    private final List<WiseSaying> wiseSayingList;
    private int lastId;

    private static final String JSON_FILE_PATH = "data.json";
    private static final String LAST_ID_FILE_PATH = "lastId.txt";

    public WiseSayingRepository(){
        try{
            File jsonFile = new File(JSON_FILE_PATH);
            if(jsonFile.createNewFile()){
                wiseSayingList = new ArrayList<>();
            }else {
                wiseSayingList = MAPPER.parseJsonFromFile(JSON_FILE_PATH);
                for(WiseSaying wiseSaying : wiseSayingList){
                    wiseSayingMap.put(wiseSaying.getId(), wiseSaying);
                }
            }
            File lastIdFile = new File(LAST_ID_FILE_PATH);

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

    public List<WiseSaying> getWiseSayingList(){
        return wiseSayingList;
    }

    public Optional<WiseSaying> getWiseSayingById(int id){
        return Optional.of(wiseSayingMap.get(id));
    }

    public int registerWiseSaying(WiseSaying wiseSaying){
        wiseSaying.setId(++lastId);
        wiseSayingMap.put(lastId, wiseSaying);
        wiseSayingList.add(wiseSaying);
        return lastId;
    }

    public boolean deleteWiseSaying(int id){
        if(!wiseSayingMap.containsKey(id)){
            return false;
        }else{
            WiseSaying wiseSaying = wiseSayingMap.get(id);
            if(!wiseSayingList.contains(wiseSaying)){
                throw new IllegalStateException("저장소 상태가 이상합니당");
            }
            wiseSayingMap.remove(id);
            wiseSayingList.remove(wiseSaying);
            return true;
        }
    }

    public void modifyWiseSaying(WiseSaying wiseSaying, String newContent){
        wiseSaying.setContent(newContent);
    }

    public void flush(){
        try {
            File jsonFile = new File(JSON_FILE_PATH);
            String json = MAPPER.makeJsonFromWiseSaying(wiseSayingList);
            BufferedWriter jsonWriter = new BufferedWriter(new FileWriter(jsonFile, false));
            jsonWriter.write(json);
            jsonWriter.close();

            File lastIdFile = new File(LAST_ID_FILE_PATH);
            BufferedWriter writer = new BufferedWriter(new FileWriter(lastIdFile, false));
            writer.write(String.valueOf(lastId));
            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
