package com.llwiseSaying.Repository;

import com.llwiseSaying.WiseSaying;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import com.llwiseSaying.Config.Config;


public class WiseSayingGenerator {

    private  Config config;
    public WiseSayingGenerator(Config config) {
        this.config=config;
    }

    public void wirteFile(WiseSaying wiseSaying) {
        int id=wiseSaying.getId();
        String filePath =  config.getDBPath() +"/"+String.valueOf(id)+config.getDBExtension();
        String content =makeJsonContent(wiseSaying);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content); // 데이터 쓰기

        } catch (IOException e) {
            System.out.println(String.valueOf(id)+"데이터베이스 파일 작성 예외 발생");
        }
    }

    private String makeJsonContent(WiseSaying wiseSaying) {
        StringBuilder sb=new StringBuilder();

        Class<?> clazz = wiseSaying.getClass();  // 클래스 정보 가져오기
        Field[] fields = clazz.getDeclaredFields();

        sb.append("{\n");
        for(int i=0;i<fields.length;i++) {
            Field field=fields[i];
            field.setAccessible(true);

            try {
                String name=field.getName();
                Object value=field.get(wiseSaying);

                sb.append("  \"");
                sb.append(name); sb.append("\" : ");

                if(value instanceof String) {
                    sb.append("\"");
                    sb.append(value);
                    sb.append("\",\n");
                } else {
                    sb.append(value); sb.append(",\n");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.setLength(sb.length()-2);
        sb.append("\n");
        sb.append("}\n");


        return  sb.toString();
    }

    public void deleteFile(int id) {

        String fileNameToDelete=String.valueOf(id)+config.getDBExtension();
        File fileToDelete = new File(config.getDBPath(), fileNameToDelete);

        fileToDelete.delete();
    }

}
