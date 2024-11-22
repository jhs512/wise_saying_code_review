package com.llwiseSaying.Repository;

import com.llwiseSaying.WiseSaying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;

import static com.llwiseSaying.Repository.WiseSayingRepository.DBdirectoryPath;

public class LoadWiseSayingList {

    public Map<Integer, WiseSaying> loadWiseSayings() {


        File directory = new File(DBdirectoryPath);
        Map<Integer,WiseSaying> wiseSayings=new LinkedHashMap<>();

        File[] files = directory.listFiles();

        //lastId.txt파일 필터링
        File[] jsonFiles = Arrays.stream(files)
                .filter(file -> file.getName().endsWith(".json")) // .json으로 끝나는 파일만 포함
                .toArray(File[]::new);

        File[] sortFiles=sortFileData(jsonFiles);

        if (files != null) {
            for (File file : sortFiles) {
                if(file.getPath().endsWith(".txt")) { continue;}
                WiseSaying wiseSaying=parseJsonData(file);
                wiseSayings.put(wiseSaying.getId(),wiseSaying);
            }
        }

        return wiseSayings;
    }

    File [] sortFileData(File [] files) {

        Arrays.sort(files, (f1, f2) -> {
            String name1 = f1.getName();
            String name2 = f2.getName();
            // 숫자를 기준으로 정렬
            int num1 = Integer.parseInt(name1.replace(".json", ""));
            int num2 = Integer.parseInt(name2.replace(".json", ""));
            return Integer.compare(num1, num2);
        });

        return files;
    }


    WiseSaying parseJsonData(File file) {

        int id=0;
        String content="";
        String writer="";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {

                if(!(line.startsWith("{") || line.startsWith("}"))) {
                    String modifyLine=line.trim();
                    if(modifyLine.endsWith(",")) { modifyLine = modifyLine.substring(0, modifyLine.length() - 1);}
                    String [] splitData=modifyLine.split(":");

                    String name=parseName(splitData[0]);

                    modifyLine=splitData[1].trim();
                    if(name.equals("id")) { id=Integer.parseInt(modifyLine); }
                    if(name.equals("content")) { content=modifyLine.substring(1,modifyLine.length()-1); }
                    if(name.equals("writer")) { writer=modifyLine.substring(1,modifyLine.length()-1); }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new WiseSaying(id,content,writer);
    }

    String parseName(String line) {
        String modifyLine=line.trim();
        modifyLine=modifyLine.substring(1,modifyLine.length()-1);
        return modifyLine;
    }
}
