package com.ll.wiseSaing.service;

import com.ll.wiseSaing.model.WiseSaying;
import com.ll.wiseSaing.repository.WiseSayingRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public class WiseSayingService {

    WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public void init(List<WiseSaying> list, int id) throws IOException, ParseException {

        id = wiseSayingRepository.selectLastId();
        JSONArray arr = wiseSayingRepository.selectData();

        if (arr != null){
            for (Object o : arr) {

                JSONObject obj = (JSONObject) o;
                WiseSaying ws = new WiseSaying();

                ws.setId(Integer.parseInt(String.valueOf(obj.get("id"))));
                ws.setContent(String.valueOf(obj.get("content")));
                ws.setAuthor(String.valueOf(obj.get("author")));

                list.add(ws);
            }
        }

    }

    public void insert(WiseSaying wiseSaying) throws IOException {
        JSONObject obj = new JSONObject();

        obj.put("id", wiseSaying.getId());
        obj.put("content", wiseSaying.getContent());
        obj.put("author", wiseSaying.getAuthor());

        wiseSayingRepository.insertData(obj);
    }

    public void insertLastId(int id) throws IOException {

        wiseSayingRepository.insertLastId(id);
    }

    public void selectList(List<WiseSaying> list, String[] rules) {

        for (int i = list.size() - 1; i >= 0; i--) {
            for (int j = 0; j < rules.length; j++) {
                WiseSaying wiseSaying = list.get(i);

                switch (rules[j].trim()) {
                    case "번호" -> System.out.print(wiseSaying.getId());
                    case "명언" -> System.out.print(wiseSaying.getContent());
                    case "작가" -> System.out.print(wiseSaying.getAuthor());
                }
                if (j < rules.length - 1) System.out.print(" / ");
            }
            System.out.println();
        }
    }

    public void deleteData(int id) {

        wiseSayingRepository.deleteData(id);
    }

    public void updateData(WiseSaying wiseSaying) throws IOException {
        JSONObject obj = new JSONObject();

        obj.put("id", wiseSaying.getId());
        obj.put("content", wiseSaying.getContent());
        obj.put("author", wiseSaying.getAuthor());

        wiseSayingRepository.updateData(obj);
    }

    public void build(List<WiseSaying> list) throws IOException {
        JSONArray arr = new JSONArray();

        for (WiseSaying wiseSaying : list) {
            JSONObject obj = new JSONObject();

            obj.put("id", wiseSaying.getId());
            obj.put("content", wiseSaying.getContent());
            obj.put("author", wiseSaying.getAuthor());

            arr.add(obj);
        }

        wiseSayingRepository.build(arr);
    }
}
