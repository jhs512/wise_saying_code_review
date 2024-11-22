package org.example.mapper;

import org.example.entity.WiseSaying;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class MyJsonMapper {
    Set<String> wiseSayingFieldList = Arrays.stream(WiseSaying.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());

    public Map<Integer, WiseSaying> parseJsonFromFile(String fileName){
        try {
            Path path = Paths.get(fileName);
            String text = Files.readString(path);
            return makeWiseSayingListFromString(text);
        }catch (Exception e){
            return new TreeMap<>();
        }
    }

    private Map<Integer, WiseSaying> makeWiseSayingListFromString(String text){
        Map<Integer, WiseSaying> result = new TreeMap<>();
        String arrayText = text.length() >= 2 ? text.substring(1, text.length() - 1) : "";

        StringBuilder sb = new StringBuilder();
        boolean parsingWiseSaying = false;
        boolean parsingStringValue = false;
        char previousChar = '\0';

        for(int i = 0; i < arrayText.length(); i++){
            char current = arrayText.charAt(i);

            if(current == '\"' && previousChar != '\\'){
                parsingStringValue = !parsingStringValue;
            }

            if(!parsingStringValue && current == '{'){
                parsingWiseSaying = true;
            }
            if(!parsingStringValue && current == '}'){
                parsingWiseSaying = false;
            }

            if(parsingWiseSaying && current != '{'){
                sb.append(current);
            }else if(!parsingWiseSaying){
                WiseSaying wiseSaying = makeWiseSaying(sb.toString());
                result.put(wiseSaying.getId(), wiseSaying);
                sb.setLength(0);
            }
            previousChar = current;
        }
        return result;
    }

    private WiseSaying makeWiseSaying(String json){
        String[] jsonArray = json.split(":");
        String id = jsonArray[0];
        String content = jsonArray[2];
        String author = jsonArray[4];

        if (!wiseSayingFieldList.containsAll(Set.of(id, content, author))) {
            throw new IllegalStateException();
        }else{
            return new WiseSaying(Integer.parseInt(jsonArray[1]), jsonArray[3], jsonArray[5]);
        }
    }

    public String makeJsonFromWiseSaying(Map<Integer, WiseSaying> wiseSayingMap){
        StringBuilder sb = new StringBuilder("[");
        int size = wiseSayingMap.size();
        int currentIndex = 0;
        for(Map.Entry<Integer, WiseSaying> entry : wiseSayingMap.entrySet()){

            WiseSaying wiseSaying = entry.getValue();
            sb.append("{\"id\":");
            sb.append(entry.getKey());
            sb.append(",\"content\":\"");
            sb.append(wiseSaying.getContent());
            sb.append("\",\"author\":\"");
            sb.append(wiseSaying.getAuthor()).append("\"}");

            currentIndex++;

            if(currentIndex < size){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
