package com.ll.wiseSaing.controller;

import com.ll.wiseSaing.Info;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WiseSayingController{

    static final String PATH = "D:\\son\\project\\programmers\\db\\wiseSaying\\";
    Scanner sc = new Scanner(System.in);

    File file;
    BufferedWriter bw;


    public void insert(int id, JSONArray arr) throws IOException {
        Info info = new Info();
        JSONObject obj = new JSONObject();

        System.out.print("명언 : ");
        info.setContent(sc.nextLine());

        System.out.print("작가 : ");
        info.setAuthor(sc.nextLine());
        info.setId(id);

        obj.put("id", info.getId());
        obj.put("content", info.getContent());
        obj.put("author", info.getAuthor());

        arr.add(obj);

        file = new File(PATH + id + ".json");
        if (file.createNewFile()) {
            bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(obj.toJSONString());
            bw.flush();
            bw.close();

            System.out.println(id + "번 명언이 등록되었습니다.");
        }
    }

    public void list(JSONArray arr) {
        String rule = sc.nextLine();
        String[] rules = rule.split("/");
        System.out.println("----------------------");

        for (int i = arr.size() - 1; i >= 0; i--) {
            for (int j = 0; j < rules.length; j++) {
                JSONObject obj = (JSONObject) arr.get(i);

                switch (rules[j].trim()) {
                    case "번호" -> System.out.print(obj.get("id"));
                    case "명언" -> System.out.print(obj.get("content"));
                    case "작가" -> System.out.print(obj.get("author"));
                }
                if (j < rules.length - 1) System.out.print(" / ");
            }
            System.out.println();
        }
    }

    public void delete(int id, JSONArray arr) {
        file = new File(PATH + id + ".json");
        boolean flag = false;
        int index = 0;

        for (int i = 0; i<arr.size(); i++) {
            JSONObject tmp = (JSONObject) arr.get(i);
            if (tmp.get("id").equals(id)) {
                index = i;
                flag = true;
                break;
            }
        }
        if (flag) {
            arr.remove(index);
            if (file.exists()) {
                file.delete();
            }
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void modify(int id, JSONArray arr) throws IOException {
        file = new File(PATH + id + ".json");
        if (!file.exists()) file.createNewFile();

        bw = new BufferedWriter(new FileWriter(file, false));
        JSONObject object = new JSONObject();

        for (int i = 0; i<arr.size(); i++) {
            JSONObject tmp = (JSONObject) arr.get(i);
            if (tmp.get("id").equals(id)) {
                object = tmp;
                break;
            }
        }


        if (!object.isEmpty()) {
            object.put("id", id);

            System.out.println("명언(기존) : " + object.get("content"));
            System.out.print("명언 : ");
            object.put("content", sc.nextLine());

            System.out.println("작가(기존) : " + object.get("author"));
            System.out.print("작가 : ");
            object.put("author", sc.nextLine());

            bw.write(object.toJSONString());
            bw.flush();
            bw.close();
        }
    }

    public void build(JSONArray arr) throws IOException {
        file = new File(PATH+"data.json");
        if (!file.exists()){
            file.createNewFile();
        }
        bw = new BufferedWriter(new FileWriter(file, false));
        bw.write(JSONArray.toJSONString(arr));
        bw.flush();
        bw.close();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
